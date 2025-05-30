package com.solwyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.solwyz.entity.Notification;
import com.solwyz.repo.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void createNotification(String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public List<Notification> getUnreadNotifications() {
        return notificationRepository.findByIsReadFalse();
    }

    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id).orElse(null);
        if (notification != null) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }
}
