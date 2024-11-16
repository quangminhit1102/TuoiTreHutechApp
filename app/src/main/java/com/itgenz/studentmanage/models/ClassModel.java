package com.itgenz.studentmanage.models;

public class ClassModel {
    private int classID;
    private String className;
    private int facultyID;
    private String facultyName;
    private int totalStudent;
    private int status;

    public ClassModel(){}

    public ClassModel(int classID, String className, int facultyID, String facultyName, int totalStudent, int status) {
        this.classID = classID;
        this.className = className;
        this.facultyID = facultyID;
        this.facultyName = facultyName;
        this.totalStudent = totalStudent;
        this.status = status;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public int getTotalStudent() {
        return totalStudent;
    }

    public void setTotalStudent(int totalStudent) {
        this.totalStudent = totalStudent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
