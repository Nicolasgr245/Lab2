package model;

public class UserSubscription extends Subscription {

    public UserSubscription(String ownerName, double baseMonthlyPrice, String planName) {
        super(ownerName, baseMonthlyPrice, planName);
    }

    @Override
    public double calculateBillingPrice() {
        return getBaseMonthlyPrice() * getTariffCoefficient();
    }

    @Override
    public double calculateBillingPrice(String promoCode) {
        double normalPrice = calculateBillingPrice();
        double discount = PromoCode.getDiscount(promoCode);

        return normalPrice * (1.0 - discount);
    }
}