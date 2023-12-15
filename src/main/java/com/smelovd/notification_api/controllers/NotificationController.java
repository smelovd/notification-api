package com.smelovd.notification_api.controllers;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send_notification")
    public ResponseEntity<?> sendNotification(@RequestParam("file") MultipartFile file, @RequestParam("message") String message) {
        log.info("try to send notification \"{}\"", message);
        if (file.isEmpty() || message == null) {
            log.warn("message \"{}\" or file is empty ", message);
            return new ResponseEntity<>("file or message is empty",HttpStatus.BAD_REQUEST);
        }
        log.info("notification data is valid");
        try {
            notificationService.saveRequest(file, message);
            notificationService.send(file);
            log.info("{} {} sent", file.getOriginalFilename(), message);
        } catch (Exception ex) {
            log.info("message {} sent failed", message);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
