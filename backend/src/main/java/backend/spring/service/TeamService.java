package backend.spring.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import backend.spring.dto.object.ViewCalendarDto;
import backend.spring.dto.object.ViewMemberDto;
import backend.spring.dto.object.ViewTeamDto;
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
import backend.spring.entity.Team;
import backend.spring.entity.TeamCalendar;
import backend.spring.entity.TeamMember;
import backend.spring.entity.User;
import backend.spring.repository.TeamCalendarRepository;
import backend.spring.repository.TeamMemberRepository;
import backend.spring.repository.TeamRepository;
import backend.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {

	private final UserRepository userRepository;
	private final TeamMemberRepository teamMemberRepository;
	private final TeamRepository teamRepository;
	private final TeamCalendarRepository teamCalendarRepository;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //년 월 일

	public ResponseEntity<? super ViewTeamsResponseDto> viewMyTeam(Long user_id){
		try{
			Optional<User> option = userRepository.findById(user_id);
			if(option.isEmpty()) {
				return ResponseDto.not_existed_user();
			}
			User user = option.get();

			List<TeamMember> teamMemberList = teamMemberRepository.findAllByUser(user);
			if(teamMemberList.isEmpty()) {  //속한 팀이 존재하지 않을 때
				return ViewTeamsResponseDto.zero_team();
			}

			//보여줄 팀 리스트 생성
			List<ViewTeamDto> teamList = new ArrayList<>();
			for(TeamMember member : teamMemberList){
				Team team = member.getTeam();
				ViewTeamDto viewTeam = new ViewTeamDto(team.getTeam_id(), team.getTeam_name(), member.isOwner() );
				teamList.add(viewTeam);
			}
			return ViewTeamsResponseDto.success(teamList);
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	public ResponseEntity<? super TeamNameResponseDto> changeTeamName(Long team_id, Long user_id, TeamNameRequestDto dto){
		try{
			if(dto.getTeamName().isEmpty()){
				return ResponseDto.missing_required_data();
			} //수정할 데이터가 null이 아닌지 확인

			Optional<User> option = userRepository.findById(user_id);
			if(option.isEmpty()) {
				return ResponseDto.not_existed_user();
			}
			User user = option.get(); //존재하는 user인지 확인

			Optional<Team> options = teamRepository.findById(team_id);
			if(options.isEmpty()) {
				return TeamNameResponseDto.not_existed_team();
			}
			Team team = options.get(); //팀이 존재하는지 확인

			//사용자가 팀장이 맞는지 확인
			boolean owner = true;
			List<TeamMember> teamMemberList = teamMemberRepository.findAllByUser(user);
			for(TeamMember member : teamMemberList){
				if(member.getTeam().getTeam_id().equals(team_id) && member.isOwner()){
					owner = false; //팀장이 맞다면 false
					break;
				}
			}
			if(owner){ //팀장과 매칭하지 않을 때
				return TeamNameResponseDto.not_match_user();
			}
			team.setTeam_name(dto.getTeamName()); //팀 명을 변경

			return TeamNameResponseDto.success();
		}catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	public ResponseEntity<? super GetMemberResponseDto> getMember(Long team_id, Long user_id){
		try{
			Optional<User> option = userRepository.findById(user_id);
			if(option.isEmpty()) {
				return ResponseDto.not_existed_user();
			} //user 존재 확인

			Optional<Team> options = teamRepository.findById(team_id);
			if(options.isEmpty()) {
				return GetMemberResponseDto.not_existed_team();
			}
			Team team = options.get(); //팀이 존재하는지 확인

			List<TeamMember> memberList = teamMemberRepository.findAllByTeam(team);
			if(memberList.isEmpty()) {
				return GetMemberResponseDto.zero_member();
			}

			List<ViewMemberDto> members = new ArrayList<>();
			for(TeamMember member : memberList){
				ViewMemberDto mem = new ViewMemberDto(member.getMember_id(), member.getMember_name(), member.isOwner(), member.getTeam_role());
				members.add(mem);
			}
			return GetMemberResponseDto.success(members);
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	public ResponseEntity<? super MemberStackResponseDto> changePosition(Long member_id, Long user_id, MemberStackRequestDto dto){
		try{
			if(dto.getTeam_role() == null){
				return ResponseDto.missing_required_data();
			} //수정할 데이터가 null이 아닌지 확인

			Optional<User> option = userRepository.findById(user_id);
			if(option.isEmpty()) {
				return ResponseDto.not_existed_user();
			}
			User user = option.get(); //존재하는 user인지 확인

			Optional<TeamMember> options = teamMemberRepository.findById(member_id);
			if(options.isEmpty()) {
				return MemberStackResponseDto.not_existed_member();
			}
			TeamMember member = options.get(); //멤버 존재하는지 확인

			//사용자와 멤버가 일치하는지 확인
			//사용자의 team리스트 중에 member_id와 같은 member_id가 있는지 확인
			List<TeamMember> teamList = teamMemberRepository.findAllByUser(user);
			boolean mem = true;
			for(TeamMember teamMember : teamList){
				if(teamMember.getMember_id().equals(member_id)){
					mem = false;
					break;
				}
			}
			if(mem){
				return MemberStackResponseDto.not_match_user();
			}
			member.setTeam_role(dto.getTeam_role());//팀 역할 변경

			return MemberStackResponseDto.success();
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	public ResponseEntity<? super CalendarResponseDto> viewCalendar(Long team_id, Long user_id, CalendarRequestDto dto){
		try{
			Optional<User> option = userRepository.findById(user_id);
			if(option.isEmpty()) {
				return ResponseDto.not_existed_user();
			} //user 존재 확인

			Optional<Team> options = teamRepository.findById(team_id);
			if(options.isEmpty()) {
				return CalendarResponseDto.not_existed_team();
			}
			Team team = options.get(); //팀이 존재하는지 확인

			List<TeamCalendar> calendars = teamCalendarRepository.findAllByTeam(team);
			if(calendars.isEmpty()){
				return CalendarResponseDto.not_existed_content(); //일정이 하나도 없을 경우
			}

			// year와 month에 해당하는 날짜를 가진 것만 calendars 리스트 중에서 저장하도록 하는 로직
			int year = dto.getYear();
			int month = dto.getMonth();
			List<TeamCalendar> filteredCalendars = calendars.stream()
				.filter(calendar -> {
					LocalDateTime date = calendar.getCal_date();
					return date.getYear() == year && date.getMonthValue() == month;
				})
				.toList();
			if (filteredCalendars.isEmpty()) {
				return CalendarResponseDto.not_existed_content(); // 해당 월에 일정이 없을 경우
			}

			List<ViewCalendarDto> dates = new ArrayList<>();
			for(TeamCalendar cal : filteredCalendars){
				String cal_date = cal.getCal_date().format(formatter);
				ViewCalendarDto view_cal = new ViewCalendarDto(cal.getCal_id(), cal_date, cal.getContent());
				dates.add(view_cal);
			}

			return CalendarResponseDto.success(year, month, dates);
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	public ResponseEntity<? super CalendarEditResponseDto> addCalendar(Long team_id, Long user_id, CalendarAddRequestDto dto){
		try{
			if(dto.getCal_date() == null || dto.getCal_date().isEmpty()){
				return ResponseDto.missing_required_data();
			} //수정할 데이터가 null인지 확인

			Optional<User> option = userRepository.findById(user_id);
			if(option.isEmpty()) {
				return ResponseDto.not_existed_user();
			} //user 존재 확인

			Optional<Team> options = teamRepository.findById(team_id);
			if(options.isEmpty()) {
				return CalendarEditResponseDto.not_existed_team();
			}
			Team team = options.get(); //팀이 존재하는지 확인

			LocalDateTime date = LocalDateTime.parse(dto.getCal_date(), formatter);
			TeamCalendar new_cal = new TeamCalendar(date, dto.getContent(), team);
			teamCalendarRepository.save(new_cal); //새로운 일정 저장

			return CalendarEditResponseDto.success();
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	public ResponseEntity<? super CalendarEditResponseDto> modifyCalendar(Long cal_id, Long user_id, CalendarAddRequestDto dto){
		try{
			if(dto.getCal_date() == null || dto.getCal_date().isEmpty()){
				return CalendarEditResponseDto.missing_required_data();
			} //수정할 데이터가 null인지 확인

			Optional<User> option = userRepository.findById(user_id);
			if(option.isEmpty()) {
				return ResponseDto.not_existed_user();
			} //user 존재 확인

			Optional<TeamCalendar> options = teamCalendarRepository.findById(cal_id);
			if(options.isEmpty()) {
				return CalendarEditResponseDto.not_existed_cal();
			}
			TeamCalendar calendar = options.get(); //일정이 존재하는지 확인
			LocalDateTime date = LocalDateTime.parse(dto.getCal_date(), formatter);
			calendar.setCal_date(date);
			calendar.setContent(dto.getContent());  //일정 수정

			return CalendarEditResponseDto.success();
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	public ResponseEntity<? super CalendarEditResponseDto> deleteCalendar(Long cal_id, Long user_id){
		try{
			Optional<User> option = userRepository.findById(user_id);
			if(option.isEmpty()) {
				return ResponseDto.not_existed_user();
			} //user 존재 확인

			Optional<TeamCalendar> options = teamCalendarRepository.findById(cal_id);
			if(options.isEmpty()) {
				return CalendarEditResponseDto.not_existed_cal();
			} //해당 일정이 존재하지 않을시

			teamCalendarRepository.deleteById(cal_id); //일정 삭제

			return CalendarEditResponseDto.success();
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

}
