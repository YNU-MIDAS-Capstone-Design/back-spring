package backend.spring.dto.response.project;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class ApplicantListResponseDto extends ResponseDto {

    private List<String> applicants;

    private ApplicantListResponseDto(List<String> applicants) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.applicants = applicants;
    }

    public static ResponseEntity<ApplicantListResponseDto> success(List<String> applicants) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApplicantListResponseDto(applicants));
    }
}
