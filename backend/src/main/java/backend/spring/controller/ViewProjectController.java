package backend.spring.controller;

import java.util.List;

import backend.spring.dto.response.ResponseDto;
import backend.spring.dto.response.view.ViewHomeResponseDto;
import backend.spring.dto.response.view.ViewProjectResponseDto;
import backend.spring.security.CustomUserDetails;
import backend.spring.service.query.ViewProjectService;

import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.spring.entity.enums.Location;
import backend.spring.entity.enums.OrderProject;
import backend.spring.entity.enums.Stack;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ViewProjectController {

	private final ViewProjectService viewProjectService;

	/**
	 * 프로젝트 리스트 조회 (필터링 & 정렬 포함)
	 * @param page 현재 페이지 번호
	 * @param order 정렬 방식 (예: RECENT, POPULAR, RECOMMEND 등)
	 * @param location 오프라인 지역 조건
	 * @param stacks 기술 스택 리스트
	 * @param keyword 검색 키워드
	 * @return ViewProjectResponseDto
	 */
	@Operation(
		summary = "프로젝트 페이지 불러오기",
		responses = {
			@ApiResponse(responseCode = "200", description = "성공(SU), 프로젝트가 없음(ER)",
				content = @Content(schema = @Schema(implementation = ViewProjectResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 사용자",
				content = @Content(schema = @Schema(implementation = ResponseDto.class))),
		}
	)
	@GetMapping("/project")   //nickname로 user 가져와서 추천알고리즘 하는 걸 추가해야함.
	public ResponseEntity<? super ViewProjectResponseDto> getProject( //required = false 선택적 파라미터
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "order", required = false, defaultValue = "RECENT") OrderProject order,
		@RequestParam(name = "process", required = false, defaultValue = "false") boolean process, //true: 모집중만
		@RequestParam(name = "location", required = false) Location location, //enum으로 매칭되도록 받기
		@RequestParam(name = "stacks", required = false) List<Stack> stacks,
		@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword
		//@AuthenticationPrincipal CustomUserDetails userDetails // 현재 로그인 유저 정보
	) {
		Long user_id = 1L;
		return viewProjectService.getProjectPage( user_id, page, order, process, location, stacks, keyword);
	}

	@GetMapping("/home")   //nickname로 user 가져와서 추천알고리즘 하는 걸 추가해야함.
	@Operation(
		summary = "프로젝트 페이지 불러오기",
		responses = {
			@ApiResponse(responseCode = "200", description = "성공(SU), 프로젝트가 없음(ER)",
				content = @Content(schema = @Schema(implementation = ViewHomeResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 사용자",
				content = @Content(schema = @Schema(implementation = ResponseDto.class))),
		}
	)
	public ResponseEntity<? super ViewHomeResponseDto> getHome(
		//@AuthenticationPrincipal CustomUserDetails userDetails
	){
		Long user_id = 1L;
		return viewProjectService.getHomePage( user_id );
	}

	// //팀원 초대하기
	// @PostMapping("/project/invite/{volunteer_id}")  //지원자(user_id, project_id) -> 팀 멤버(user_id, team_id, owner)
	// public ResponseEntity<? super InviteMemberResponseDto> inviteMember(
	// 	@PathVariable Long volunteer_id,
	// 	@AuthenticationPrincipal CustomUserDetails userDetails){ //사용자가 팀장인지 확인 후 팀원을 초대
	// 	return viewProjectService.inviteMember(volunteer_id, userDetails.getUserId() );
	// }
}
