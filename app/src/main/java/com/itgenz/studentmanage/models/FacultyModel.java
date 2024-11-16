package com.itgenz.studentmanage.models;

public class FacultyModel {
    private int facultyID;
    private String facultyName;
    private String phone ;
    private int totalClass ;
    private int status ;

    public FacultyModel(){}

    public FacultyModel(int facultyID, String facultyName, String phone, int totalClass, int status) {
        this.facultyID = facultyID;
        this.facultyName = facultyName;
        this.phone = phone;
        this.totalClass = totalClass;
        this.status = status;
    }

    public FacultyModel(String facultyName, String phone) {
        this.facultyName = facultyName;
        this.phone = phone;
    }

    public FacultyModel(int facultyID, String facultyName, String phone) {
        this.facultyID = facultyID;
        this.facultyName = facultyName;
        this.phone = phone;
    }

    public int getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(int facultyID) {
        this.facultyID = facultyID;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTotalClass() {
        return totalClass;
    }

    public void setTotalClass(int totalClass) {
        this.totalClass = totalClass;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
