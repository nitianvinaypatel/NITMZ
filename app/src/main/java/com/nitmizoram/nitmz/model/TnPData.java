package com.nitmizoram.nitmz.model;

public class TnPData {

    private String imageUrl,name,branch,phone,email;

    public TnPData() {
    }

    public TnPData(String imageUrl, String name, String branch, String phone, String email) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.branch = branch;
        this.phone = phone;
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
