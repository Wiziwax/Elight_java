package com.chestnut_java;

import java.util.List;

public class ActivityEntry {
    private String description;
    private String dateTime; // Format: "YYYY-MM-DD HH:mm"
    private int startIconResId;
    private int endIconResId;

    public ActivityEntry(String description, String dateTime, int startIconResId, int endIconResId) {
        this.description = description;
        this.dateTime = dateTime;
        this.startIconResId = startIconResId;
        this.endIconResId = endIconResId;
    }

    public String getDescription() {
        return description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getStartIconResId() {
        return startIconResId;
    }

    public int getEndIconResId() {
        return endIconResId;
    }
}


