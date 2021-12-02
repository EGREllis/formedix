package com.formedix.loader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RatesLineParser implements Parser<NavigableMap<Date, Map<String, Double>>> {
    private static final String DATE_FORMAT_STRING = "yyyy-MM-dd";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STRING);
    private final NavigableMap<Date,Map<String, Double>> result = new TreeMap<>();
    private String[] header;
    private boolean isHeader = true;
    private boolean shownData = false;

    @Override
    public void parse(String line) {
        if (isHeader) {
            parseHeader(line);
            isHeader = false;
        } else {
            parseData(line);
        }
    }

    private void parseHeader(String line) {
        System.out.println(line);
        this.header = line.split(",");
    }

    private void parseData(String line) {
        if (!shownData) {
            System.out.println(line);
            shownData = true;
        }
        String[] data = line.split(",");
        try {
            Date date = DATE_FORMAT.parse(data[0]);
            Map<String,Double> row = new HashMap<>();
            for (int i = 1; i < header.length; i++) {
                String currency = header[i];
                String text = data[i];
                Double value;
                if ("N/A".equals(text)) {
                    value = Double.NaN;
                } else {
                    value = Double.parseDouble(text);
                }
                row.put(currency, value);
            }
            result.put(date, row);
        } catch (ParseException pe) {
            throw new RuntimeException(pe);
        }
    }

    @Override
    public NavigableMap<Date, Map<String, Double>> getResult() {
        return result;
    }
}
