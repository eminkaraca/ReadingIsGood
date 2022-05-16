package com.readingisgood.Service.Util;

import com.readingisgood.Service.Exception.RigCustomeException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Dater {

    public static LocalDate getLocalDate(Integer date){
        DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
        return LocalDate.parse(date.toString(), formatter);

    }
    public static LocalDate checkDate(Integer date){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
            return LocalDate.parse(date.toString(), formatter);
        }catch (Exception e){
            throw new RigCustomeException(HttpStatus.NOT_ACCEPTABLE,String.format("Date is not correct %s",date.toString()));
        }
    }
}
