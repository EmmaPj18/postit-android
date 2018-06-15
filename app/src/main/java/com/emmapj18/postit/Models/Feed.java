package com.emmapj18.postit.Models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



public class Feed {
    public String description;
    public String imageUrl;
    public String dateAdded;
    public String location;
    public String user;


    public static final String DATE_PATTERN = "dd-MM-yyyy hh:mm a";

    public Date getDateAdded() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
        try {
            date = format.parse(dateAdded);
            return date;
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date;
    }
}
