package kh.edu.cstad.bankingapi.base;

import lombok.Builder;

import java.util.Date;

@Builder
public record BaseResponse<T>(
        String status,
        Date timeStamp,
        String message,
        T data
) { }
