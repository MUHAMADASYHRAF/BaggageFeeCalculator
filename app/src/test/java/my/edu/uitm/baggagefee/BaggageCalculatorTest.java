package my.edu.uitm.baggagefee;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BaggageCalculatorTest {
    private static final double D = 0.0001;

    private double total(double w, boolean biz, double rate) {
        double ex = BaggageCalculator.excessWeight(w, biz);
        return BaggageCalculator.totalPayable(BaggageCalculator.excessCharge(ex, rate));
    }

    @Test public void economy25() { assertEquals(81.00, total(25, false, 15), D); }
    @Test public void economyUnderLimit() { assertEquals(0.0, total(18, false, 15), D); }
    @Test public void business55() { assertEquals(194.40, total(55, true, 12), D); }
    @Test public void business42_5() { assertEquals(32.40, total(42.5, true, 12), D); }
    @Test public void excessNeverNegative() { assertEquals(0.0, BaggageCalculator.excessWeight(5, true), D); }
}
