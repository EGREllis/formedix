package com.formedix.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Map;
import java.util.NavigableMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class RatesLoader implements Loader<NavigableMap<Date, Map<String, Double>>> {
    private static final String SOURCE_URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist.zip";

    public RatesLoader(Parser<NavigableMap<Date,Map<String,Double>>> parser) {
        this.parser = parser;
    }

    private final Parser<NavigableMap<Date,Map<String,Double>>> parser;

    @Override
    public NavigableMap<Date, Map<String, Double>> load() throws IOException {
        URL url = new URL(SOURCE_URL);
        URLConnection connection = url.openConnection();
        try (ZipInputStream zip = new ZipInputStream(connection.getInputStream());
             BufferedReader reader = new BufferedReader(new InputStreamReader(zip))) {
            ZipEntry entry = zip.getNextEntry();
            System.out.println(entry.getName());
            String line;
            while ((line = reader.readLine()) != null) {
                parser.parse(line);
            }
        }
        return parser.getResult();
    }
}
