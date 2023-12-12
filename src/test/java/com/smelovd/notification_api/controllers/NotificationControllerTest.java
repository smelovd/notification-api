package com.smelovd.notification_api.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.io.File;

class NotificationControllerTest {

    @Test
    void sendNotification() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("message", "message");
        body.add("file", new FileSystemResource(new File("src/main/resources/test_table1.csv")));

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);

        restTemplate.exchange(
                "http://127.0.0.1:8080/api/v1/send_notification",
                HttpMethod.POST,
                httpEntity,
                String.class
        );
    }
}