package com.smelovd.notification_api.controllers;

import com.smelovd.notification_api.entity.NotificationRequest;
import com.smelovd.notification_api.service.NotificationRequestService;
import com.smelovd.notification_api.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class NotificationRequestController {

    private final NotificationRequestService notificationRequestService;
    private final NotificationService notificationService;

    @PostMapping("/send_notification")
    public ResponseEntity<?> saveNotificationRequest(@RequestParam("message") String message, @RequestParam("file") MultipartFile file) {
        log.info("try to send notification \"{}\", to users in file \"{}\"",
                message, file.getOriginalFilename());
        try {
            //notificationRequestService.validateFile(file);
            NotificationRequest request =notificationRequestService.saveRequest(message, file)
                            .block();
            log.info("saved request " + request);
            notificationService.pushFileEventsToKafkaAndDatabase(file, request.getId()).subscribe();
            return new ResponseEntity<>("notifications add to queue", HttpStatus.OK);
        } catch (IOException e) {
            log.info("send failed \"{}\", to users in file \"{}\"",
                    message, file.getOriginalFilename());
            return new ResponseEntity<>("file or message is invalid", HttpStatus.BAD_REQUEST);
        }
    }
}
