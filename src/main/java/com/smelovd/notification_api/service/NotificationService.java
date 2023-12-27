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
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Timestamp;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final KafkaTemplate<String, Notification> kafkaTemplate;

    //@Async
    public Mono<Void> pushFileEventsToKafkaAndDatabase(MultipartFile file, String id) throws IOException {
        log.info("sending notification \"{}\"", file.getOriginalFilename());
        return Flux.fromIterable(getRecords(file))
                .flatMap(r -> mapNotification(r, id))
                //.doOnNext(n -> kafkaTemplate.send("notifications", n))
                .doOnNext(n -> notificationRepository.insert(n).subscribe())
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }

    private Iterable<CSVRecord> getRecords(MultipartFile file) throws IOException {
        log.info("file parsing");
        Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            return CSVFormat.RFC4180.builder()
                    .setHeader("id", "service_user_id", "notification_service").build().parse(reader);

    }

    private Mono<Notification> mapNotification(CSVRecord record, String notificationId) {
        log.info("mapping notification, " + record);
        String fileId = record.get("id");
        String serviceUserId = record.get("service_user_id");
        String service = record.get("notification_service");
        return Mono.just(Notification.builder()
                .fileId(fileId)
                .serviceUserId(serviceUserId)
                .notificationService(service)
                .notificationId(notificationId)
                .timestamp(new Timestamp(System.currentTimeMillis())).build());
    }
}
