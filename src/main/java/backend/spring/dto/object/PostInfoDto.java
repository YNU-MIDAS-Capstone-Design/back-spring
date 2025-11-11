package backend.spring.dto.object;

import lombok.Getter;

@Getter
public class PostInfoDto {
    private Long id;
    private String title;

    public PostInfoDto (Long id, String title){
        this.id = id;
        this.title = title;
    }
}
