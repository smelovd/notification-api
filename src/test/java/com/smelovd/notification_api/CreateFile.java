package com.smelovd.notification_api;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateFile {
    public static void main(String[] args) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/test_table1.csv"));
            for (int i = 1; i < 1000; i++) {
                //users
                //id, phone, mail,
                writer.write(i + ",user" + i + "@gmail.com,email,1\n");
            }
            writer.close();
            System.out.println("success");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }/*
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("message", "message");
        body.add("file", new FileSystemResource(new File("src/main/resources/test_table1.csv")));

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);

        restTemplate.exchange(
                "http://localhost:8080/api/v1/send_notification",
                HttpMethod.POST,
                httpEntity,
                String.class
        );*/
    }
}
