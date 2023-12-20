package com.smelovd.notification_api.service;

import com.smelovd.notification_api.entity.Notification;
import com.smelovd.notification_api.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Timestamp;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final KafkaTemplate<String, Notification> kafkaTemplate;

    @Async
    public void send(MultipartFile file) {
        log.info("try send notification");
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Iterable<CSVRecord> records = CSVFormat.RFC4180.builder().setHeader("id", "service_user_id", "notification_service", "notification_id").build().parse(reader);
            log.info("file is valid \"{}\"", file.getOriginalFilename());
            Flux.fromIterable(records)
                    .flatMap(this::mapNotification)
                    .doOnNext(n -> kafkaTemplate.send("notifications", n))
                    .subscribe(notificationRepository::save);
            System.out.println("complete");
        } catch (Exception ex) {
            log.error("file has problem");
            throw new RuntimeException(ex);
        }
    }

    private Mono<Notification> mapNotification(CSVRecord record) {
        Long id = Long.valueOf(record.get("id"));
        String serviceUserId = record.get("service_user_id");
        String service = record.get("notification_service");
        Long notificationId = Long.valueOf(record.get("notification_id"));

        System.out.println(id + " " + serviceUserId + " " + service);
        return Mono.just((new Notification(id, serviceUserId, service, notificationId, new Timestamp(System.currentTimeMillis()))));
    }
}
