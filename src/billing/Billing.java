
package billing;

import model.Subscription;
import service.NotificationService;

public class Billing {

    private static int totalSubscribersCount = 0;
    private static double totalRevenue = 0.0;

    private final NotificationService notificationService;

    public Billing(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void processPayment(Subscription sub, String contact, double finalPrice) {

        if (sub == null || finalPrice <= 0 ||
                !Double.isFinite(finalPrice)) {
            System.out.println("[Error] Invalid payment.");
            return;
        }

        totalSubscribersCount++;
        totalRevenue += finalPrice;

        System.out.println();
        System.out.println("PAYMENT PROCESSED");
        System.out.println("Owner: " + sub.getOwnerName());
        System.out.println("Plan: " + sub.getPlanName());
        System.out.println("Amount: " + finalPrice + " RUB");

        String message = "Payment successful. Amount: " + finalPrice + " RUB";

        notificationService.sendNotification(message, contact);
    }

    public static void printStatistics() {

        System.out.println();
        System.out.println("GLOBAL STATISTICS");
        System.out.println("Total subscriptions: " + totalSubscribersCount);
        System.out.println("Total revenue: " + totalRevenue + " RUB");
    }
}
