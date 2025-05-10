package backend.spring.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import backend.spring.dto.request.myteams.*;
import backend.spring.dto.response.myteams.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import backend.spring.dto.object.ViewCalendarDto;
import backend.spring.dto.object.ViewMemberDto;
import backend.spring.dto.object.ViewTeamDto;
import backend.spring.dto.response.ResponseDto;
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
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class TeamService {

	private final UserRepository userRepository;
	private final TeamMemberRepository teamMemberRepository;
	private final TeamRepository teamRepository;
	private final TeamCalendarRepository teamCalendarRepository;
	private final FileService fileService;

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
				ViewTeamDto viewTeam = new ViewTeamDto(team.getTeamId(), team.getTeamName(), team.getTeamImage(), member.isOwner() );
				teamList.add(viewTeam);
			}
			return ViewTeamsResponseDto.success(teamList);
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	@Transactional //팀 생성시-> 팀이름,팀색상 저장
	public ResponseEntity<ResponseDto> createTeam(Long user_id, CreateTeamRequestDto dto){
		try{
			if(dto.getTeamName() == null || dto.getTeamName().isEmpty()){
				return ResponseDto.missing_required_data();
			}
			Optional<User> option = userRepository.findById(user_id);
			if(option.isEmpty()) {
				return ResponseDto.not_existed_user();
			}
			User user = option.get();

			Team team = new Team(dto.getTeamName(), dto.getTeamColor(), dto.getTeamColor());
			teamRepository.save(team);

			TeamMember team_owner = new TeamMember(user.getNickname(), true, user, team);
			teamMemberRepository.save(team_owner);

			return ResponseDto.successResponse();
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	//팀 이미지 추가
	@Transactional
	public ResponseEntity<? super TeamImgResponseDto> uploadImage(MultipartFile file, Long team_id, Long user_id){
		try{
			Optional<User> option = userRepository.findById(user_id);
			if(option.isEmpty()) {
				return ResponseDto.not_existed_user();
			}

			String fileName = fileService.file_upload("team_image", file);
			if( fileName == null){
				return ResponseDto.databaseError(); //파일 저장 실패
			} else{
				// fileName만 db에 저장하면 됨
				Optional<Team> options = teamRepository.findById(team_id);
				if(options.isEmpty()) { //팀이 존재하는지 확인
					return TeamImgResponseDto.not_existed_team();
				}
				Team team = options.get();

				String image_url = "http://localhost:8080/api/view/team_image," + fileName;
				team.setTeamImage(image_url); // url로 저장

				return TeamImgResponseDto.success(image_url);
			}
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}
	//팀 이미지 삭제
	@Transactional
	public ResponseEntity<? super TeamImgResponseDto> deleteTeamImg(Long team_id, Long user_id){
		try{
			Optional<User> option = userRepository.findById(user_id);
			if(option.isEmpty()) {
				return ResponseDto.not_existed_user();
			}
			Optional<Team> options = teamRepository.findById(team_id);
			if(options.isEmpty()) { //팀이 존재하는지 확인
				return TeamImgResponseDto.not_existed_team();
			}
			Team team = options.get();

			String imagePath = team.getTeamImage();
			String fileName;
			// 이미지 URL 형식이면 파일 이름만 추출
			String prefix = "http://localhost:8080/api/view/team_image,";
			if (imagePath != null && imagePath.startsWith(prefix)) {
				fileName = imagePath.substring(prefix.length());
			} else {
				return ResponseDto.missing_required_data();
			}

			if(fileService.file_delete("team_image", fileName)){
				String color = team.getTeamColor();
				team.setTeamImage(color); //이미지가 삭제 되었으므로 이미지를 컬러로 바꿈.
				return TeamImgResponseDto.success(color); //프론트에게 이미지 반환
			} else{
				return ResponseDto.databaseError();  //파일 삭제 실패
			}
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}


	@Transactional
	public ResponseEntity<? super CreateMemberResponseDto> createMember(Long user_id, Long team_id, CreateMemberRequestDto dto){
		try{
			if(dto.getNickname() == null || dto.getNickname().isEmpty()){
				return ResponseDto.missing_required_data();
			}

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

			Optional<User> member_option = userRepository.findByNickname(dto.getNickname());
			if(member_option.isEmpty()) {
				return CreateMemberResponseDto.not_existed_nickname();
			}
			User member = member_option.get();

			TeamMember new_member = new TeamMember(dto.getNickname(), false, member, team);
			teamMemberRepository.save(new_member);

			return CreateMemberResponseDto.success(dto.getNickname());
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}
	@Transactional
	public ResponseEntity<? super DelMemberResponseDto> deleteMember(Long team_id, Long member_id, Long user_id){
		try{
			Optional<User> option = userRepository.findById(user_id);
			if(option.isEmpty()) {
				return ResponseDto.not_existed_user();
			}
			User user = option.get();

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
				return DelMemberResponseDto.not_match_user();
			}

			//팀원 삭제
			Optional<TeamMember> member_option = teamMemberRepository.findById(member_id);
			if(member_option.isEmpty()){
				return DelMemberResponseDto.not_existed_member();
			}
			teamMemberRepository.deleteById(member_id);

			return DelMemberResponseDto.success();
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

			//팀의 멤버 리스트 불러오기
			List<TeamMember> memberList = teamMemberRepository.findAllByTeam(team);
			if(memberList.isEmpty()) {
				return GetMemberResponseDto.zero_member();
			}
			String image_url;
			List<ViewMemberDto> members = new ArrayList<>();
			for(TeamMember member : memberList){
				User member_user = member.getUser();
				image_url = "http://localhost:8080/api/view/profile_image," + member_user.getProfileImageFilename();
				ViewMemberDto mem = new ViewMemberDto(member.getMemberId(), member.getMemberName(), member.isOwner(), member.getTeamRole(), image_url);
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
	public ResponseEntity<? super CalendarResponseDto> viewCalendar(Long team_id, Long user_id, int year, int month){
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

			// year와 month에 해당하는 날짜를 가진 것만 calendars 리스트 중에서 반환
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
