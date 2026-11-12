
package model;

public class PromoCode {

    public static double getDiscount(String code) {

        if (code == null || code.isBlank()) {
            return 0.0;
        }

        return switch (code.trim().toUpperCase()) {
            case "HOLA10" -> 0.10;
            case "HOLA20" -> 0.20;

            default -> {
                System.out.println("[Warning] Invalid promo code: " + code);
                yield 0.0;
            }
        };
    }
}