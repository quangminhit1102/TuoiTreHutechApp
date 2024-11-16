package com.itgenz.studentmanage.models;

public class ChangePassModel {
    private int id;
    private String passwordOld;
    private String passwordNew;
    private String confirmPassword;

    public ChangePassModel(int id, String passwordOld, String passwordNew, String confirmPassword) {
        this.id = id;
        this.passwordOld = passwordOld;
        this.passwordNew = passwordNew;
        this.confirmPassword = confirmPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }

    public String getPasswordNew() {
        return passwordNew;
    }

    public void setPasswordNew(String passwordNew) {
        this.passwordNew = passwordNew;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
