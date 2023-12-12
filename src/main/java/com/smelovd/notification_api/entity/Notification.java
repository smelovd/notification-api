package com.smelovd.notification_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("notifications")
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    private Long id;

    @Column(value = "service_user_id")
    private String serviceUserId;

    @Column(value = "notification_service")
    private String notificationService;

    @Column(value = "notification_id")
    private Long notificationId;

    /*@Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "count_try")
    private Long countTry;*/
}
