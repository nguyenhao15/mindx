package com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processTag;

public record ProcessTagUpdateRecord (
        String id,
        String tagName,
        String fullTagName
){}
