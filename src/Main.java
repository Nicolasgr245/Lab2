
import java.util.Scanner;

import billing.Billing;
import model.Subscription;
import model.UserSubscription;
import model.CorporateSubscription;
import service.NotificationService;
import service.impl.EmailNotification;
import service.impl.SmsNotification;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("SUBSCRIPTION SYSTEM");

        boolean running = true;

        while (running) {

            String name;

            while (true) {
                System.out.print("Enter customer name: ");
                name = scanner.nextLine().trim();

                if (!name.isEmpty()) {
                    break;
                }

                System.out.println("[Error] Name cannot be empty");
            }

            boolean isCompany = askYesNo(scanner, "Is this a company? (yes/no): ");

            System.out.println("\nAvailable plans:");
            System.out.println("1. Subscription1 - 500 RUB/month");
            System.out.println("2. Subscription2 - 750 RUB/month");
            System.out.println("3. Subscription3 - 1000 RUB/month");

            int planChoice = readInt(scanner, "Select plan (1-3): ", 1, 3);

            String planName = "Subscription" + planChoice;
            double basePrice = 500.0;

            Subscription subscription;

            if (isCompany) {

                int licenses = readInt(scanner, "Enter number of licenses: ", 1, Integer.MAX_VALUE);

                subscription = new CorporateSubscription(name, basePrice, planName, licenses);

            } else {

                subscription = new UserSubscription(name, basePrice, planName);
            }

            double normalPrice = subscription.calculateBillingPrice();

            double finalPrice = normalPrice;

            if (!isCompany) {

                boolean usePromo = askYesNo(scanner, "Do you have a promo code? (yes/no): ");

                if (usePromo) {
                    System.out.print("Enter promo code: ");
                    String promoCode = scanner.nextLine().trim();

                    finalPrice = subscription.calculateBillingPrice(promoCode);
                }
            } else {
                System.out.println("[Info] Corporate promo codes are not available");
            }

            System.out.println("\nPAYMENT SUMMARY");
            System.out.println("Customer: " + name);
            System.out.println("Type: " + (isCompany ? "Corporate" : "Individual"));
            System.out.println("Plan: " + planName);
            System.out.printf("Normal price: %.2f RUB%n", normalPrice);
            System.out.printf("Final price: %.2f RUB%n", finalPrice);

            boolean confirmed = askYesNo(scanner, "Confirm payment? (yes/no): ");

            if (confirmed) {

                System.out.println("\nNotification method:");
                System.out.println("1. Email");
                System.out.println("2. SMS");

                int notificationChoice = readInt(scanner, "Select notification (1-2): ", 1, 2);

                NotificationService notificationService;
                String contact;

                if (notificationChoice == 1) {

                    notificationService = new EmailNotification();

                    while (true) {
                        System.out.print("Enter email address: ");
                        contact = scanner.nextLine().trim();

                        if (contact.matches("[^\\s@]+@[^\\s@.]+(\\.[^\\s@.]+)+")) {break;}

                        System.out.println("[Error] Invalid email address");
                    }

                } else {

                    notificationService = new SmsNotification();

                    while (true) {
                        System.out.print("Enter phone number: ");
                        contact = scanner.nextLine().trim();

                        if (contact.matches("\\+7\\d{10}") || contact.matches("8\\d{10}")) {break;}

                        System.out.println("[Error] Invalid phone number");
                    }
                }

                Billing billing = new Billing(notificationService);

                billing.processPayment(subscription, contact, finalPrice);

            } else {
                System.out.println("[INFO] Payment cancelled");
            }

            running = askYesNo(scanner, "\nRegister another customer? (yes/no): ");
        }

        Billing.printStatistics();

        System.out.println("\nThank you for your purchase");

        scanner.close();
    }

    private static int readInt(Scanner scanner, String message, int min, int max) {

        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);

                if (value >= min && value <= max) {
                    return value;
                }

            } catch (NumberFormatException e) {}

            System.out.println("[Error] Enter a valid number");
        }
    }

    private static double readDouble(Scanner scanner, String message) {

        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim().replace(',', '.');

            try {
                double value = Double.parseDouble(input);

                if (Double.isFinite(value) && value > 0) {
                    return value;
                }

            } catch (NumberFormatException e) {
                // Invalid input, ask again
            }

            System.out.println("[Error] Enter a positive valid number");
        }
    }

    private static boolean askYesNo(Scanner scanner, String message) {

        while (true) {
            System.out.print(message);
            String answer = scanner.nextLine().trim().toLowerCase();

            if (answer.equals("yes") || answer.equals("y")) {return true;}

            if (answer.equals("no") || answer.equals("n")) {return false;}

            System.out.println("[Error] Please enter yes or no");
        }
    }
}
