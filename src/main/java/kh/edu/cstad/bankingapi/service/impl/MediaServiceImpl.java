package kh.edu.cstad.bankingapi.service.impl;

import kh.edu.cstad.bankingapi.dto.FileUploadResponse;
import kh.edu.cstad.bankingapi.service.MediaService;
import org.hibernate.sql.exec.spi.StandardEntityInstanceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.UUID;

@Service
public class MediaServiceImpl implements MediaService {
    @Value("${file.target-path}")
    private String targetPath;
    @Value("${file.preview-url}")
    private String previewLink;
    @Value("${file-download-link}")
    private String downloadLink;
    private static final Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);
    @Override
    public FileUploadResponse singleUpload(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String [] splits = originalFileName.split("\\.");
        String newFileName = UUID.randomUUID() + "." + splits[1];// splits[1] = file extension
        try{
            Path folder = Paths.get(targetPath).toAbsolutePath().normalize();
            Files.createDirectories(folder); // ensure folder exists
            Path filePath = folder.resolve(newFileName);
            Files.copy(file.getInputStream(), filePath);
            return FileUploadResponse.builder()
                    .fileName(newFileName)
                    .fileSize(file.getSize())
                    .fileType(file.getContentType())
                    .previewLink(previewLink + newFileName)
                    .downloadLink(downloadLink+newFileName)
                    .build();
        }catch (Exception exception){
            log.error("Error file single upload: {}", exception.getMessage());
        }
        return null;
    }

    @Override
    public List<FileUploadResponse> multipleUploads(List<MultipartFile> files) {
        return List.of();
    }
}
