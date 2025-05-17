package backend.spring.controller;

import backend.spring.dto.request.project.EditCommentRequestDto;
import backend.spring.dto.request.project.EditProjectRequestDto;
import backend.spring.dto.request.project.PostCommentRequestDto;
import backend.spring.dto.request.project.PostProjectRequestDto;
import backend.spring.dto.response.ResponseDto;
import backend.spring.dto.response.project.PostProjectResponseDto;
import backend.spring.security.CustomUserDetails;
import backend.spring.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Project", description = "Project 관련 API")
@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "프로젝트 등록", description = "모집글 등록")
    @PostMapping
    public ResponseEntity<? extends ResponseDto> createProject(
            @RequestBody @Valid PostProjectRequestDto requestDto,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return PostProjectResponseDto.success(
                projectService.createProject(requestDto, userDetails.getUser())
        );
    }

    @Operation(summary = "프로젝트 삭제", description = "작성자가 모집글 삭제")
    @DeleteMapping("/{projectId}/delete")
    public ResponseEntity<? extends ResponseDto> deleteProject(
            @PathVariable Long projectId,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return projectService.deleteProject(projectId, userDetails.getUser());
    }

    @Operation(summary = "프로젝트 수정", description = "글 수정")
    @PutMapping("/{projectId}/edit")
    public ResponseEntity<? extends ResponseDto> editProject(
            @PathVariable Long projectId,
            @RequestBody @Valid EditProjectRequestDto requestDto,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return projectService.editProject(projectId, requestDto, userDetails.getUser());
    }

    @Operation(summary = "프로젝트 상세 조회", description = "프로젝트 ID를 통해 모집글 상세 조회 가능")
    @GetMapping("/{projectId}/view")
    public ResponseEntity<? extends ResponseDto> getProjectDetail(
            @PathVariable Long projectId
    ) {
        return projectService.getProjectDetail(projectId);
    }

    @Operation(summary = "좋아요", description = "좋아요/취소")
    @PostMapping("/{projectId}/like")
    public ResponseEntity<? extends ResponseDto> toggleLike(
            @PathVariable Long projectId,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return projectService.toggleLike(projectId, userDetails.getUser());
    }

    @Operation(summary = "댓글", description = "댓글 작성")
    @PostMapping("/{projectId}/comment")
    public ResponseEntity<? extends ResponseDto> writeComment(
            @PathVariable Long projectId,
            @RequestBody @Valid PostCommentRequestDto requestDto,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return projectService.writeComment(projectId, requestDto, userDetails.getUser());
    }

    @Operation(summary = "댓글 목록 조회", description = "모집글의 댓글 목록을 가져옴")
    @GetMapping("/{projectId}/comment/list")
    public ResponseEntity<? extends ResponseDto> getComments(
            @PathVariable Long projectId
    ) {
        return projectService.getComments(projectId);
    }

    @Operation(summary = "댓글 수정", description = "댓글 내용 수정")
    @PutMapping("/comment/{commentId}/edit")
    public ResponseEntity<? extends ResponseDto> editComment(
            @PathVariable Long commentId,
            @RequestBody @Valid EditCommentRequestDto requestDto,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return projectService.editComment(commentId, requestDto, userDetails.getUser());
    }

    @Operation(summary = "댓글 삭제", description = "댓글 삭제")
    @DeleteMapping("/comment/{commentId}/delete")
    public ResponseEntity<? extends ResponseDto> deleteComment(
            @PathVariable Long commentId,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return projectService.deleteComment(commentId, userDetails.getUser());
    }

    @Operation(summary = "프로젝트 지원", description = "로그인한 유저가 해당 프로젝트에 지원합니다.")
    @PostMapping("/{projectId}/apply")
    public ResponseEntity<? extends ResponseDto> applyToProject(
            @PathVariable Long projectId,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return projectService.applyToProject(projectId, userDetails.getUser());
    }

    @Operation(summary = "지원자 목록 조회", description = "작성자가 자신의 프로젝트에 지원한 사용자 목록을 조회합니다.")
    @GetMapping("/{projectId}/applicants")
    public ResponseEntity<? extends ResponseDto> getApplicants(
            @PathVariable Long projectId,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return projectService.getApplicants(projectId, userDetails.getUser());
    }

    @Operation(summary = "프로젝트 지원 취소", description = "로그인한 유저가 해당 프로젝트에 대한 지원을 취소합니다.")
    @DeleteMapping("/{projectId}/cancel")
    public ResponseEntity<? extends ResponseDto> cancelApplication(
            @PathVariable Long projectId,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return projectService.cancelApplication(projectId, userDetails.getUser());
    }


}
