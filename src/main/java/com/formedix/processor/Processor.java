package com.formedix.processor;

import java.util.Date;

public interface Processor {
    Double getRate(Date date, String currency);
    Double convert(Date date, String from, String to, Double amount);
    Double getHighestRate(Date start, Date end, String currency);
    Double getAverageRate(Date start, Date end, String currency);
}
