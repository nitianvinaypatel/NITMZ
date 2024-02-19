package com.nitmizoram.nitmz.model;

public class BusData {
    private String busNo;
    private String hostel;
    private String time;
    private String from;
    private String to;
    private String uniqueKey;

    public BusData() {
    }

    public BusData(String busNo, String hostel, String time, String from, String to, String uniqueKey) {
        this.busNo = busNo;
        this.hostel = hostel;
        this.time = time;
        this.from = from;
        this.to = to;
        this.uniqueKey = uniqueKey;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
}
