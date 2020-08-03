package com.company;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

    //creating method to display time
    public String displayTime(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:m:s");
        return simpleDateFormat.format(date);
    }
}
