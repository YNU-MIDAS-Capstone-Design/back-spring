package backend.spring.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.ArrayList;

import backend.spring.service.ProjectService;
import backend.spring.dto.request.project.*;
import backend.spring.dto.response.project.*;
import backend.spring.dto.response.ResponseDto;
import backend.spring.entity.*;
import backend.spring.repository.*;

@Service
@RequiredArgsConstructor
public class ProjectServiceImplement implements ProjectService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectCommentRepository projectCommentRepository;
    private final ProjectLikeRepository projectLikeRepository;

    @Override
    public ResponseEntity<? super PostProjectResponseDto> postProject(PostProjectRequestDto dto, long userId) {
        try {
            boolean existedUser = userRepository.existsByUserId(userId);
            if (!existedUser) return PostProjectResponseDto.notExistUser();

            ProjectEntity projectEntity = new ProjectEntity(dto, userId);
            projectRepository.save(projectEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostProjectResponseDto.success();
    }

    @Override   //없애기
    public ResponseEntity<? super GetProjectLikeResponseDto> getProjectLikeList(Integer projectId) {
        List<GetProjectLikeListResultSet> resultSets = new ArrayList<>();
        try {
            boolean existedProject = projectRepository.existsByProjectId(projectId);
            if (!existedProject) return GetProjectLikeResponseDto.noExistProject();
            resultSets = projectLikeRepository.getProjectLikeList(projectId);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetProjectLikeResponseDto.success(resultSets);
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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