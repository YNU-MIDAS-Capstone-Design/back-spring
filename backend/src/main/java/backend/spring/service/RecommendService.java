package backend.spring.service;

import backend.spring.dto.object.RecommendScoreDto;
import backend.spring.entity.User;
import backend.spring.entity.Project;
import backend.spring.repository.UserRepository;
import backend.spring.repository.ProjectRepository;
import backend.spring.repository.ProjectLikeRepository;
import backend.spring.repository.ProjectCommentRepository;
import backend.spring.repository.ProjectApplicantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.AbstractMap.SimpleEntry;

@Service
@Transactional(readOnly = true)
public class RecommendService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectLikeRepository likeRepository;
    private final ProjectCommentRepository commentRepository;
    private final ProjectApplicantRepository applicantRepository;

    public RecommendService(UserRepository userRepository,
                            ProjectRepository projectRepository,
                            ProjectLikeRepository likeRepository,
                            ProjectCommentRepository commentRepository,
                            ProjectApplicantRepository applicantRepository) {
        this.userRepository      = userRepository;
        this.projectRepository   = projectRepository;
        this.likeRepository      = likeRepository;
        this.commentRepository   = commentRepository;
        this.applicantRepository = applicantRepository;
    }

    /**
     * 1) 콘텐츠 기반 유사도 (Jaccard)
     * 2) User-Based 협업 필터링 (좋아요·댓글·지원 가중치)
     * 3) 최종 점수 = 0.7 * contentScore + 0.3 * normalized collabScore
     */
    public List<Project> recommendHybrid(Long userId) {
        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //  이미 상호작용한 프로젝트 집합 수집
        Set<Long> interacted = new HashSet<>();
        likeRepository.findAll().stream()
                .filter(l -> l.getUser().getUserId().equals(userId))
                .map(l -> l.getProject().getProjectId())
                .forEach(interacted::add);
        commentRepository.findAll().stream()
                .filter(c -> c.getUser().getUserId().equals(userId))
                .map(c -> c.getProject().getProjectId())
                .forEach(interacted::add);
        applicantRepository.findAll().stream()
                .filter(a -> a.getUser().getUserId().equals(userId))
                .map(a -> a.getProject().getProjectId())
                .forEach(interacted::add);

        //  콘텐츠 기반 점수 계산 (Jaccard on tech stacks)
        Set<String> userStacks = user.getTechStacks().stream()
                .map(ts -> ts.getName().name())
                .collect(Collectors.toSet());
        Map<Long, Double> contentScore = new HashMap<>();
        projectRepository.findAll().forEach(p -> {
            Set<String> projStacks = p.getStackList().stream()
                    .map(ps -> ps.getStack().name())
                    .collect(Collectors.toSet());
            contentScore.put(p.getProjectId(),
                    jaccard(userStacks, projStacks));
        });


        //  모든 유저별 프로젝트 상호작용 가중치 벡터 생성
        //    좋아요=1, 댓글=2, 지원=4
        Map<Long, Map<Long, Double>> userWeights = new HashMap<>();
        // 좋아요 → weight = 1
        likeRepository.findAll().forEach(l ->
                userWeights
                        .computeIfAbsent(l.getUser().getUserId(), k -> new HashMap<>())
                        .merge(l.getProject().getProjectId(), 1.0, Double::sum)
        );
        // 댓글 → weight = 2
        commentRepository.findAll().forEach(c ->
                userWeights
                        .computeIfAbsent(c.getUser().getUserId(), k -> new HashMap<>())
                        .merge(c.getProject().getProjectId(), 2.0, Double::sum)
        );
        // 지원 → weight = 4
        applicantRepository.findAll().forEach(a ->
                userWeights
                        .computeIfAbsent(a.getUser().getUserId(), k -> new HashMap<>())
                        .merge(a.getProject().getProjectId(), 4.0, Double::sum)
        );

        // 내 가중치 벡터
        Map<Long, Double> myWeights = userWeights.getOrDefault(userId, Collections.emptyMap());

        // 다른 유저들과의 가중치 코사인 유사도 계산
        Map<Long, Double> similarity = new HashMap<>();
        for (var entry : userWeights.entrySet()) {
            Long otherId = entry.getKey();
            if (otherId.equals(userId)) continue;
            double sim = weightedCosine(myWeights, entry.getValue());
            if (sim > 0) {
                similarity.put(otherId, sim);
            }
        }

        //프로젝트별 협업 점수 집계 (sim × 해당 유저의 프로젝트 가중치)
        Map<Long, Double> collabRaw = new HashMap<>();
        for (var simEntry : similarity.entrySet()) {
            double sim = simEntry.getValue();
            Map<Long, Double> otherWeights = userWeights.get(simEntry.getKey());
            for (var projEntry : otherWeights.entrySet()) {
                Long pid = projEntry.getKey();
                if (interacted.contains(pid)) continue;  // 이미 상호작용한 건 제외
                double w = projEntry.getValue();
                collabRaw.merge(pid, sim * w, Double::sum);
            }
        }

        // 협업 점수 정규화
        double maxCollab = collabRaw.values().stream()
                .max(Double::compareTo)
                .orElse(1.0);


        // 최종 점수 합산 및 정렬
        return projectRepository.findAll().stream()
                .filter(p -> !interacted.contains(p.getProjectId()))
                .map(p -> new SimpleEntry<>(
                        p,
                        0.7 * contentScore.getOrDefault(p.getProjectId(), 0.0)
                                + 0.3 * (collabRaw.getOrDefault(p.getProjectId(), 0.0) / maxCollab)
                ))
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .map(SimpleEntry::getKey)
                .collect(Collectors.toList());
    }

    //Jaccard 유사도 계산
    private double jaccard(Set<String> a, Set<String> b) {
        if (a.isEmpty() && b.isEmpty()) return 0.0;
        Set<String> inter = new HashSet<>(a);
        inter.retainAll(b);
        Set<String> union = new HashSet<>(a);
        union.addAll(b);
        return (double) inter.size() / union.size();
    }

    //가중치 기반 Cosine 유사도 계산
    private double weightedCosine(Map<Long, Double> a, Map<Long, Double> b) {
        if (a.isEmpty() || b.isEmpty()) return 0.0;
        double dot = 0;
        for (var e : a.entrySet()) {
            dot += e.getValue() * b.getOrDefault(e.getKey(), 0.0);
        }
        // norms
        double normA = Math.sqrt(a.values().stream().mapToDouble(v -> v * v).sum());
        double normB = Math.sqrt(b.values().stream().mapToDouble(v -> v * v).sum());
        return (normA == 0 || normB == 0) ? 0.0 : dot / (normA * normB);
    }

    public List<RecommendScoreDto> getRecommendScores(Long userId) {
        //  사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 이미 상호작용한 프로젝트 수집
        Set<Long> interacted = new HashSet<>();
        likeRepository.findAll().stream()
                .filter(l -> l.getUser().getUserId().equals(userId))
                .map(l -> l.getProject().getProjectId())
                .forEach(interacted::add);
        commentRepository.findAll().stream()
                .filter(c -> c.getUser().getUserId().equals(userId))
                .map(c -> c.getProject().getProjectId())
                .forEach(interacted::add);
        applicantRepository.findAll().stream()
                .filter(a -> a.getUser().getUserId().equals(userId))
                .map(a -> a.getProject().getProjectId())
                .forEach(interacted::add);

        //  콘텐츠 기반 점수
        Set<String> userStacks = user.getTechStacks().stream()
                .map(ts -> ts.getName().name())
                .collect(Collectors.toSet());
        Map<Long, Double> contentScore = new HashMap<>();
        projectRepository.findAll().forEach(p -> {
            Set<String> projStacks = p.getStackList().stream()
                    .map(ps -> ps.getStack().name())
                    .collect(Collectors.toSet());
            contentScore.put(p.getProjectId(), jaccard(userStacks, projStacks));
        });


        // 유저별 상호작용 가중치 벡터
        Map<Long, Map<Long, Double>> userWeights = new HashMap<>();
        likeRepository.findAll().forEach(l ->
                userWeights
                        .computeIfAbsent(l.getUser().getUserId(), k -> new HashMap<>())
                        .merge(l.getProject().getProjectId(), 1.0, Double::sum)
        );
        commentRepository.findAll().forEach(c ->
                userWeights
                        .computeIfAbsent(c.getUser().getUserId(), k -> new HashMap<>())
                        .merge(c.getProject().getProjectId(), 2.0, Double::sum)
        );
        applicantRepository.findAll().forEach(a ->
                userWeights
                        .computeIfAbsent(a.getUser().getUserId(), k -> new HashMap<>())
                        .merge(a.getProject().getProjectId(), 4.0, Double::sum)
        );

        Map<Long, Double> myWeights = userWeights.getOrDefault(userId, Collections.emptyMap());

        Map<Long, Double> similarity = new HashMap<>();
        for (var entry : userWeights.entrySet()) {
            Long otherId = entry.getKey();
            if (otherId.equals(userId)) continue;
            double sim = weightedCosine(myWeights, entry.getValue());
            if (sim > 0) similarity.put(otherId, sim);
        }

        Map<Long, Double> collabRaw = new HashMap<>();
        for (var simEntry : similarity.entrySet()) {
            double sim = simEntry.getValue();
            Map<Long, Double> otherWeights = userWeights.get(simEntry.getKey());
            for (var projEntry : otherWeights.entrySet()) {
                Long pid = projEntry.getKey();
                if (interacted.contains(pid)) continue;
                double w = projEntry.getValue();
                collabRaw.merge(pid, sim * w, Double::sum);
            }
        }

        double maxCollab = collabRaw.values().stream()
                .max(Double::compareTo)
                .orElse(1.0);

        //  DTO 매핑 및 정렬
        return projectRepository.findAll().stream()
                .filter(p -> !interacted.contains(p.getProjectId()))
                .map(p -> {
                    double c = contentScore.getOrDefault(p.getProjectId(), 0.0);
                    double colNorm = collabRaw.getOrDefault(p.getProjectId(), 0.0) / maxCollab;
                    double finalS = 0.7 * c + 0.3 * colNorm;
                    return new RecommendScoreDto(p.getProjectId(), c, colNorm, finalS);
                })
                .sorted(Comparator.comparingDouble(RecommendScoreDto::getFinalScore).reversed())
                .collect(Collectors.toList());
    }
}
