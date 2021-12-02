package com.formedix.loader;

import java.util.Date;
import java.util.Map;
import java.util.NavigableMap;

public class RatesLineParser implements Parser<NavigableMap<Date, Map<String, Double>>> {
    boolean isHeader = true;
    boolean shownData = false;

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
    }

    private void parseData(String line) {
        if (!shownData) {
            System.out.println(line);
            shownData = true;
        }
    }

    @Override
    public NavigableMap<Date, Map<String, Double>> getResult() {
        return null;
    }
}
