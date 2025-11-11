package backend.spring.service;

import backend.spring.dto.object.PostInfoDto;
import backend.spring.dto.object.UserInfoDto;
import backend.spring.dto.response.AdminDelPostResponseDto;
import backend.spring.dto.response.AdminPostSummaryResponseDto;
import backend.spring.dto.response.AdminUserInfoResponseDto;
import backend.spring.dto.response.ResponseDto;
import backend.spring.entity.Project;
import backend.spring.entity.User;
import backend.spring.repository.ProjectRepository;
import backend.spring.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Transactional  //지연 로딩을 위해 추가
    public ResponseEntity<? super AdminUserInfoResponseDto> getUsers(String nickname){
        if(!nickname.equals("admin")){
            return ResponseDto.not_existed_user();
        }

        // DB에서 모든 유저 가져오기
        List<User> userList = userRepository.findAll();

        // User -> UserInfoDto 변환
        List<UserInfoDto> userInfoList = new ArrayList<>();
        for (User user : userList) {
            UserInfoDto userInfo = new UserInfoDto(
                    user.getUserId(),
                    user.getNickname(),
                    user.getEmail(),
                    user.getBio(),
                    user.getLocation() != null ? user.getLocation().name() : null,
                    user.getSns(),
                    user.getMbti(),
                    user.getJob()
            );
            userInfoList.add(userInfo);
        }

        // 변환된 리스트를 응답으로 반환
        return AdminUserInfoResponseDto.success(userInfoList);
    }

    // 관리자: 프로젝트 목록 조회
    @Transactional
    public ResponseEntity<? super AdminPostSummaryResponseDto> getAllPosts(String nickname) {
        if(!nickname.equals("admin")){
            return ResponseDto.not_existed_user();
        }

        List<Project> posts = projectRepository.findAll();

        // User -> UserInfoDto 변환
        List<PostInfoDto> postInfoList = new ArrayList<>();
        for (Project project : posts) {
            PostInfoDto postInfo = new PostInfoDto(
                    project.getProjectId(),
                    project.getTitle()
            );
            postInfoList.add(postInfo);
        }

        return AdminPostSummaryResponseDto.success(postInfoList);
    }

    // 프로젝트 삭제
    @Transactional
    public ResponseEntity< ? super AdminDelPostResponseDto> deletePostById(String nickname, Long id) {
        if(!nickname.equals("admin")){
            return ResponseDto.not_existed_user();
        }

        if (!projectRepository.existsById(id)) {
            return AdminDelPostResponseDto.not_existed_post();
        }
        projectRepository.deleteById(id);

        return AdminDelPostResponseDto.success();
    }

}