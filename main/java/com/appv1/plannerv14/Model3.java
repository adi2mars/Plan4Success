package com.appv1.Plan4Success;

public class Model3 implements Comparable<Model3>  {
    public String task3;
    public int priority3;
    public String time3;
    public String SubGoal;
    public int positionSpinner;

    Model3(String task, int priority, String time, String SubGoal, int positionSpinner) {
        this.task3 = task;
        this.priority3 = priority;
        this.time3 = time;
        this.SubGoal = SubGoal;
        this.positionSpinner = positionSpinner;
    }


    @Override
    public int compareTo(Model3 o) {
        if(this.priority3 < o.priority3) {
            return -1;
        } else if (this.priority3 > o.priority3) {
            return 1;
        } else {
            return 0;
        }
    }
}


