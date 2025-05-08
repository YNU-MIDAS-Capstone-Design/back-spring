package backend.spring.dto.object;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 단순 참고용
@Getter
@AllArgsConstructor
public class RecommendScoreDto {
    private Long projectId;
    private double contentScore;
    private double collabScore;
    private double finalScore;
}
