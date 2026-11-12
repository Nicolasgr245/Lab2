package model;

public abstract class Subscription {
    private final String ownerName;
    private final double baseMonthlyPrice;
    private final String planName;

    public Subscription(String ownerName, double baseMonthlyPrice,
                        String planName) {

        this.ownerName = ownerName;
        this.planName = planName;

        if (baseMonthlyPrice <= 0) {
            System.out.println(
                    "[Warning] Invalid base price. Using 100.0"
            );
            this.baseMonthlyPrice = 100.0;
        } else {
            this.baseMonthlyPrice = baseMonthlyPrice;
        }
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBaseMonthlyPrice() {
        return baseMonthlyPrice;
    }

    public String getPlanName() {
        return planName;
    }

    protected double getTariffCoefficient() {
        return switch (planName.toUpperCase()) {
            case "SUBSCRIPTION1" -> 1.0;
            case "SUBSCRIPTION2" -> 1.5;
            case "SUBSCRIPTION3" -> 2.0;
            default -> {System.out.println("[Warning] Unknown plan: " + planName);
                yield 1.0;
            }
        };
    }

    public abstract double calculateBillingPrice();

    public double calculateBillingPrice(String promoCode) {
        return calculateBillingPrice();
    }
}
