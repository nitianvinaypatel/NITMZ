package com.example.nitmz.model;

public class User {

    private String userId;

    private String image;
    private String name;
    private String rollNo;
    private String phone;
    private String email;
    private String course;
    private String branch;
    private String semester;
    private String hostel;

    private String isAdmin;

    public User() {
    }


    public User(String userId, String name, String rollNo, String phoneNo, String email, String course, String branch, String semester, String hostel, String isAdmin) {
        this.userId = userId;
        this.name = name;
        this.rollNo = rollNo;
        this.phone = phoneNo;
        this.email = email;
        this.course = course;
        this.branch = branch;
        this.semester = semester;
        this.hostel = hostel;
        this.isAdmin = isAdmin;
    }


    public String getuserId() {
        return userId;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo.toUpperCase();
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }


    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

}

