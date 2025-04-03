package backend.spring.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateProfileRequest {
    private String bio;
    private String location;
    private String sns;
    private List<String> techStacks;
}
