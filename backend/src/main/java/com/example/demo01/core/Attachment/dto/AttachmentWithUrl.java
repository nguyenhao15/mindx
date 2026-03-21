package com.example.demo01.core.Attachment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttachmentWithUrl {

    private String fileUrl;
    private Boolean isDeleted;
}
