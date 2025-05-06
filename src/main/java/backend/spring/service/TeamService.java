package backend.spring.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import backend.spring.dto.object.ViewCalendarDto;
import backend.spring.dto.object.ViewMemberDto;
import backend.spring.dto.object.ViewTeamDto;
import backend.spring.dto.request.myteams.CalendarAddRequestDto;
import backend.spring.dto.request.myteams.CalendarRequestDto;
import backend.spring.dto.request.myteams.CreateMemberRequestDto;
import backend.spring.dto.request.myteams.MemberStackRequestDto;
import backend.spring.dto.request.myteams.TeamNameRequestDto;
import backend.spring.dto.response.ResponseDto;
import backend.spring.dto.response.myteams.CalendarEditResponseDto;
import backend.spring.dto.response.myteams.CalendarResponseDto;
import backend.spring.dto.response.myteams.CreateMemberResponseDto;
import backend.spring.dto.response.myteams.CreateTeamResponseDto;
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
import jakarta.transaction.Transactional;  //지연 로딩을 사용하는 메서드에 추가
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {

	private final UserRepository userRepository;
	private final TeamMemberRepository teamMemberRepository;
	private final TeamRepository teamRepository;
	private final TeamCalendarRepository teamCalendarRepository;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //년 월 일

	@Transactional  //지연 로딩을 위해 추가
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
				ViewTeamDto viewTeam = new ViewTeamDto(team.getTeamId(), team.getTeamName(), member.isOwner() );
				teamList.add(viewTeam);
			}
			return ViewTeamsResponseDto.success(teamList);
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	@Transactional
	public ResponseEntity<? super CreateTeamResponseDto> createTeam(Long user_id, TeamNameRequestDto dto){
		try{
			if(dto.getTeamName() == null || dto.getTeamName().isEmpty()){
				return ResponseDto.missing_required_data();
			}
			Optional<User> option = userRepository.findById(user_id);
			if(option.isEmpty()) {
				return ResponseDto.not_existed_user();
			}
			User user = option.get();

			Team team = new Team(dto.getTeamName());
			teamRepository.save(team);

			TeamMember team_owner = new TeamMember(user.getNickname(), true, user, team);
			teamMemberRepository.save(team_owner);

			return CreateTeamResponseDto.success();
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	@Transactional
	public ResponseEntity<? super CreateMemberResponseDto> createMember(Long user_id, Long team_id, CreateMemberRequestDto dto){
		try{
			Optional<User> option = userRepository.findById(user_id);
			if(option.isEmpty()) {
				return ResponseDto.not_existed_user();
			}
			User user = option.get();

			Optional<Team> options = teamRepository.findById(team_id);
			if(options.isEmpty()) {
				return CreateMemberResponseDto.not_existed_team();
			}
			Team team = options.get(); //팀이 존재하는지 확인

			//사용자가 팀장이 맞는지 확인
			boolean owner = true;
			List<TeamMember> teamMemberList = teamMemberRepository.findAllByUser(user);
			for(TeamMember member : teamMemberList){
				if(member.getTeam().getTeamId().equals(team_id) && member.isOwner()){
					owner = false; //팀장이 맞다면 false
					break;
				}
			}
			if(owner){ //팀장과 매칭하지 않을 때
				return CreateMemberResponseDto.not_match_user();
			}

			if(dto.getNickname() == null || dto.getNickname().isEmpty()){
				return ResponseDto.missing_required_data();
			}
			Optional<User> member_option = userRepository.findByNickname(dto.getNickname());
			if(member_option.isEmpty()) {
				return CreateMemberResponseDto.not_existed_nickname();
			}
			User member = option.get();

			TeamMember new_member = new TeamMember(dto.getNickname(), false, member, team);
			teamMemberRepository.save(new_member);

			return CreateMemberResponseDto.success();
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	@Transactional
	public ResponseEntity<? super TeamNameResponseDto> changeTeamName(Long team_id, Long user_id, TeamNameRequestDto dto){
		try{
			if(dto.getTeamName() == null || dto.getTeamName().isEmpty()){
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
				if(member.getTeam().getTeamId().equals(team_id) && member.isOwner()){
					owner = false; //팀장이 맞다면 false
					break;
				}
			}
			if(owner){ //팀장과 매칭하지 않을 때
				return TeamNameResponseDto.not_match_user();
			}
			team.setTeamName(dto.getTeamName()); //팀 명을 변경

			return TeamNameResponseDto.success();
		}catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	@Transactional
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
				ViewMemberDto mem = new ViewMemberDto(member.getMemberId(), member.getMemberName(), member.isOwner(), member.getTeamRole());
				members.add(mem);
			}
			return GetMemberResponseDto.success(members);
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	@Transactional
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
				if(teamMember.getMemberId().equals(member_id)){
					mem = false;
					break;
				}
			}
			if(mem){
				return MemberStackResponseDto.not_match_user();
			}
			member.setTeamRole(dto.getTeam_role());//팀 역할 변경

			return MemberStackResponseDto.success();
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	@Transactional
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
					LocalDateTime date = calendar.getCalDate();
					return date.getYear() == year && date.getMonthValue() == month;
				})
				.toList();
			if (filteredCalendars.isEmpty()) {
				return CalendarResponseDto.not_existed_content(); // 해당 월에 일정이 없을 경우
			}

			List<ViewCalendarDto> dates = new ArrayList<>();
			for(TeamCalendar cal : filteredCalendars){
				String cal_date = cal.getCalDate().format(formatter);
				ViewCalendarDto view_cal = new ViewCalendarDto(cal.getCalId(), cal_date, cal.getContent());
				dates.add(view_cal);
			}

			return CalendarResponseDto.success(year, month, dates);
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	@Transactional
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

			LocalDate date = LocalDate.parse(dto.getCal_date(), formatter);
			LocalDateTime dateTime = date.atStartOfDay();
			TeamCalendar new_cal = new TeamCalendar(dateTime, dto.getContent(), team);
			teamCalendarRepository.save(new_cal); //새로운 일정 저장

			return CalendarEditResponseDto.success();
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	@Transactional
	public ResponseEntity<? super CalendarEditResponseDto> modifyCalendar(Long cal_id, Long user_id, CalendarAddRequestDto dto){
		try{
			if(dto.getContent() == null || dto.getContent().isEmpty()){
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
			calendar.setContent(dto.getContent());  //일정 수정

			return CalendarEditResponseDto.success();
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	@Transactional
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
