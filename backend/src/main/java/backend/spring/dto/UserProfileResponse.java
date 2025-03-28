package backend.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserProfileResponse {
    private String nickname;
    private String bio;
    private List<String> techStacks;
}
