package backend.spring.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignupRequest {
    private String nickname;
    private String email;
    private String password;
    private String bio;
    private String location;
    private String sns;
    private List<String> techStacks;
}
