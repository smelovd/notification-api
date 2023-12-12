package com.smelovd.notification_api.repository;


import com.smelovd.notification_api.entity.Notification;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface NotificationRepository extends ReactiveCrudRepository<Notification, Long> {

    @Query(value = "insert into notifications (id, service_user_id, notification_service, notification_id) values " +
            "(:#{#notification.id}, :#{#notification.serviceUserId}, :#{#notification.notificationService}, :#{#notification.notificationId})")
    <S extends Notification> Mono<S> saveNotification(@Param("notification") Notification notification);
}
