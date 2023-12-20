package com.smelovd.notification_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    private Long id;

    private String serviceUserId;

    private String notificationService;

    private Long notificationId;

    private Timestamp timestamp;
/*
    @Column(name = "count_try")
    private Long countTry;*/
}
