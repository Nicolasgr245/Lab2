package service.impl;

import service.NotificationService;

public class EmailNotification implements NotificationService {

    @Override
    public void sendNotification(String message, String recipient) {

        if (recipient == null ||
                !recipient.contains("@") ||
                !recipient.contains(".")) {

            System.out.println("[Error] Invalid email address");
            return;
        }

        System.out.println("[Email] На адрес " + recipient + " Send Email: " + message);
    }
}
