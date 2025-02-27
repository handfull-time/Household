package com.utime.household.test.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

//    @GetMapping("View/image")
//    public ResponseEntity<byte[]> getImage(@RequestParam String filename) {
//        try {
//            // 이미지 파일을 static/images 폴더에서 로드
//            Resource imageFile = new ClassPathResource("static/images/" + filename);
//
//            // 파일을 임시 경로에 복사하여 MIME 타입을 판별
//            Path tempFile = Files.createTempFile("image_", "_tmp");
//            Files.copy(imageFile.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
//
//            // 파일의 MIME 타입을 자동 감지
//            String contentType = Files.probeContentType(tempFile);
//
//            // 사용 후 임시 파일 삭제
//            Files.delete(tempFile);
//
//            // MIME 타입이 감지되지 않으면 오류 반환
//            if (contentType == null || !isSupportedImageType(contentType)) {
//                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(null);
//            }
//
//            // 파일을 바이트 배열로 읽음
//            InputStream inputStream = imageFile.getInputStream();
//            byte[] imageBytes = StreamUtils.copyToByteArray(inputStream);
//
//            // HTTP 응답 설정
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.parseMediaType(contentType));
//
//            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }
//
//    // 지원하는 이미지 타입 확인 (PNG, JPG, SVG)
//    private boolean isSupportedImageType(String contentType) {
//        return contentType.equals("image/png") ||
//               contentType.equals("image/jpeg") ||
//               contentType.equals("image/svg+xml");
//    }

/*    
    <img th:src="@{/image(filename='logo.png')}" alt="PNG 이미지">
    <img th:src="@{/image(filename='logo.jpg')}" alt="JPG 이미지">
    <img th:src="@{/image(filename='undraw_profile_1.svg')}" alt="SVG 이미지">
*/    	
    
	@GetMapping("View/image")
	public ResponseEntity<byte[]> getImage(@RequestParam String filename) {
		try {
				
	        // 이미지 파일 로드 (static/images/ 폴더 내 파일 찾기)
	        Resource imageFile = new ClassPathResource("static/images/" + filename);
	        InputStream inputStream = imageFile.getInputStream();
	
	        // 파일 헤더를 읽어 MIME 타입 판별
	        String contentType = detectImageType(inputStream);
	        if (contentType == null) {
	            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(null);
	        }
	
	        // 파일 전체 내용을 바이트 배열로 변환
	        byte[] imageBytes = StreamUtils.copyToByteArray(imageFile.getInputStream());
	
	        // HTTP 응답 설정
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.parseMediaType(contentType));
	
	        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
	    } catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }	
	}

// data:image/svg+xml;base64,

    // 파일 헤더를 읽어 이미지 유형을 판별
    private String detectImageType(InputStream inputStream) throws IOException {
        final byte[] header = new byte[8]; // PNG는 8바이트, JPG는 4바이트, SVG는 최소 4바이트 필요
        inputStream.mark(8);
        final int bytesRead = inputStream.read(header);
        inputStream.reset();

        if (bytesRead < 4) return null; // 최소 4바이트 이상 읽어야 판별 가능

        // JPG 헤더 (FF D8 FF E0)
        if (header[0] == (byte) 0xFF && header[1] == (byte) 0xD8 &&
            header[2] == (byte) 0xFF && header[3] == (byte) 0xE0) {
            return "image/jpeg";
        }

        // PNG 헤더 (89 50 4E 47 0D 0A 1A 0A)
        if (bytesRead >= 8 &&
            header[0] == (byte) 0x89 && header[1] == (byte) 0x50 &&
            header[2] == (byte) 0x4E && header[3] == (byte) 0x47 &&
            header[4] == (byte) 0x0D && header[5] == (byte) 0x0A &&
            header[6] == (byte) 0x1A && header[7] == (byte) 0x0A) {
            return "image/png";
        }

        // SVG 헤더 (< ? x m l) (3C 3F 78 6D 6C)
        if (header[0] == (byte) 0x3C && header[1] == (byte) 0x3F &&
            header[2] == (byte) 0x78 && header[3] == (byte) 0x6D &&
            header[4] == (byte) 0x6C) {
            return "image/svg+xml";
        }

        return null; // 지원하지 않는 파일 형식
    }

    
}

