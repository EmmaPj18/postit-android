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


    public static final String DATE_PATTERN = "dd-MM-yyyy hh:mm a";

    public Date getDateAdded() {
        DateFormat format = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
        try {
            return format.parse(dateAdded);
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
