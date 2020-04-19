package team.hashbash.sangarodhak.Modals;

public class NoticeDataModal {

    private String imageLink, from, text, videoLink;

    public NoticeDataModal(String imageLink, String from, String text, String videoLink) {
        this.imageLink = imageLink;
        this.from = from;
        this.text = text;
        this.videoLink = videoLink;
    }

    public String getImageLink() {
        return "" + imageLink;
    }

    public String getFrom() {
        return "" + from;
    }

    public String getText() {
        return "" + text;
    }

    public String getVideoLink() {
        return "" + videoLink;
    }
}
