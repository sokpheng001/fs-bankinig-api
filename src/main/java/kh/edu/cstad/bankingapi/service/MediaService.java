package kh.edu.cstad.bankingapi.service;

import kh.edu.cstad.bankingapi.dto.FileUploadResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface MediaService {
    FileUploadResponse singleUpload(MultipartFile file);
    List<FileUploadResponse> multipleUploads(List<MultipartFile> files);
}
