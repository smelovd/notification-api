package com.smelovd.notification_api.service;

import com.smelovd.notification_api.entity.NotificationRequest;
import com.smelovd.notification_api.entity.NotificationRequestStatus;
import com.smelovd.notification_api.repository.NotificationRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationRequestService {

    private final NotificationRequestRepository notificationRequestRepository;

    public void validateFile(MultipartFile file) throws IOException {
        log.info("reading file \"{}\"", file.getOriginalFilename());
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            if (file.isEmpty()) throw new IOException();
            Iterable<CSVRecord> records = CSVFormat.RFC4180.builder()
                    .setHeader("id", "service_user_id", "notification_service")
                    .build().parse(reader);
            System.out.println(records.iterator().next().getRecordNumber());
            log.info("file is valid \"{}\"", file.getOriginalFilename());
        } catch (IOException ex) {
            log.info("file invalid \"{}\"", file.getOriginalFilename());
            throw new IOException("file invalid");
        }
    }

    public Mono<String> saveRequest(String message, MultipartFile file) {
        log.info("saving request \"{}\", \"{}\"", message, file.getOriginalFilename());
        try {
            return Mono.just(buildNotificationRequest(message, file))
                    .flatMap(notificationRequestRepository::insert)
                    .flatMap(n -> Mono.just(n.getId()));
        } catch (Exception e) {
            log.warn("failed to save request \"{}\", \"{}\"", message, file.getOriginalFilename());
            throw new RuntimeException("failed to save request");
        }
    }

    private NotificationRequest buildNotificationRequest(String message, MultipartFile file) throws IOException {
        return NotificationRequest.builder()
                .file(file.getBytes())
                .message(message)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .notificationRequestStatus(NotificationRequestStatus.CREATED).build();
    }

}
