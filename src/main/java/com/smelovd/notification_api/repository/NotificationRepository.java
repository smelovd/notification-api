package com.smelovd.notification_api.repository;


import com.smelovd.notification_api.entity.Notification;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends ReactiveMongoRepository<Notification, Long> {

    /*@Query(value = "insert into notifications (id, service_user_id, notification_service, notification_id) values " +
            "(:#{#notification.id}, :#{#notification.serviceUserId}, :#{#notification.notificationService}, :#{#notification.notificationId})")
    <S extends Notification> Mono<S> saveNotification(@Param("notification") Notification notification);
*/
}
