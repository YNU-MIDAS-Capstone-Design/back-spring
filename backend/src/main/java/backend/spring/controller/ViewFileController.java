package backend.spring.controller;

import backend.spring.dto.response.ResponseDto;
import backend.spring.dto.response.UploadFileResponseDto;
import backend.spring.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/view")
@RequiredArgsConstructor
public class ViewFileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<? super UploadFileResponseDto> uploadImage(@RequestParam("file") MultipartFile file){
        String fileName = fileService.file_upload("profile_image", file);
        if( fileName == null){
            return UploadFileResponseDto.databaseError(); //파일 저장 실패
        } else{
            String image_url = "http://localhost:8080/api/view/profile_image/" + fileName;
            return UploadFileResponseDto.success(image_url);
        }
    }
    @DeleteMapping("/delete/{file_name}")
    public ResponseEntity<ResponseDto> deleteImage(@PathVariable("file_name") String fileName){
        if(fileService.file_delete("profile_image", fileName)){
            return ResponseDto.successResponse();
        } else{
            return ResponseDto.databaseError();
        }
    }

    @GetMapping("/profile_image/{file_name}")
    public ResponseEntity<Resource> viewProfile(@PathVariable("file_name") String fileName) {
        try {
            String fileDir = "/app/data/profile_image/";
            Path path = Paths.get(fileDir, fileName);
            Resource resource = new UrlResource(path.toUri());

            // 파일의 MIME 타입을 동적으로 결정
            String mimeType = Files.probeContentType(path);

            // MIME 타입이 null일 경우 기본값을 설정할 수 있습니다.
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))  //동적으로 타입 설정 가능
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
