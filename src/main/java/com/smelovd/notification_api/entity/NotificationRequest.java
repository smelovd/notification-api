package com.smelovd.notification_api.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Data
//@Document
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {

    @Id
    private Long id;

    private MultipartFile file;

    private String message;

    private Timestamp timestamp;
}
