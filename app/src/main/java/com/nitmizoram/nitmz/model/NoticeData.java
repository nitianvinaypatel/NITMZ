package com.nitmizoram.nitmz.model;

public class NoticeData {

    private String imageUrl;
    private String message;

    private String senderName;
    private String senderRollno;
    private String senderImage;

    private String date;
    private String time;
    private String key;

    public NoticeData() {
    }

    public NoticeData(String imageUrl, String message, String senderName, String senderRollno, String senderImage, String date, String time, String key) {
        this.imageUrl = imageUrl;
        this.message = message;
        this.senderName = senderName;
        this.senderRollno = senderRollno;
        this.senderImage = senderImage;
        this.date = date;
        this.time = time;
        this.key = key;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderRollno() {
        return senderRollno;
    }

    public void setSenderRollno(String senderRollno) {
        this.senderRollno = senderRollno;
    }

    public String getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(String senderImage) {
        this.senderImage = senderImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

