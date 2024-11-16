package com.itgenz.studentmanage.models;

import java.util.ArrayList;

public class ResFacultyModel {
    private int draw;
    private int recordsTotal;
    private int recordsFiltered;
    private ArrayList<FacultyModel> data;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public ArrayList<FacultyModel> getData() {
        return data;
    }

    public void setData(ArrayList<FacultyModel> data) {
        this.data = data;
    }
}
