package com.chestnut_java;

import java.util.List;
public class GroupedActivity {
    private String monthName;
    private List<ActivityEntry> activities;

    public GroupedActivity(String monthName, List<ActivityEntry> activities) {
        this.monthName = monthName;
        this.activities = activities;
    }

    public String getMonthName() {
        return monthName;
    }

    public List<ActivityEntry> getActivities() {
        return activities;
    }
}