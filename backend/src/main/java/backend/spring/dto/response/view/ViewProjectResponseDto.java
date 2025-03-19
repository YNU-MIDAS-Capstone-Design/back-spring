package backend.spring.dto.response.view;

import java.util.List;

import backend.spring.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.object.ViewProjectData;

public class ViewProjectResponseDto extends ResponseDto {
	List<ViewProjectData> projects;

	private ViewProjectResponseDto(List<ViewProjectData> projects) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.projects = projects;
	}

	public static ResponseEntity<ViewProjectResponseDto> success(List<ViewProjectData> projects){
		ViewProjectResponseDto result = new ViewProjectResponseDto(projects);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	public static ResponseEntity<ResponseDto> not_existed_project(){
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_PROJECT, ResponseMessage.NOT_EXISTED_PROJECT);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}
}
