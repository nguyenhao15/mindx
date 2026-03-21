package com.example.demo01.core.Aws3.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileResponseDTO {
    private String fileName;
    private String fileUrl;
    private String contentType;
    private long size;
}
