package com.itgenz.studentmanage.models;

import java.util.ArrayList;

public class ResUnionModel {
    private int draw;
    private int recordsTotal;
    private int recordsFiltered;
    private ArrayList<UnionModel> data;

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

    public ArrayList<UnionModel> getData() {
        return data;
    }

    public void setData(ArrayList<UnionModel> data) {
        this.data = data;
    }
}
