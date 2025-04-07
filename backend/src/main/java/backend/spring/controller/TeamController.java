package backend.spring.controller;

import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.spring.dto.request.myteams.CalendarAddRequestDto;
import backend.spring.dto.request.myteams.CalendarRequestDto;
import backend.spring.dto.request.myteams.MemberStackRequestDto;
import backend.spring.dto.request.myteams.TeamNameRequestDto;
import backend.spring.dto.response.ResponseDto;
import backend.spring.dto.response.myteams.CalendarEditResponseDto;
import backend.spring.dto.response.myteams.CalendarResponseDto;
import backend.spring.dto.response.myteams.GetMemberResponseDto;
import backend.spring.dto.response.myteams.MemberStackResponseDto;
import backend.spring.dto.response.myteams.TeamNameResponseDto;
import backend.spring.dto.response.myteams.ViewTeamsResponseDto;
import backend.spring.security.CustomUserDetails;
import backend.spring.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myteams/team")
public class TeamController {
	private final TeamService teamService;

	//나의 팀 페이지 불러오기(팀: 팀이름, 역할)
	@Operation(
		summary = "팀 페이지 불러오기",
		responses = {
			@ApiResponse(responseCode = "200", description = "성공(SU), 속한 팀이 없습니다(ER)",
				content = @Content(schema = @Schema(implementation = ViewTeamsResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 사용자(NU)",
				content = @Content(schema = @Schema(implementation = ResponseDto.class))),
		}
	)
	@GetMapping("")
	public ResponseEntity<? super ViewTeamsResponseDto> viewMyTeam(
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return teamService.viewMyTeam( userDetails.getUser().getUserId() );
	}

	//팀 이름 바꾸기(팀장만)
	@Operation(
		summary = "팀 이름 변경",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			required = true,
			content = @Content(
				schema = @Schema(implementation = TeamNameRequestDto.class)
			)
		),
		responses = {
			@ApiResponse(responseCode = "200", description = "성공",
				content = @Content(schema = @Schema(implementation = TeamNameResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 팀(NET), 존재하지 않는 사용자(NU), 수정할 데이터가 null(MRD)",
				content = @Content(schema = @Schema(implementation = ResponseDto.class))),
			@ApiResponse(responseCode = "403", description = "팀장이 아님(NMU)",
				content = @Content(schema = @Schema(implementation = ResponseDto.class)))
		}
	)
	@PostMapping("/name/{team_id}")
	public ResponseEntity<? super TeamNameResponseDto> changeTeamName(
		@RequestBody TeamNameRequestDto teamNameRequestDto,
		@PathVariable Long team_id,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return teamService.changeTeamName(team_id, userDetails.getUser().getUserId(), teamNameRequestDto);
	}

	//팀 멤버 불러오기
	@Operation(
		summary = "팀 멤버 불러오기",
		responses = {
			@ApiResponse(responseCode = "200", description = "성공(SU), 팀원이 존재하지 않음(ER)",
				content = @Content(schema = @Schema(implementation = GetMemberResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 팀(NET), 존재하지 않는 사용자(NU)",
				content = @Content(schema = @Schema(implementation = ResponseDto.class))),
		}
	)
	@GetMapping("/{team_id}/member")
	public ResponseEntity<? super GetMemberResponseDto> getMember(
		@PathVariable Long team_id,
		@AuthenticationPrincipal CustomUserDetails userDetails
	){
		return teamService.getMember(team_id, userDetails.getUser().getUserId() );
	}

	//팀원 스택 바꾸기(팀원 자신만)
	@Operation(
		summary = "팀원 스택 바꾸기(팀원 자신만)",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			required = true,
			content = @Content(
				schema = @Schema(implementation = MemberStackRequestDto.class)
			)
		),
		responses = {
			@ApiResponse(responseCode = "200", description = "성공(SU)",
				content = @Content(schema = @Schema(implementation = MemberStackResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "해당 팀원이 존재하지 않음(NEI), 수정할 데이터가 null(MRD)",
				content = @Content(schema = @Schema(implementation = ResponseDto.class))),
			@ApiResponse(responseCode = "403", description = "해당 팀원과 사용자가 일치하지 않음(NMU)",
				content = @Content(schema = @Schema(implementation = ResponseDto.class)))
		}
	)
	@PostMapping("/member/position/{member_id}")
	public ResponseEntity<? super MemberStackResponseDto> changePosition(
		@RequestBody MemberStackRequestDto memberStackRequestDto,
		@PathVariable Long member_id,
		@AuthenticationPrincipal CustomUserDetails userDetails
	){
		return teamService.changePosition(member_id, userDetails.getUser().getUserId(), memberStackRequestDto);
	}

	//팀 일정 month 별로 불러오기
	@GetMapping("/{team_id}/calendar")
	@Operation(
		summary = "팀 일정 불러오기",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			required = true,
			content = @Content(
				schema = @Schema(implementation = CalendarRequestDto.class)
			)
		),
		responses = {
			@ApiResponse(responseCode = "200", description = "성공(SU), 일정이 존재하지 않음(ER)",
				content = @Content(schema = @Schema(implementation = CalendarResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 팀(NET), 존재하지 않는 사용자(NU)",
				content = @Content(schema = @Schema(implementation = ResponseDto.class)))
		}
	)
	public ResponseEntity<? super CalendarResponseDto> viewCalendar(
		@RequestBody CalendarRequestDto calendarRequestDto, //year, month
		@PathVariable Long team_id,
		@AuthenticationPrincipal CustomUserDetails userDetails
	){
		return teamService.viewCalendar(team_id, userDetails.getUser().getUserId(), calendarRequestDto);
	}

	//팀 일정 추가
	@PostMapping("/{team_id}/calendar/add")
	@Operation(
		summary = "팀 일정 추가",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			required = true,
			content = @Content(
				schema = @Schema(implementation = CalendarAddRequestDto.class)
			)
		),
		responses = {
			@ApiResponse(responseCode = "200", description = "성공(SU)",
				content = @Content(schema = @Schema(implementation = CalendarEditResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 팀(NET), 존재하지 않는 사용자(NU), 수정할 데이터가 null(MRD)",
				content = @Content(schema = @Schema(implementation = ResponseDto.class)))
		}
	)
	public ResponseEntity<? super CalendarEditResponseDto> addCalendar(
		@RequestBody CalendarAddRequestDto calendarAddRequestDto, //날짜, 내용
		@PathVariable Long team_id,
		@AuthenticationPrincipal CustomUserDetails userDetails
	)
	{
		return teamService.addCalendar(team_id, userDetails.getUser().getUserId(), calendarAddRequestDto);
	}

	//팀 일정 삭제
	@DeleteMapping("/calendar/{cal_id}/delete")
	@Operation(
		summary = "팀 일정 삭제",
		responses = {
			@ApiResponse(responseCode = "200", description = "성공",
				content = @Content(schema = @Schema(implementation = CalendarEditResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 일정(NEI), 존재하지 않는 사용자(NU)",
				content = @Content(schema = @Schema(implementation = ResponseDto.class)))
		}
	)
	public ResponseEntity<? super CalendarEditResponseDto> deleteCalendar(
		@PathVariable Long cal_id,
		@AuthenticationPrincipal CustomUserDetails userDetails
	)  //user가 팀의 멤버인지 확인?
	{
		return teamService.deleteCalendar(cal_id, userDetails.getUser().getUserId() );
	}

	//팀 일정 수정
	@PutMapping("/calendar/{cal_id}/modify")
	@Operation(
		summary = "팀 일정 수정",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			required = true,
			content = @Content(
				schema = @Schema(implementation = CalendarAddRequestDto.class)
			)
		),
		responses = {
			@ApiResponse(responseCode = "200", description = "성공",
				content = @Content(schema = @Schema(implementation = CalendarEditResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 일정(NEI), 존재하지 않는 사용자(NU), 수정할 데이터가 null(MRD)",
				content = @Content(schema = @Schema(implementation = ResponseDto.class)))
		}
	)
	public ResponseEntity<? super CalendarEditResponseDto> modifyCalendar(
		@RequestBody CalendarAddRequestDto calendarAddRequestDto, //날짜, 내용
		@PathVariable Long cal_id,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return teamService.modifyCalendar(cal_id, userDetails.getUser().getUserId(), calendarAddRequestDto);
	}
}
