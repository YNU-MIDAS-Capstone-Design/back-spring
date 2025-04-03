package backend.spring.dto.request;

import backend.spring.entity.enums.Location;
import backend.spring.entity.enums.Stack;
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
    private Location location;
    private String sns;
    private List<Stack> techStacks;
}
