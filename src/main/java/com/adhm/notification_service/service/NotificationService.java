package com.adhm.notification_service.service;

import com.adhm.notification_service.model.Notification;
import com.adhm.notification_service.model.Status;
import com.adhm.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public Notification sendNotification(Notification notification) {
        notificationRepository.save(notification);
        System.out.println("[" + notification.getChannel() + "] Sending to user "
                + notification.getUserId() + ": " + notification.getMessage());
        notification.setStatus(Status.SENT);
        return notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsForUser(String userId) {
        return notificationRepository.findByUserId(userId);
    }
}