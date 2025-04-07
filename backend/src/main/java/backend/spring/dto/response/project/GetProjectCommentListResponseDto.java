package backend.spring.dto.response.project;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.response.ResponseDto;
import backend.spring.dto.object.ProjectCommentListItem;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetProjectCommentListResponseDto extends ResponseDto {

    private List<ProjectCommentListItem> projectCommentList;

    public GetProjectCommentListResponseDto(List<ProjectCommentListItem> projects) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.projectCommentList = projects;
    }

    public static ResponseEntity<GetProjectCommentListResponseDto> success(List<ProjectCommentListItem> projects) {
        GetProjectCommentListResponseDto result = new GetProjectCommentListResponseDto(projects);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistProject() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_PROJECT, ResponseMessage.NOT_EXISTED_PROJECT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
