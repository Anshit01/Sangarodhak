package com.hashbash.sangarodhak.Modals;

public class FunZoneModal {
    private String title, activityToOpen;
    private String[] subItems, activityExtra;
    private boolean isExpanded = false;

    public FunZoneModal(String title, String activityToOpen, String[] subItems, String[] activityExtra) {
        this.title = title;
        this.activityToOpen = activityToOpen;
        this.subItems = subItems;
        this.activityExtra = activityExtra;
    }

    public String getTitle() {
        return title;
    }

    public String getActivityToOpen() {
        return activityToOpen;
    }

    public String[] getSubItems() {
        return subItems;
    }

    public String[] getActivityExtra() {
        return activityExtra;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
