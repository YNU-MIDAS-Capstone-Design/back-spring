package backend.spring.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExampleRequestDto {
    private String orderId;
    private Long amount;
    private String paymentKey;
}
