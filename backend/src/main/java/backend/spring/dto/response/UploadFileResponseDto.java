package backend.spring.dto.response;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class UploadFileResponseDto extends ResponseDto{
    String image_url;

    private UploadFileResponseDto(String image_url){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.image_url = image_url;
    }

    public static ResponseEntity<UploadFileResponseDto> success(String image_url){
        UploadFileResponseDto result = new UploadFileResponseDto(image_url);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
