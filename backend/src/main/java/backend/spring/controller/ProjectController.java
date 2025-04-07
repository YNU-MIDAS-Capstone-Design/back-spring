package backend.spring.controller;

import backend.spring.dto.request.project.PostProjectCommentRequestDto;
import backend.spring.dto.request.project.PostProjectRequestDto;
import backend.spring.dto.response.project.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping; //기존 import
import org.springframework.web.bind.annotation.RestController; //기존 import
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/project")
public class ProjectController{
    private final ProjectService projectService;

    @PostMapping("")
    public ResponseEntity<? super PostProjectResponseDto> postProject(
            @RequestBody @Valid PostProjectRequestDto requestBody,
            @AuthenticationPrincipal Long userId
    ){
        ResponseEntity<? super PostProjectResponseDto> response = projectService.postProject(requestBody, userId);
        return response;
    }

    @PutMapping("/{project_id}/like")
    public ResponseEntity<? super PutProjectLikeResponseDto> putProjectLike(
            @PathVariable("project_id") Integer projectId,
            @AuthenticationPrincipal Long userId
    ){
        ResponseEntity<? super PutProjectResponseDto> response = projectService.putProjectLike(projectId, userId);
        return response;
    }

    @PutMapping("/{project_id}/comment")
    public ResponseEntity<? super PostProjectCommentResponseDto> postProjectComment(
            @RequestBody @Valid PostProjectCommentRequestDto requestBody,
            @PathVariable("project_id") Integer projectId,
            @AuthenticationPrincipal Long userId
    ){
        ResponseEntity<? super PostProjectCommentResponseDto> response = projectService.putProjectComment(requestBody, projectId, userId);
        return response;
    }

    @GetMapping("/{project_id}/like_list")
    public ResponseEntity<? super GetProjectLikeListResponseDto> getProjectLikeList(
            @PathVariable("project_id") Integer projectId
    ){
        ResponseEntity<? super GetProjectLikeListResponseDto> response = projectService.getProjectLikeList(projectId);
        return response;
    }

    @GetMapping("/{project_id}/comment_list")
    public ResponseEntity<? super GetProjectCommentListResponseDto> getProjectCommentList(
            @PathVariable("project_id") Integer projectId
            ){
        ResponseEntity<? super GetProjectCommentListResponseDto> response = projectService.getProjectCommentList(projectId);
        return response;
    }

    @DeleteMapping("/{project_id}")
    public ResponseEntity<? super DeleteProjectResponseDto> deleteProject(
            @PathVariable("project_id") Integer projectId,
            @AuthenticationPrincipal Long userId
    ) {
        ResponseEntity<? super DeleteProjectResponseDto> response = projectService.deleteProject(projectId, userId);
        return response;
    }
}