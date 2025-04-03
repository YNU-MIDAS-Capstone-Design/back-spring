package backend.spring.dto.request;

import backend.spring.entity.enums.Location;
import backend.spring.entity.enums.Stack;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdateProfileRequest {
    private String bio;
    private Location location;
    private String sns;
    private List<Stack> techStacks;
}
