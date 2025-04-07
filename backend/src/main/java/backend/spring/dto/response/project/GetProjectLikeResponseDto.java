package backend.spring.dto.response.project;

import backend.spring.dto.response.ResponseDto;
import backend.spring.dto.object.ProjectLikeListItem;
import backend.spring.repository.resultSet.GetProjectLikeListResultSet;
import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetProjectLikeResponseDto extends ResponseDto {

    private List<ProjectLikeListItem> projectLikeList;

    public GetProjectLikeResponseDto(List<GetProjectLikeListResultSet> resultSets) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.projectLikeList = ProjectLikeListItem.copyList(resultSets);
    }

    public static ResponseEntity<GetProjectLikeResponseDto> success(List<GetProjectLikeListResultSet> resultSets) {
        GetProjectLikeResponseDto result = new GetProjectLikeResponseDto(resultSets);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistProject() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_PROJECT, ResponseMessage.NOT_EXISTED_PROJECT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
