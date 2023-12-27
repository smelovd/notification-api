package com.smelovd.notification_api.repository;


import com.smelovd.notification_api.entity.Notification;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface NotificationRepository extends ReactiveMongoRepository<Notification, String> {
    Mono<Long> countNotificationsByNotificationId(String notificationId);
}
