package backend.spring.dto.response.project;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.object.ProjectDetailDto;
import backend.spring.dto.response.ResponseDto;
import backend.spring.entity.Project;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ViewProjectResponseDto extends ResponseDto {

    private ProjectDetailDto project;

    private ViewProjectResponseDto(Project projectEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.project = new ProjectDetailDto(projectEntity);
    }

    public static ResponseEntity<ViewProjectResponseDto> success(Project project) {
        ViewProjectResponseDto response = new ViewProjectResponseDto(project);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
