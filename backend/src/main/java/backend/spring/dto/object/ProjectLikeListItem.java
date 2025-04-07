package backend.spring.dto.object;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.ArrayList;
import backend.spring.repository.resultSet.GetProjectLikeListResultSet;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectLikeListItem{
    private long userId;
    private String nickname;

    public ProjectLikeListItem(GetProjectLikeListResultSet resultSet) {
        this.userId = resultSet.getUserId();
        this.nickname = resultSet.getNickname();
    }
    public static List<ProjectLikeListItem> copyList(List<GetProjectLikeListResultSet> resultSets) {
        List<ProjectLikeListItem> list = new ArrayList<>();
        for(GetProjectLikeListResultSet resultSet: resultSets){
            ProjectLikeListItem projectLikeListItem = new ProjectLikeListItem(resultSet);
            list.add(projectLikeListItem);
        }
        return list;

    }

}