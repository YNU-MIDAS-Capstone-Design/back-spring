package backend.spring.service;

import backend.spring.dto.object.ViewProjectDto;
import backend.spring.dto.request.SignupRequest;
import backend.spring.dto.request.UpdateProfileRequest;
import backend.spring.dto.object.UserProfileResponse;
import backend.spring.dto.response.SignupResponseDto;
import backend.spring.entity.TechStack;
import backend.spring.entity.User;
import backend.spring.entity.enums.Stack;
import backend.spring.entity.Project;


import backend.spring.repository.ProjectApplicantRepository;
import backend.spring.repository.ProjectLikeRepository;
import backend.spring.repository.ProjectRepository;
import backend.spring.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    private final ProjectLikeRepository projectLikeRepository;
    private final ProjectApplicantRepository projectApplicantRepository;
    private final ProjectRepository projectRepository;



    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       FileService fileService,
                       ProjectLikeRepository projectLikeRepository,
                       ProjectApplicantRepository projectApplicantRepository,
                       ProjectRepository projectRepository) {
        this.userRepository             = userRepository;
        this.passwordEncoder            = passwordEncoder;
        this.fileService                = fileService;
        this.projectLikeRepository      = projectLikeRepository;
        this.projectApplicantRepository = projectApplicantRepository;
        this.projectRepository          = projectRepository;
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

        Optional.ofNullable(request.getTechStacks())
                .orElse(List.of())
                .forEach(stackEnum -> {
                    TechStack ts = new TechStack(stackEnum);
                    ts.setUser(user);
                    techStacks.add(ts);
                });

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

        if (request.getBio() != null)      user.setBio(request.getBio());
        if (request.getLocation() != null) user.setLocation(request.getLocation());
        if (request.getSns() != null)      user.setSns(request.getSns());

        if (request.getTechStacks() != null && !request.getTechStacks().isEmpty()) {
            if (user.getTechStacks() == null) {
                user.setTechStacks(new ArrayList<>());
            } else {
                user.getTechStacks().clear();
            }

            for (Stack stackEnum : request.getTechStacks()) {
                TechStack ts = new TechStack(stackEnum);
                ts.setUser(user);
                user.getTechStacks().add(ts);
            }
        }
    }


    /**
     * 프로필 이미지 업로드 후 filename을 User 엔티티에 저장
     */
    @Transactional
    public void updateMyProfileImage(MultipartFile imageFile, String username) {
        if (imageFile == null || imageFile.isEmpty()) {
            throw new RuntimeException("업로드할 파일을 선택해 주세요");
        }

        User user = userRepository.findByNickname(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String filename;
        try {
            filename = fileService.file_upload("profile_image", imageFile);
        } catch (Exception e) {
            throw new RuntimeException("프로필 이미지 저장에 실패했습니다", e);
        }
        if (filename == null || filename.isBlank()) {
            throw new RuntimeException("프로필 이미지 저장에 실패했습니다");
        }

        user.setProfileImageFilename(filename);
    }

    @Transactional
    public void deleteMyProfileImage(String username) {
        User user = userRepository.findByNickname(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String filename = user.getProfileImageFilename();
        if (filename != null && !filename.isBlank()) {
            boolean deleted = fileService.file_delete("profile_image", filename);
            if (!deleted) {
                throw new RuntimeException("Failed to delete profile image file");
            }
            //  DB 초기화
            user.setProfileImageFilename(null);
        }
    }


    @Transactional(readOnly = true)
    public boolean isEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean isNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public List<ViewProjectDto> getLikedProjects(String username) {
        User me = userRepository.findByNickname(username)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        return projectLikeRepository.findAllByUser(me).stream()
                .map(pl -> {
                    Project p = pl.getProject();
                    List<Stack> stacks = p.getStackList().stream()
                            .map(ps -> ps.getStack())
                            .toList();
                    return new ViewProjectDto(
                            p.getProjectId(),
                            p.getTitle(),
                            p.getContent(),
                            stacks
                    );
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ViewProjectDto> getAppliedProjects(String username) {
        User me = userRepository.findByNickname(username)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        return projectApplicantRepository.findAllByUser(me).stream()
                .map(pa -> {
                    Project p = pa.getProject();
                    List<Stack> stacks = p.getStackList().stream()
                            .map(ps -> ps.getStack())
                            .toList();
                    return new ViewProjectDto(
                            p.getProjectId(),
                            p.getTitle(),
                            p.getContent(),
                            stacks
                    );
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ViewProjectDto> getMyProjects(String username) {
        User me = userRepository.findByNickname(username)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        return projectRepository.findAllByUser(me).stream()
                .map(p -> {
                    List<Stack> stacks = p.getStackList().stream()
                            .map(ps -> ps.getStack())
                            .toList();
                    return new ViewProjectDto(
                            p.getProjectId(),
                            p.getTitle(),
                            p.getContent(),
                            stacks
                    );
                })
                .toList();
    }


}
