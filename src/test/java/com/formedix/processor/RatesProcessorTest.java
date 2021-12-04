package com.formedix.processor;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class RatesProcessorTest {
    private final long MILLISECONDS_PER_DAY = 1000 * 60 * 60 * 24;
    private final Date FIRST_DATE = new Date(0);
    private final Date SECOND_DATE = new Date(MILLISECONDS_PER_DAY);
    private final Date THIRD_DATE = new Date(MILLISECONDS_PER_DAY * 2);
    private final String JPY = "JPY";
    private final Double FIRST_DOUBLE = 5.0;
    private final Double SECOND_DOUBLE = 15.0;
    private final Double THIRD_DOUBLE = Double.NaN;
    private final Map<String, Double> FIRST_ROW = new HashMap<>();
    private final Map<String, Double> SECOND_ROW =  new HashMap<>();
    private final Map<String, Double> THIRD_ROW = new HashMap<>();

    private final NavigableMap<Date, Map<String, Double>> TEST_DATA = new TreeMap<>();

    @Before
    public void setup() {
        FIRST_ROW.clear();
        FIRST_ROW.put(JPY, FIRST_DOUBLE);
        SECOND_ROW.clear();
        SECOND_ROW.put(JPY, SECOND_DOUBLE);
        THIRD_ROW.clear();
        THIRD_ROW.put(JPY, THIRD_DOUBLE);
        TEST_DATA.clear();
        TEST_DATA.put(FIRST_DATE, FIRST_ROW);
        TEST_DATA.put(SECOND_DATE, SECOND_ROW);
        TEST_DATA.put(THIRD_DATE, THIRD_ROW);
    }

    @Test
    public void when_askedToFindMaximum_then_returnsMaximum() {
        Processor processor = new RatesProcessor(TEST_DATA);
        Double actual = processor.getHighestRate(FIRST_DATE, SECOND_DATE, JPY);
        assert actual == 15.0;
    }

    @Test
    public void when_askedToFindMaximum_given_DoubleNaNIsInRange_then_returnsMaximum() {
        Processor processor = new RatesProcessor(TEST_DATA);
        Double actual = processor.getHighestRate(FIRST_DATE, THIRD_DATE, JPY);
        assert actual == 15.0;
    }

    @Test
    public void when_askedToFindMaximum_given_noNumbersInRange_then_returnsDoubleNaN() {
        Processor processor = new RatesProcessor(TEST_DATA);
        Double actual = processor.getHighestRate(THIRD_DATE, THIRD_DATE, JPY);
        assert actual.isNaN();
    }

    @Test
    public void when_askedToFindAverage_then_returnsAverage() {
        Processor processor = new RatesProcessor(TEST_DATA);
        Double actual = processor.getAverageRate(FIRST_DATE, SECOND_DATE, JPY);
        assert actual == 10.0;
    }

    @Test
    public void when_askedToFindAverage_given_DoubleNaNIsInRange_then_returnsAverage() {
        Processor processor = new RatesProcessor(TEST_DATA);
        Double actual = processor.getAverageRate(FIRST_DATE, THIRD_DATE, JPY);
        assert actual == 10.0;
    }

    @Test
    public void when_askedToFindAverage_given_noNumbersInRange_then_returnsDoubleNaN() {
        Processor processor = new RatesProcessor(TEST_DATA);
        Double actual = processor.getAverageRate(THIRD_DATE, THIRD_DATE, JPY);
        assert actual.isNaN();
    }

    @Test
    public void when_askedForRate_then_returnsRate() {
        Processor processor = new RatesProcessor(TEST_DATA);
        Double actual = processor.getRate(FIRST_DATE, JPY);
        assert actual.equals(FIRST_DOUBLE);
    }
}
