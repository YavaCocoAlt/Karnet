package net.harmal.karnet2.core;


import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import net.harmal.karnet2.utils.Logs;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date
{
    private byte  day  ;
    private byte  month;
    private short year ;

    @RequiresApi(Build.VERSION_CODES.O)
    public Date(@NotNull LocalDate date)
    {
        this.day    = (byte) date.getDayOfMonth();
        this.month  = (byte) date.getMonthValue();
        this.year   = (short) date.getYear     ();
    }

    public Date(int day, int month, int year)
    {
        this.day    = (byte)  day  ;
        this.month  = (byte)  month;
        this.year   = (short) year ;
    }

    /**
     * Creates a Date object using the input
     * Format: ded/MM/yyyy
     * @throws IllegalArgumentException if the input can not be transformed to a date object or isn't valid
     */
    public Date(String raw) throws IllegalArgumentException
    {
        try
        {
            // TODO: Debug Start
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            {
                String[] split = raw.split("/");
                if(split.length != 3)
                    throw new Exception("There should be 3 date entries");
                int day   = Integer.parseInt(split[0]);
                int month = Integer.parseInt(split[1]);
                int year  = Integer.parseInt(split[2]);

                Logs.debug("Day: " + day);
                Logs.debug("Month: " + month);
                Logs.debug("Year: " + year);

                if(day < 1 || day > 31)
                    throw new Exception("Day should be between 1 and 31");
                if(month < 1 || month > 12)
                    throw new Exception("Month should be between 1 and 12");
                this.day   = (byte ) day  ;
                this.month = (byte ) month;
                this.year  = (short) year ;
                return;
            }
            // Debug End

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(raw, format);
            this.day   = (byte ) date.getDayOfMonth();
            this.month = (byte ) date.getMonthValue();
            this.year  = (short) date.getYear(      );
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }

    }

    public int day()
    {
        return day;
    }
    public int month()
    {
        return month;
    }
    public int year()
    {
        return year;
    }

    public void day(int day)
    {
        this.day = (byte) day;
    }
    public void month(int month)
    {
        this.month = (byte) month;
    }
    public void year(int year)
    {
        this.year = (short) year;
    }

    @NotNull
    @Contract(" -> new")
    public static Date today()
    {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return new Date(1, 1, 2019);
        return new Date(LocalDate.now());
    }
    @NotNull
    @Contract(" -> new")
    public static Date tomorrow()
    {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return new Date(2, 1, 2019);
        return new Date(LocalDate.now().plusDays(1));
    }
    @NotNull
    @Contract("_ -> new")
    public static Date afterDays(int days)
    {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return new Date(1 + days, 1, 2019);
        return new Date(LocalDate.now().plusDays(days));
    }

    @NotNull
    @Override
    public String toString() {
        return day + "/" + month + "/" + year;
    }
}
