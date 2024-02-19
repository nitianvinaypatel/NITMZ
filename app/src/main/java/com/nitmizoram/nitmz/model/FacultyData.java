package com.nitmizoram.nitmz.model;

public class FacultyData {
    private String FimageUrl, name,designation,department,contact,key;

    public FacultyData() {
    }

    public FacultyData(String fimageUrl, String name, String designation, String department, String contact, String key) {
        FimageUrl = fimageUrl;
        this.name = name;
        this.designation = designation;
        this.department = department;
        this.contact = contact;
        this.key = key;
    }

    public String getFimageUrl() {
        return FimageUrl;
    }

    public void setFimageUrl(String fimageUrl) {
        FimageUrl = fimageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
