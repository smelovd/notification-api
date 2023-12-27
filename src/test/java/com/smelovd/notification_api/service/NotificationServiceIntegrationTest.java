package com.smelovd.notification_api.service;

import com.smelovd.notification_api.entity.Notification;
import com.smelovd.notification_api.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.mockito.Mockito.*;

@SpringBootTest
class NotificationServiceIntegrationTest {

    @Autowired
    private NotificationService notificationService;

    @MockBean
    private NotificationRepository notificationRepository;

    @MockBean
    private KafkaTemplate<String, Notification> kafkaTemplate;

   /* @Test
    void pushEventsToKafkaAndDatabase_Success() throws IOException {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            builder.append(i).append(",i@gmail.com,MAIL,").append("\n");
        }

        MultipartFile file = new MockMultipartFile(
                "test.csv",
                "test.csv",
                "text/plain",
                builder.toString().getBytes()
        );

        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("test.csv");
        when(mockFile.getInputStream()).thenReturn(file.getInputStream());

        when(notificationRepository.insert(any(Notification.class))).thenReturn(Mono.empty());
        when(kafkaTemplate.send(anyString(), any(Notification.class))).thenReturn(null);

        notificationService.pushFileEventsToKafkaAndDatabase(mockFile, "1").subscribe();

        verify(notificationRepository, times(10)).insert(any(Notification.class));
        verify(kafkaTemplate, times(10)).send(anyString(), any(Notification.class));
    }*/

    // Additional integration tests for failure scenarios or other cases
}