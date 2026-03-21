package com.example.demo01.core.Attachment.mapper;

import com.example.demo01.core.Attachment.dto.AttachmentDto;
import com.example.demo01.core.Attachment.model.AttachmentItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    AttachmentItem mapToAttachmentItem(AttachmentDto attachmentDto);

    AttachmentDto mapToAttachmentDto(AttachmentItem attachmentItem);

    List<AttachmentDto> mapToAttachmentItemDto(List<AttachmentItem> processFlowAttachments);

}
