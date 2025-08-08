package kh.edu.cstad.bankingapi.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import kh.edu.cstad.bankingapi.base.BaseResponse;
import kh.edu.cstad.bankingapi.service.MediaService;
import kh.edu.cstad.bankingapi.service.impl.MediaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;
    @PostMapping("/single-upload")
    public BaseResponse<Object> singleUpload(@RequestParam("file") MultipartFile multipartFile){
        return BaseResponse.builder()
                .status(String.valueOf(HttpStatus.OK.value()))
                .timeStamp(Date.from(Instant.now()))
                .message("Uploaded file successfully")
                .data(mediaService.singleUpload(multipartFile))
                .build();
    }
    @GetMapping("/media-download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("uploads").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    // This header forces download
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
