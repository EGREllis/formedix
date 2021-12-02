package com.formedix.loader;

import org.junit.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.NavigableMap;

public class RatesLineParserTest {
    private static final String HEADER = "Date,USD,JPY,BGN,CYP,CZK,DKK,EEK,GBP,HUF,LTL,LVL,MTL,PLN,ROL,RON,SEK,SIT,SKK,CHF,ISK,NOK,HRK,RUB,TRL,TRY,AUD,BRL,CAD,CNY,HKD,IDR,ILS,INR,KRW,MXN,MYR,NZD,PHP,SGD,THB,ZAR,";
    private static final String FIRST_LINE =  "2021-12-01,1.1314,128.27,1.9558,N/A,25.443,7.4362,N/A,0.85,363.78,N/A,N/A,N/A,4.6283,N/A,4.9467,10.226,N/A,N/A,1.0427,146.6,10.2195,7.5185,83.6246,N/A,15.1664,1.5849,6.3498,1.4433,7.2065,8.8154,16249.4,3.5623,84.7485,1330.53,24.0869,4.7745,1.6548,57.004,1.5431,38.151,17.9011,";

    @Test
    public void when_givenAHeaderAndOneLine_then_resultMatchesExpectations() {
        Parser<NavigableMap<Date, Map<String,Double>>> parser = new RatesLineParser();
        parser.parse(HEADER);
        parser.parse(FIRST_LINE);
        NavigableMap<Date, Map<String,Double>> result = parser.getResult();

        assert result.size() == 1;
        for(Map.Entry<Date, Map<String, Double>> entry : result.entrySet()) {
            assert new SimpleDateFormat("yyyy-MM-dd").format(entry.getKey()).equals("2021-12-01");

            Map<String, Double> row = entry.getValue();
            assert row.get("USD") == 1.1314;
            assert row.get("JPY") == 128.27;
            assert row.get("BGN") == 1.9558;
            assert row.get("CYP").isNaN();
        }
    }
}
