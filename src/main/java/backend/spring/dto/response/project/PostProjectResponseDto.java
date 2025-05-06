package backend.spring.dto.response.project;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PostProjectResponseDto extends ResponseDto {

    private Long projectId;

    private PostProjectResponseDto(Long projectId) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.projectId = projectId;
    }

    public static ResponseEntity<PostProjectResponseDto> success(Long projectId) {
        PostProjectResponseDto result = new PostProjectResponseDto(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}