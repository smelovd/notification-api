package com.smelovd.notification_api.repository;

import com.smelovd.notification_api.entity.NotificationRequest;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRequestRepository extends ReactiveMongoRepository<NotificationRequest, String> {
}
