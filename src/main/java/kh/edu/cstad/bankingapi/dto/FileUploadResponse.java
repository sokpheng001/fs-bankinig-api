package kh.edu.cstad.bankingapi.dto;

import lombok.Builder;

@Builder
public record FileUploadResponse(
        String fileName,
        Long fileSize,
        String fileType,
        String previewLink,
        String downloadLink
) {
}
