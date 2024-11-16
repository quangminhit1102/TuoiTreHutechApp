package com.itgenz.studentmanage.models;

public class ResLoginModel {
    private int status;
    private UserModel user;

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
