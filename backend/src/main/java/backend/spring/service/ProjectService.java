package backend.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Optional;

import backend.spring.dto.request.project.*;
import backend.spring.dto.response.project.*;
import backend.spring.dto.response.ResponseDto;
import backend.spring.entity.*;
import backend.spring.repository.*;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectCommentRepository projectCommentRepository;
    private final ProjectLikeRepository projectLikeRepository;

    public ResponseEntity<? super PostProjectResponseDto> postProject(PostProjectRequestDto dto, long userId) {
        try {
            // 1. 사용자 존재 여부 확인
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                return PostProjectCommentResponseDto.noExistUser();
            }
            User user = optionalUser.get();

            // 2. 프로젝트 객체 생성 및 설정
            Project project = new Project(dto.getTitle(), dto.getContent());

            // 저장
            projectRepository.save(project);

            return PostProjectResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace(); // 서버 로그 확인용
            return ResponseDto.databaseError();
        }
    }


    public ResponseEntity<? super PutProjectLikeResponseDto> putProjectLike(Integer projectId, long userId) {
        try {
            boolean existedUser = userRepository.existsByUserId(userId);
            if (!existedUser) return PutProjectLikeResponseDto.noExistUser();

            ProjectEntity projectEntity = projectRepository.findByProjectId(projectId);
            if (projectEntity == null) return PutProjectLikeResponseDto.noExistProject();

            ProjectLikeEntity projectLikeEntity = projectLikeRepository.findByProjectIdAndUserId(projectId, userId);
            if (projectLikeEntity == null) {
                projectLikeEntity = new ProjectLikeEntity(projectId, userId);
                projectLikeRepository.save(projectLikeEntity);
                projectEntity.increaseLikeCount();
            } else {
                projectLikeRepository.delete(projectLikeEntity);
                projectEntity.decreaseLikeCount();
            }

            projectRepository.save(projectEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PutProjectLikeResponseDto.success();
    }


    public ResponseEntity<? super GetProjectCommentListResponseDto> getProjectCommentList(Integer projectId) {
        List<GetProjectCommentListResultSet> resultSets = new ArrayList<>();
        try {
            boolean existedProject = projectRepository.existsByProjectId(projectId);
            if (!existedProject) return GetProjectCommentListResponseDto.noExistProject();
            resultSets = projectCommentRepository.getProjectCommentList(projectId);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetProjectCommentListResponseDto.success(resultSets);
    }


    public ResponseEntity<? super DeleteProjectResponseDto> deleteProject(Integer projectId) {
        try {
            ProjectEntity projectEntity = projectRepository.findByProjectId(projectId);
            if (projectEntity == null) return DeleteProjectResponseDto.noExistProject();

            // writer 체크 생략 (userId 받아야 함)
            projectLikeRepository.deleteByProjectId(projectId);
            projectCommentRepository.deleteByProjectId(projectId);
            projectRepository.delete(projectEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return DeleteProjectResponseDto.success();
    }


    public ResponseEntity<? super PostProjectCommentResponseDto> postProjectComment(PostProjectCommentRequestDto dto, long userId) {
        try {
            ProjectEntity projectEntity = projectRepository.findByProjectId(dto.getProjectId());
            if (projectEntity == null) return PostProjectCommentResponseDto.noExistProject();

            boolean existedUser = userRepository.existsByUserId(userId);
            if (!existedUser) return PostProjectCommentResponseDto.noExistUser();

            ProjectCommentEntity commentEntity = new ProjectCommentEntity(dto, userId);
            projectCommentRepository.save(commentEntity);

            projectEntity.increaseCommentCount();
            projectRepository.save(projectEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostProjectCommentResponseDto.success();
    }
}