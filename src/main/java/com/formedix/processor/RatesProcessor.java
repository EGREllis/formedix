package com.formedix.processor;

import java.util.Date;
import java.util.Map;
import java.util.NavigableMap;

public class RatesProcessor implements Processor {
    private NavigableMap<Date, Map<String, Double>> data;

    public RatesProcessor(NavigableMap<Date, Map<String, Double>> data) {
        this.data = data;
    }

    private Double getRateFromData(Date date, String currency, NavigableMap<Date, Map<String,Double>> data) {
        Double result = Double.NaN;
        Map<String,Double> row = data.get(date);
        if (row != null) {
            if (row.containsKey(currency)) {
                result = row.get(currency);
            }
        }
        return result;
    }

    @Override
    public Double getRate(Date date, String currency) {
        return getRateFromData(date, currency, data);
    }

    private Double getEffectiveExchangeRate(Date date, String from, String to) {
        Double result = Double.NaN;
        Double fromRate = getRateFromData(date, from, data);
        Double toRate = getRateFromData(date, to, data);
        if (!fromRate.isNaN() && !toRate.isNaN()) {
            Double effectiveRate = toRate / fromRate;  // Might be the other way arround
            result = effectiveRate;
        }
        return result;
    }

    @Override
    public Double convert(Date date, String from, String to, Double amount) {
        Double effectiveExchangeRate = getEffectiveExchangeRate(date, from, to);
        return amount * effectiveExchangeRate;
    }

    @Override
    public Double getHighestRate(Date start, Date stop, String currency) {
        NavigableMap<Date, Map<String,Double>> range = data.subMap(start, true, stop, true);
        boolean first = true;
        Double maxRate = Double.NaN;
        for (Map.Entry<Date, Map<String,Double>> entry : range.entrySet()) {
            Double rate = getRateFromData(entry.getKey(), currency, range);
            if (!rate.isNaN()) {
                if (first) {
                    maxRate = rate;
                    first = false;
                } else if (!first && rate.compareTo(maxRate) > 0) {
                    maxRate = rate;
                }
            }
        }
        return maxRate;
    }

    @Override
    public Double getAverageRate(Date start, Date stop, String currency) {
        NavigableMap<Date, Map<String, Double>> range = data.subMap(start, true, stop, true);
        Double averageRate = 0.0;
        int numberOfRates = 0;
        for (Map.Entry<Date, Map<String, Double>> entry : range.entrySet()) {
            Double rate = getRateFromData(entry.getKey(), currency, range);
            if (!rate.isNaN()) {
                averageRate += rate;
                numberOfRates++;
            }
        }
        averageRate /= numberOfRates;
        return averageRate;
    }
}
