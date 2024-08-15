package com.work.biorhythm;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author linux
 */
public final class Biorhythm {
    
    private Biorhythm() {
    }

    // Calcular el número de días vividos
    public static long getDays(int year, int month, int day) {
        try {
            LocalDate birthDate = LocalDate.of(year, month, day);
            LocalDate currentDate = LocalDate.now();
            return ChronoUnit.DAYS.between(birthDate, currentDate);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return -1;
        }        
    }
    
    public static double getPhysical(long days) {
        return Math.sin(2 * Math.PI * days / 23);
        
    }
    
    public static double getIntellectual(long days) {
        return Math.sin(2 * Math.PI * days / 33);
    }
    
    public static double getEmotional(long days) {
        return Math.sin(2 * Math.PI * days / 28);
    }
}
