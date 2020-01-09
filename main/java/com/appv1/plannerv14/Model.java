package com.appv1.Plan4Success;

public class Model implements Comparable<Model>  {
    public String task;
    public int priority;
    public int time;

    Model(String task, int priority, int time) {
        this.task = task;
        this.priority = priority;
        this.time = time;
    }


    @Override
    public int compareTo(Model o) {
        if(this.priority < o.priority) {
            return -1;
        } else if (this.priority > o.priority) {
            return 1;
        } else {
            return 0;
        }
    }
}

