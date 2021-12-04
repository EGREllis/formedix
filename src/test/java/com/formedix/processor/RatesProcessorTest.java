package com.formedix.processor;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class RatesProcessorTest {
    private final Date FIRST_DATE = new Date(0);
    private final Date SECOND_DATE = new Date(1000*60*60*24);
    private final String JPY = "JPY";
    private final Double FIRST_DOUBLE = 5.0;
    private final Double SECOND_DOUBLE = 15.0;
    private final Map<String, Double> FIRST_ROW = new HashMap<>();
    private final Map<String, Double> SECOND_ROW =  new HashMap<>();

    private final NavigableMap<Date, Map<String, Double>> TEST_DATA = new TreeMap<>();

    @Before
    public void setup() {
        FIRST_ROW.clear();
        FIRST_ROW.put(JPY, FIRST_DOUBLE);
        SECOND_ROW.clear();
        SECOND_ROW.put(JPY, SECOND_DOUBLE);
        TEST_DATA.clear();
        TEST_DATA.put(FIRST_DATE, FIRST_ROW);
        TEST_DATA.put(SECOND_DATE, SECOND_ROW);
    }

    @Test
    public void when_askedToFindMaximum_then_returnsMaximum() {
        Processor processor = new RatesProcessor(TEST_DATA);
        Double actual = processor.getHighestRate(FIRST_DATE, SECOND_DATE, JPY);
        assert actual == 15.0;
    }

    @Test
    public void when_askedToFindAverage_then_returnsAverage() {
        Processor processor = new RatesProcessor(TEST_DATA);
        Double actual = processor.getAverageRate(FIRST_DATE, SECOND_DATE, JPY);
        assert actual == 10.0;
    }

    @Test
    public void when_askedForRate_then_returnsRate() {
        Processor processor = new RatesProcessor(TEST_DATA);
        Double actual = processor.getRate(FIRST_DATE, JPY);
        assert actual.equals(FIRST_DOUBLE);
    }
}
