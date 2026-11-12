
package model;

public class CorporateSubscription extends Subscription {

    private final int activeLicensesCount;

    public CorporateSubscription(String ownerName,
                                 double baseMonthlyPrice,
                                 String planName,
                                 int activeLicensesCount) {

        super(ownerName, baseMonthlyPrice, planName);

        if (activeLicensesCount < 1) {
            System.out.println(
                    "[Warning] Invalid license count. Using 1"
            );
            this.activeLicensesCount = 1;
        } else {
            this.activeLicensesCount = activeLicensesCount;
        }
    }

    public int getActiveLicensesCount() {
        return activeLicensesCount;
    }

    @Override
    public double calculateBillingPrice() {
        return getBaseMonthlyPrice()
                * getTariffCoefficient()
                * activeLicensesCount;
    }

    @Override
    public double calculateBillingPrice(String promoCode) {

        System.out.println(
                "[Error] Promo codes are not available for corporate subscriptions"
        );

        return calculateBillingPrice();
    }
}
