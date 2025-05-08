package backend.spring.service;

import backend.spring.dto.request.SignupRequest;
import backend.spring.dto.request.UpdateProfileRequest;
import backend.spring.dto.object.UserProfileResponse;
import backend.spring.dto.response.SignupResponseDto;
import backend.spring.entity.TechStack;
import backend.spring.entity.User;
import backend.spring.entity.enums.Stack;
import backend.spring.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       FileService fileService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileService = fileService;
    }

    @Transactional
    public ResponseEntity<SignupResponseDto> registerUser(SignupRequest request) {
        if (userRepository.existsByNickname(request.getNickname())) {
            return SignupResponseDto.duplicateNickname();
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return SignupResponseDto.duplicateEmail();
        }

        List<TechStack> techStacks = new ArrayList<>();

        User user = User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .location(request.getLocation())
                .sns(request.getSns())
                .bio(request.getBio())
                .techStacks(techStacks)
                .build();

        for (Stack stack : request.getTechStacks()) {
            TechStack ts = new TechStack();
            ts.setName(stack);
            ts.setUser(user);
            techStacks.add(ts);
        }

        userRepository.save(user);
        return SignupResponseDto.signupSuccess();
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserProfileResponse(user);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getMyProfile(String username) {
        User user = userRepository.findByNickname(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserProfileResponse(user);
    }

    @Transactional
    public void updateMyProfile(UpdateProfileRequest request, String username) {
        User user = userRepository.findByNickname(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setBio(request.getBio());
        user.setLocation(request.getLocation());
        user.setSns(request.getSns());
        user.getTechStacks().clear();

        List<TechStack> newStacks = new ArrayList<>();
        for (Stack stack : request.getTechStacks()) {
            TechStack ts = new TechStack();
            ts.setName(stack);
            ts.setUser(user);
            newStacks.add(ts);
        }
        user.getTechStacks().addAll(newStacks);
    }

    /**
     * 프로필 이미지 업로드 후 URL을 User 엔티티에 저장
     */
    @Transactional
    public void updateMyProfileImage(MultipartFile imageFile, String username) {
        User user = userRepository.findByNickname(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 파일 저장 (storage/profile 디렉토리에 저장) 및 파일명 반환
        String filename = fileService.file_upload("profile_image", imageFile);
        // URL 조합
        String imageUrl = "/static/profile/" + filename;
        user.setProfileImageUrl(imageUrl);
    }

    @Transactional(readOnly = true)
    public boolean isEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean isNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
