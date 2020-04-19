package team.hashbash.sangarodhak.Modals;

public class FunZoneModal {
    private String title, activityToOpen;
    private String[] subItems, activityExtra, imageLink;
    private boolean isExpanded = false;

    public FunZoneModal(String title, String activityToOpen, String[] imageLink, String[] subItems, String[] activityExtra) {
        this.title = title;
        this.activityToOpen = activityToOpen;
        this.subItems = subItems;
        this.activityExtra = activityExtra;
        this.imageLink = imageLink;
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

    public String[] getImageLink() {
        return imageLink;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
