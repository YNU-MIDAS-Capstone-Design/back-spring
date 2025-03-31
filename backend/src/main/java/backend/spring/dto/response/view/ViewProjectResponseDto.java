package backend.spring.dto.response.view;

import java.util.List;

import backend.spring.dto.object.ViewProjectDto;
import backend.spring.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import lombok.Getter;

@Getter
public class ViewProjectResponseDto extends ResponseDto {
	List<ViewProjectDto> projects;
	Integer totalCount; //전체 프로젝트 수
	Integer page;  //현재 페이지

	private ViewProjectResponseDto(List<ViewProjectDto> projects, Integer totalCount, Integer page) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.projects = projects;
		this.totalCount = totalCount;
		this.page = page;
	}

	public static ResponseEntity<ViewProjectResponseDto> success(List<ViewProjectDto> projects, Integer totalCount, Integer page){
		ViewProjectResponseDto result = new ViewProjectResponseDto(projects, totalCount, page);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	public static ResponseEntity<ResponseDto> not_existed_project(){
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_PROJECT, ResponseMessage.NOT_EXISTED_PROJECT);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}
}