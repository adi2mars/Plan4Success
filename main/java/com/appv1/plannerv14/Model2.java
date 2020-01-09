package com.appv1.Plan4Success;

import androidx.work.PeriodicWorkRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Model2
        implements Comparable<Model2>
        {

    public String Assignment;
    public String DueDate;
    public int positionSpinner;
    PeriodicWorkRequest periodicWork;




            Model2(String task,String DueDate, int positionSpinner) {
        this.Assignment = task;
        this.DueDate = DueDate;
        this.positionSpinner = positionSpinner;
        //PeriodicWorkRequest periodicWork = this.periodicWork;

    }


   @Override
    public int compareTo(Model2 o) {
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
       try {
           Date start = sdf.parse(this.DueDate);
           Date end = sdf.parse(o.DueDate);
           if (start.after(end)) {
               return 1;
           } else if (start.before(end)) {
               return -1;
           } else {
               return 0;
           }

       } catch (ParseException e) {
           e.printStackTrace();
           return 0;
       }
   }
        /*if(this.priority < o.priority) {
            return -1;
        } else if (this.priority > o.priority) {
            return 1;
        } else {
            return 0;
        }*/
}

