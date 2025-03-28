package backend.spring.service;

import backend.spring.domain.TechStack;
import backend.spring.domain.User;
import backend.spring.dto.SignupRequest;
import backend.spring.dto.UserProfileResponse;
import backend.spring.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerUser(SignupRequest request) {
        User user = new User();
        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setLocation(request.getLocation());
        user.setSns(request.getSns());
        user.setBio(request.getBio());

        List<TechStack> techStacks = new ArrayList<>();
        for (String name : request.getTechStacks()) {
            TechStack ts = new TechStack();
            ts.setName(name);
            ts.setUser(user);
            techStacks.add(ts);
        }
        user.setTechStacks(techStacks);

        userRepository.save(user);
    }

    @Transactional
    public UserProfileResponse getUserProfile(String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> techs = user.getTechStacks().stream()
                .map(TechStack::getName)
                .toList();

        return new UserProfileResponse(user.getNickname(), user.getBio(), techs);
    }

    @Transactional

    public UserProfileResponse getMyProfile() {
        String currentNickname = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserProfile(currentNickname);
    }

    @Transactional
    public void updateMyProfile(String bio, List<String> techStacks) {
        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setBio(bio);
        user.getTechStacks().clear();

        List<TechStack> newStacks = new ArrayList<>();
        for (String name : techStacks) {
            TechStack ts = new TechStack();
            ts.setName(name);
            ts.setUser(user);
            newStacks.add(ts);
        }
        user.getTechStacks().addAll(newStacks);
    }
}
