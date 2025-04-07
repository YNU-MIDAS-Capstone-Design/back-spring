package backend.spring.dto.object;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCommentListItem{
    private long userId;
    private String nickname;
    private String content;
    private String createdAt;

    public ProjectCommentListItem( long userId, String nickname, String content, String createdAt) {
        this.userId = userId;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.content = content;
    }
}