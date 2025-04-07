package backend.spring.dto.object;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.ArrayList;
import backend.spring.repository.resultSet.GetProjectCommentListResultSet;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCommentListItem{
    //private long userId;
    private String nickname;
    private String content;
    private String createdAt;

    public ProjectCommentListItem(GetProjectCommentListResultSet resultSet) {
        this.nickname = resultSet.getNickname();
        this.createdAt = resultSet.getCreatedAt();
        this.content = resultSet.getContent();
    }

    public static List<ProjectCommentListItem> copyList(List<GetProjectCommentListResultSet> resultSets){
        List<ProjectCommentListItem> list = new ArrayList<>();
        for(GetProjectCommentListResultSet resultSet : resultSets){
            ProjectCommentListItem projectCommentListItem = new ProjectCommentListItem(resultSet);
            list.add(projectCommentListItem);
        }
        return list;
    }
}