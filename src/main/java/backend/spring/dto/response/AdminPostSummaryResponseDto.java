package backend.spring.dto.response;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.object.PostInfoDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class AdminPostSummaryResponseDto extends ResponseDto {

    private final List<PostInfoDto> posts;

    private AdminPostSummaryResponseDto(List<PostInfoDto> posts) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.posts = posts;
    }

    public static ResponseEntity<AdminPostSummaryResponseDto> success(List<PostInfoDto> posts) {
        AdminPostSummaryResponseDto body = new AdminPostSummaryResponseDto(posts);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}

