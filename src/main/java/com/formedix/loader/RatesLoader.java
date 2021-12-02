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

    @Override
    public NavigableMap<Date, Map<String, Double>> load() throws IOException {
        URL url = new URL(SOURCE_URL);
        URLConnection connection = url.openConnection();
        ZipInputStream zip = new ZipInputStream(connection.getInputStream());
        ZipEntry entry = zip.getNextEntry();
        System.out.println(entry.getName());
        BufferedReader reader = new BufferedReader(new InputStreamReader(zip));
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        return null;
    }
}
