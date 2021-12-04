package com;

import com.formedix.loader.Loader;
import com.formedix.loader.RatesLineParser;
import com.formedix.loader.RatesLoader;
import com.formedix.processor.Processor;
import com.formedix.processor.RatesProcessor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.NavigableMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final String LOG_FORMAT = "The %1$s rate at %2$s is %3$f";
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static void main( String[] args ) throws Exception {
        Loader<NavigableMap<Date, Map<String, Double>>> loader = new RatesLoader(new RatesLineParser());
        NavigableMap<Date, Map<String, Double>> data = loader.load();
        Processor processor = new RatesProcessor(data);

        Date exampleDate = DATE_FORMAT.parse("2021-12-03");
        String currency = "USD";
        System.out.println(String.format(LOG_FORMAT, currency, exampleDate, processor.getRate(exampleDate, currency)));
    }
}
