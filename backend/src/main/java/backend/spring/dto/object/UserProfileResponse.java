package backend.spring.dto.object;

import backend.spring.entity.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserProfileResponse {
    private String nickname;
    private String bio;
    private String location;
    private String sns;
    private List<String> techStacks;

    public UserProfileResponse(User user) {
        this.nickname = user.getNickname();
        this.bio = user.getBio();
        this.location = user.getLocation().name();
        this.sns = user.getSns();
        this.techStacks = user.getTechStacks()
                .stream()
                .map(stack -> stack.getName().name()) 
                .collect(Collectors.toList());
    }
}
