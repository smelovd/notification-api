package com.smelovd.notification_api.service;

import com.smelovd.notification_api.entity.Notification;
import com.smelovd.notification_api.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final KafkaTemplate<String, Notification> kafkaTemplate;

    public void send(MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Iterable<CSVRecord> notifications = CSVFormat.RFC4180.builder()
                    .setHeader("id", "service_user_id", "notification_service", "notification_id").build().parse(reader);

            Flux.fromIterable(notifications).map(this::mapNotification).subscribe(notification -> {
                notificationRepository.saveNotification(notification).subscribe();
                kafkaTemplate.send("notifications", notification);
            });
        } catch (Exception e) {
            log.error("file has problem");
            throw new RuntimeException(e);
        }
    }

    private Notification mapNotification(CSVRecord record) {
        Long id = Long.valueOf(record.get("id"));
        String serviceUserId = record.get("service_user_id");
        String service = record.get("notification_service");
        Long notificationId = Long.valueOf(record.get("notification_id"));

        System.out.println(id + " " + serviceUserId + " " + service);
        return new Notification(id, serviceUserId, service, notificationId);
    }
}
