package my.edu.uitm.baggagefee;

/** Pure calculation logic (no Android dependencies) so it is easy to unit test. */
public final class BaggageCalculator {

    public static final double ECONOMY_ALLOWANCE_KG = 20.0;
    public static final double BUSINESS_ALLOWANCE_KG = 40.0;
    public static final double SERVICE_TAX_RATE = 0.08; // 8% service tax

    private BaggageCalculator() { }

    public static double allowanceKg(boolean business) {
        return business ? BUSINESS_ALLOWANCE_KG : ECONOMY_ALLOWANCE_KG;
    }

    /** Excess weight = weight - X, never negative. */
    public static double excessWeight(double weightKg, boolean business) {
        return Math.max(0.0, weightKg - allowanceKg(business));
    }

    /** Excess charge = excess weight * rate per kg. */
    public static double excessCharge(double excessKg, double ratePerKg) {
        return excessKg * ratePerKg;
    }

    /** Total payable = charge + 8% of charge. */
    public static double totalPayable(double charge) {
        return charge + (SERVICE_TAX_RATE * charge);
    }
}
