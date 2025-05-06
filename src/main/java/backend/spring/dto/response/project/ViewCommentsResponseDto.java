package backend.spring.dto.response.project;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.object.ProjectCommentDto;
import backend.spring.dto.response.ResponseDto;
import backend.spring.entity.ProjectComment;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ViewCommentsResponseDto extends ResponseDto {

    private List<ProjectCommentDto> comments;

    private ViewCommentsResponseDto(List<ProjectComment> commentList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.comments = commentList.stream()
                .map(ProjectCommentDto::new)
                .collect(Collectors.toList());
    }

    public static ResponseEntity<ViewCommentsResponseDto> success(List<ProjectComment> commentList) {
        return ResponseEntity.status(HttpStatus.OK).body(new ViewCommentsResponseDto(commentList));
    }
}
