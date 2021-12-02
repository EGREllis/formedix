package com.formedix.loader;

import java.util.Date;
import java.util.Map;
import java.util.NavigableMap;

public class RatesLineParser implements Parser<NavigableMap<Date, Map<String, Double>>> {
    boolean isHeader = true;

    @Override
    public void parse(String line) {
        if (isHeader) {
            parseHeader(line);
        } else {
            parseData(line);
        }
    }

    private void parseHeader(String line) {
        
    }

    private void parseData(String line) {

    }

    @Override
    public NavigableMap<Date, Map<String, Double>> getResult() {
        return null;
    }
}
