package backend.spring.service;

import backend.spring.dto.request.project.EditCommentRequestDto;
import backend.spring.dto.request.project.EditProjectRequestDto;
import backend.spring.dto.request.project.PostCommentRequestDto;
import backend.spring.dto.request.project.PostProjectRequestDto;
import backend.spring.dto.response.ResponseDto;
import backend.spring.dto.response.project.ApplicantListResponseDto;
import backend.spring.dto.response.project.ViewCommentsResponseDto;
import backend.spring.dto.response.project.ViewProjectResponseDto;
import backend.spring.entity.*;
import backend.spring.repository.ProjectApplicantRepository;
import backend.spring.repository.ProjectCommentRepository;
import backend.spring.repository.ProjectRepository;
import backend.spring.repository.ProjectLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectLikeRepository projectLikeRepository;
    private final ProjectCommentRepository projectCommentRepository;
    private final ProjectApplicantRepository applicantRepository;



    @Transactional
    public Long createProject(PostProjectRequestDto requestDto, User user) {

        Project project = new Project();

        project.setTitle(requestDto.getTitle());
        project.setDescription(requestDto.getDescription());
        project.setContent(requestDto.getContent());
        project.setProcessing(requestDto.getProcessing());
        project.setRecruitmentField(requestDto.getRecruitmentField());
        project.setPeople(requestDto.getPeople());
        project.setMeet_location(requestDto.getMeet_location());

        project.setUser(user);
        project.setLikeCount(0);
        project.setViewCount(0);

        Project savedProject = projectRepository.save(project);

        return savedProject.getProjectId();
    }

    @Transactional
    public ResponseEntity<? extends ResponseDto> deleteProject(Long projectId, User user) {
        Project project = projectRepository.findById(projectId)
                .orElse(null);

        if (project == null) {
            return ResponseDto.missing_required_data();
        }

        if (!project.getUser().getUserId().equals(user.getUserId())) {
            return ResponseDto.not_existed_user();
        }

        projectRepository.delete(project);
        return ResponseDto.successResponse();
    }

    @Transactional
    public ResponseEntity<? extends ResponseDto> editProject(
            Long projectId,
            EditProjectRequestDto requestDto,
            User user
    ) {
        Project project = projectRepository.findById(projectId)
                .orElse(null);

        if (project == null) {
            return ResponseDto.missing_required_data();
        }

        if (!project.getUser().getUserId().equals(user.getUserId())) {
            return ResponseDto.not_existed_user();
        }

        project.setTitle(requestDto.getTitle());
        project.setDescription(requestDto.getDescription());
        project.setContent(requestDto.getContent());
        project.setProcessing(requestDto.getProcessing());
        project.setRecruitmentField(requestDto.getRecruitmentField());
        project.setPeople(requestDto.getPeople());
        project.setMeet_location(requestDto.getMeet_location());

        return ResponseDto.successResponse();
    }

    @Transactional
    public ResponseEntity<? extends ResponseDto> getProjectDetail(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElse(null);

        if (project == null) {
            return ResponseDto.missing_required_data();
        }

        project.setViewCount(project.getViewCount() + 1);

        return ViewProjectResponseDto.success(project);
    }

    @Transactional
    public ResponseEntity<? extends ResponseDto> toggleLike(Long projectId, User user) {
        Project project = projectRepository.findById(projectId)
                .orElse(null);

        if (project == null) {
            return ResponseDto.missing_required_data();
        }

        Optional<ProjectLike> likeOptional = projectLikeRepository.findByProjectAndUser(project, user);

        if (likeOptional.isPresent()) {
            projectLikeRepository.delete(likeOptional.get());
            project.setLikeCount(project.getLikeCount() - 1);
            return ResponseEntity.ok(new ResponseDto("LIKE_REMOVED", "좋아요가 취소되었습니다."));
        } else {
            ProjectLike like = new ProjectLike();
            like.setProject(project);
            like.setUser(user);
            projectLikeRepository.save(like);
            project.setLikeCount(project.getLikeCount() + 1);
            return ResponseDto.successResponse();
        }
    }

    @Transactional
    public ResponseEntity<? extends ResponseDto> writeComment(Long projectId, PostCommentRequestDto requestDto, User user) {
        Project project = projectRepository.findById(projectId)
                .orElse(null);

        if (project == null) {
            return ResponseDto.missing_required_data();
        }

        ProjectComment comment = new ProjectComment();
        comment.setMessage(requestDto.getMessage());
        comment.setUser(user);
        comment.setProject(project);

        projectCommentRepository.save(comment);

        return ResponseDto.successResponse();
    }

    @Transactional(readOnly = true)
    public ResponseEntity<? extends ResponseDto> getComments(Long projectId) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project == null) {
            return ResponseDto.missing_required_data();
        }

        List<ProjectComment> comments = project.getCommentList(); // Project 엔티티에서 댓글 리스트 가져옴
        return ViewCommentsResponseDto.success(comments);
    }

    @Transactional
    public ResponseEntity<? extends ResponseDto> editComment(Long commentId, EditCommentRequestDto requestDto, User user) {
        ProjectComment comment = projectCommentRepository.findById(commentId)
                .orElse(null);
        if (comment == null) {
            return ResponseDto.missing_required_data();
        }

        if (!comment.getUser().getUserId().equals(user.getUserId())) {
            return ResponseDto.not_existed_user(); // 권한 없음
        }

        comment.setMessage(requestDto.getMessage());
        return ResponseDto.successResponse();
    }

    @Transactional
    public ResponseEntity<? extends ResponseDto> deleteComment(Long commentId, User user) {
        ProjectComment comment = projectCommentRepository.findById(commentId)
                .orElse(null);
        if (comment == null) {
            return ResponseDto.missing_required_data();
        }

        if (!comment.getUser().getUserId().equals(user.getUserId())) {
            return ResponseDto.not_existed_user();
        }

        projectCommentRepository.delete(comment);
        return ResponseDto.successResponse();
    }

    @Transactional
    public ResponseEntity<? extends ResponseDto> applyToProject(Long projectId, User user) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) return ResponseDto.missing_required_data();

        if (applicantRepository.existsByProjectAndUser(project, user)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto("ALREADY_APPLIED", "이미 지원한 글입니다."));
        }

        ProjectApplicant applicant = new ProjectApplicant();
        applicant.setProject(project);
        applicant.setUser(user);
        applicant.setAccepted(false);
        applicantRepository.save(applicant);

        return ResponseDto.successResponse();
    }

    @Transactional(readOnly = true)
    public ResponseEntity<? extends ResponseDto> getApplicants(Long projectId, User requester) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) return ResponseDto.missing_required_data();

        if (!project.getUser().getUserId().equals(requester.getUserId())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDto("FORBIDDEN", "본인의 모집글만 조회할 수 있습니다."));
        }

        List<ProjectApplicant> applicants = applicantRepository.findByProject(project);

        List<String> nicknames = applicants.stream()
                .map(applicant -> applicant.getUser().getNickname())
                .collect(Collectors.toList());

        return ApplicantListResponseDto.success(nicknames);
    }

}