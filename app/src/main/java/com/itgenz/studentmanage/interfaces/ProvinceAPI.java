package com.itgenz.studentmanage.interfaces;

import com.itgenz.studentmanage.models.Province;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProvinceAPI {

    public static final String BASE_URL = "https://provinces.open-api.vn/api/";

    @GET("?depth=3")
    Call<List<Province>> getListProvince();
}
