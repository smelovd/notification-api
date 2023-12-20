package com.smelovd.notification_api.service;

import com.smelovd.notification_api.entity.Notification;
import com.smelovd.notification_api.entity.NotificationRequest;
import com.smelovd.notification_api.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationRequestService {

    private final NotificationRepository notificationRepository;
    private final KafkaTemplate<String, Notification> kafkaTemplate;

    public void validateFile(MultipartFile file) throws IOException {
        log.info("try read file \"{}\"", file.getOriginalFilename());
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            if (file.isEmpty()) throw new IOException();
            Iterable<CSVRecord> records = CSVFormat.RFC4180.builder()
                    .setHeader("id", "service_user_id", "notification_service", "notification_id")
                    .build().parse(reader);
            log.info("file is valid \"{}\"", file.getOriginalFilename());
        } catch (IOException ex) {
            throw new IOException("file is invalid");
        }
    }

    public void saveRequest(String message, MultipartFile file) {
        log.info("try to save request \"{}\", \"{}\"", message, file.getOriginalFilename());
        try {
            NotificationRequest notificationRequest = NotificationRequest.builder()
                    .file(file).message(message).build();
            System.out.println(notificationRequest);
            //notificationRequestRepository.saveNotificationRequest().subscribe();
            log.info("saved request \"{}\", \"{}\"", message, file.getOriginalFilename());
        } catch (RuntimeException ex) {
            log.warn("failed to save request \"{}\", \"{}\"", message, file.getOriginalFilename());
        }
    }
}
