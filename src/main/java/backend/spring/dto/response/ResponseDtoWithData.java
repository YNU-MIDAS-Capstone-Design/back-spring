package backend.spring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
public class ResponseDtoWithData<T> extends ResponseDto {
    @Schema(description = "응답 데이터")
    private T data;

    public ResponseDtoWithData(String code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    /**
     * 성공 코드·메시지("SU","SUCCESS")와 함께 data를 담아 반환하는 팩토리.
     */
    public static <T> ResponseDtoWithData<T> success(T data) {
        return new ResponseDtoWithData<>("SU", "SUCCESS", data);
    }
}
