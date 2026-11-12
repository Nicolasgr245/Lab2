package service.impl;

import service.NotificationService;

public class SmsNotification implements NotificationService {

    @Override
    public void sendNotification(String message, String recipient) {

        if (recipient == null ||
                !(recipient.matches("\\+7\\d{10}") ||
                        recipient.matches("8\\d{10}"))) {

            System.out.println("[Error] Invalid phone number.");
            return;
        }

        System.out.println("[Sms] На номер " + recipient + " отправлено сообщение: " + message);
    }
}