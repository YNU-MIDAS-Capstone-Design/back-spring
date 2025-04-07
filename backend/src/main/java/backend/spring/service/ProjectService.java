package backend.spring.service;

import org.springframework.http.ResponseEntity;

import backend.spring.dto.request.project.PostProjectRequestDto;
import backend.spring.dto.request.project.PostProjectCommentRequestDto;

import backend.spring.dto.response.project.PostProjectResponseDto;
import backend.spring.dto.response.project.PostProjectCommentResponseDto;
import backend.spring.dto.response.project.GetProjectLikeResponseDto;
import backend.spring.dto.response.project.GetProjectCommentListResponseDto;
import backend.spring.dto.response.project.PutProjectLikeResponseDto;
import backend.spring.dto.response.project.DeleteProjectResponseDto;

public interface ProjectService {

    ResponseEntity<? super PostProjectResponseDto> postProject(PostProjectRequestDto dto, long userId);
    ResponseEntity<? super PostProjectCommentResponseDto> postProjectComment(PostProjectCommentRequestDto dto, long userId);
    ResponseEntity<? super GetProjectLikeResponseDto> getProjectLikeList(Integer projectId);
    ResponseEntity<? super GetProjectCommentListResponseDto> getProjectCommentList(Integer projectId);
    ResponseEntity<? super PutProjectLikeResponseDto> putProjectLike(Integer projectId, long userId);
    ResponseEntity<? super DeleteProjectResponseDto> deleteProject(Integer projectId);
}