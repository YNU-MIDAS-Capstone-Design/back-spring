package backend.spring.controller;

import org.springframework.http.ResponseEntity;
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
import backend.spring.dto.response.myteams.CalendarEditResponseDto;
import backend.spring.dto.response.myteams.CalendarResponseDto;
import backend.spring.dto.response.myteams.GetMemberResponseDto;
import backend.spring.dto.response.myteams.MemberStackResponseDto;
import backend.spring.dto.response.myteams.TeamNameResponseDto;
import backend.spring.dto.response.myteams.ViewTeamsResponseDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myteams/team")
public class TeamController {

	//나의 팀 페이지 불러오기(팀: 팀이름, 역할)
	@GetMapping("")
	public ResponseEntity<ViewTeamsResponseDto> getMyTeam(
		@AuthenticationPrincipal CustomUserDetails userDetails) {

	}

	//팀 이름 바꾸기(팀장만)
	@PostMapping("/name/{team_id}")
	public ResponseEntity<TeamNameResponseDto> changeTeamName(
		@RequestBody TeamNameRequestDto teamNameRequestDto,
		@PathVariable Long team_id,
		@AuthenticationPrincipal CustomUserDetails userDetails) {

	}

	//팀 멤버 불러오기
	@GetMapping("/{team_id}/member")
	public ResponseEntity<GetMemberResponseDto> getMember(
		@PathVariable Long team_id){

	}

	//팀원 스택 바꾸기(팀원 자신만)
	@PostMapping("/member/position/{member_id}")
	public ResponseEntity<MemberStackResponseDto> changePosition(
		@RequestBody MemberStackRequestDto memberStackRequestDto,
		@PathVariable Long member_id,
		@AuthenticationPrincipal CustomUserDetails userDetails){

	}

	//팀 일정 month 별로 불러오기
	@GetMapping("/{team_id}/calendar")
	public ResponseEntity<CalendarResponseDto> viewCalendar(
		@RequestBody CalendarRequestDto calendarRequestDto,
		@PathVariable Long team_id,
		@AuthenticationPrincipal CustomUserDetails userDetails){

	}

	//팀 일정 추가
	@PostMapping("/{team_id}/calendar/add")
	public ResponseEntity<CalendarEditResponseDto> addCalendar(
		@RequestBody CalendarAddRequestDto calendarAddRequestDto,
		@PathVariable Long team_id,
		@AuthenticationPrincipal CustomUserDetails userDetails){

	}

	//팀 일정 삭제
	@DeleteMapping("/calendar/{cal_id}/delete")
	public ResponseEntity<CalendarEditResponseDto> deleteCalendar(
		@PathVariable Long cal_id,
		@AuthenticationPrincipal CustomUserDetails userDetails){

	}

	//팀 일정 수정
	@PutMapping("/calendar/{cal_id}/modify")
	public ResponseEntity<CalendarEditResponseDto> deleteCalendar(
		@PathVariable Long cal_id,
		@AuthenticationPrincipal CustomUserDetails userDetails){

	}
}
