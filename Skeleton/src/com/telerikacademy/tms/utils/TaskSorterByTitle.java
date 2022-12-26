package com.telerikacademy.tms.utils;

import com.telerikacademy.tms.models.tasks.contracts.Task;

import java.util.Comparator;

public class TaskSorterByTitle implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {
        if(o1.getTitle().equalsIgnoreCase(o2.getTitle())){
            return 0;
        }else {
            return -1;
        }
    }
}
