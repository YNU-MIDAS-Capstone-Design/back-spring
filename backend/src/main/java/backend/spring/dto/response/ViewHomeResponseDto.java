package backend.spring.dto.response;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.object.ViewProjectData;
import lombok.Getter;

@Getter
public class ViewHomeResponseDto extends ResponseDto{
	private List<ViewProjectData> recent; //추천순
	private List<ViewProjectData> popular; //인기순

	private ViewHomeResponseDto(List<ViewProjectData> recent, List<ViewProjectData> popular){
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.recent = recent;
		this.popular = popular;
	}

	public static ResponseEntity<ViewHomeResponseDto> success(List<ViewProjectData> recent, List<ViewProjectData> popular){
		ViewHomeResponseDto result = new ViewHomeResponseDto(recent, popular);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	public static ResponseEntity<ResponseDto> not_existed_project(){
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_PROJECT, ResponseMessage.NOT_EXISTED_PROJECT);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

}
