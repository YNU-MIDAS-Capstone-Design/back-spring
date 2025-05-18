package backend.spring.dto.response.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnreadCountResponseDto {
    private long count;
}
