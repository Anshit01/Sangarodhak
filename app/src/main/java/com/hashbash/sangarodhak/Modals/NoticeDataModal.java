package com.hashbash.sangarodhak.Modals;

public class NoticeDataModal {

    private String imageLink, from, text;

    public NoticeDataModal(String imageLink, String from, String text) {
        this.imageLink = imageLink;
        this.from = from;
        this.text = text;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }
}
