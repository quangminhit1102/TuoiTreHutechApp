package com.itgenz.studentmanage.models;

import java.io.Serializable;
import java.util.Date;

public class UnionModel implements Serializable {
    private int id ;
    private String unionID ;
    private String fullname ;
    private String studentCode ;
    private String email ;
    private String phone ;
    private int gender ;
    private int cityId ;
    private int districtId ;
    private int wardId ;
    private String className ;
    private String birthDay ;
    private String facultyName ;
    private int facultyId ;
    private String joinDate ;
    private String JoinDate ;
    private String address ;
    private String create_at ;
    private String create_At ;
    private String update_At ;
    private String returnDate ;
    private String ReturnDate ;
    private int create_by ;
    private int userID ;
    private int status ;
    private String birthDayString;
    private int isEmail ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnionID() {
        return unionID;
    }

    public void setUnionID(String unionID) {
        this.unionID = unionID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getjoinDate(){return JoinDate;}

    public void setjoinDate(String JoinDate){this.JoinDate = JoinDate;}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getCreate_At() {
        return create_At;
    }

    public void setCreate_At(String create_At) {
        this.create_At = create_At;
    }

    public String getUpdate_At() {
        return update_At;
    }

    public void setUpdate_At(String update_At) {
        this.update_At = update_At;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getreturnDate() {
        return ReturnDate;
    }

    public void setreturnDate(String ReturnDate) {
        this.ReturnDate = ReturnDate;
    }

    public int getCreate_by() {
        return create_by;
    }

    public void setCreate_by(int create_by) {
        this.create_by = create_by;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsEmail() {
        return isEmail;
    }

    public void setIsEmail(int isEmail) {
        this.isEmail = isEmail;
    }

    public String getBirthDayString() {
        return birthDayString;
    }

    public void setBirthDayString(String birthDayString) {
        this.birthDayString = birthDayString;
    }
}
