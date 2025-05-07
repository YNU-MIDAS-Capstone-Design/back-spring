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
public class ViewHomeResponseDto extends ResponseDto {
	List<ViewProjectDto> recent;
	List<ViewProjectDto> popular;
	List<ViewProjectDto> recommend;

	private ViewHomeResponseDto(List<ViewProjectDto> recent, List<ViewProjectDto> popular, List<ViewProjectDto> recommend) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.recent = recent;
		this.popular = popular;
		this.recommend = recommend;
	}

	public static ResponseEntity<ViewHomeResponseDto> success(List<ViewProjectDto> recent, List<ViewProjectDto> popular, List<ViewProjectDto> recommend){
		ViewHomeResponseDto result = new ViewHomeResponseDto(recent, popular, recommend);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	//not_existed_user

	public static ResponseEntity<ResponseDto> zero_project(){
		ResponseDto result = new ResponseDto(ResponseCode.EMPTY_RESULT, "프로젝트가 없습니다.");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
