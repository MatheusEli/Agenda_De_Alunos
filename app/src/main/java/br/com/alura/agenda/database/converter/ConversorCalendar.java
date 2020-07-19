package br.com.alura.agenda.database.converter;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class ConversorCalendar {

    @TypeConverter
    public static Long paraLong(Calendar momento){
        if(momento != null){
            return momento.getTimeInMillis();
        }
        return null;
    }

    @TypeConverter
    public static Calendar paraCalendar(Long valor){
        Calendar momento = Calendar.getInstance();
        if (valor != null) {
            momento.setTimeInMillis(valor);
        }
        return momento;
    }
}
