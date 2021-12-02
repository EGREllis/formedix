package com;

import com.formedix.loader.Loader;
import com.formedix.loader.RatesLineParser;
import com.formedix.loader.RatesLoader;

import java.util.Date;
import java.util.Map;
import java.util.NavigableMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        Loader<NavigableMap<Date, Map<String, Double>>> loader = new RatesLoader(new RatesLineParser());
        loader.load();
    }
}
