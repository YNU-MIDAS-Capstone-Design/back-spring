package backend.spring.dto.request;

import backend.spring.entity.enums.Location;
import backend.spring.entity.enums.Stack;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "내 프로필 수정 요청 DTO")
public class UpdateProfileRequest {

    @Schema(description = "자기소개", example = "안녕하세요, 백엔드 개발자입니다.")
    private String bio;

    @Schema(description = "거주 지역", example = "경상북도")
    private Location location;

    @Schema(description = "SNS 링크", example = "https://linkedin.com/in/example")
    private String sns;


    @Schema(description = "MBTI", example = "ENTP")
    private String mbti;

    @Schema(description = "직업", example = "프론트엔드 개발자")
    private String job;

    @Builder.Default
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @Schema(description = "기술 스택 목록", example = "[\"JAVA\", \"REACT\"]")
    private List<Stack> techStacks = List.of();
}
