package com.emmapj18.postit.Helpers;

import com.emmapj18.postit.Models.Feed;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonHelper {

    public static String convertDateToString(Date indate)
    {
        String dateString = null;
        DateFormat formatter = new SimpleDateFormat(Feed.DATE_PATTERN, Locale.getDefault());
        try{
            dateString = formatter.format( indate );
        }catch (Exception ex ){
            ex.printStackTrace();
        }
        return dateString;
    }
}
