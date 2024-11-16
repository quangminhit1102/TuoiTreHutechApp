package com.itgenz.studentmanage.interfaces;

import com.itgenz.studentmanage.models.ChangeInfoModel;
import com.itgenz.studentmanage.models.ChangePassModel;
import com.itgenz.studentmanage.models.FacultyModel;
import com.itgenz.studentmanage.models.LoginModel;
import com.itgenz.studentmanage.models.ReqModel;
import com.itgenz.studentmanage.models.ResFacultyModel;
import com.itgenz.studentmanage.models.ResLoginModel;
import com.itgenz.studentmanage.models.ResUnionModel;
import com.itgenz.studentmanage.models.UnionModel;
import com.itgenz.studentmanage.models.UserModel;


import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceAPI {
    String BASE_Service = "https://test.tuoitrehutech.com/";

    // Login API
    @POST("api/loginapi/login")
    Observable<ResLoginModel> login(@Body LoginModel loginModel);

    // Login API
    @POST("api/loginapi/login")
    Call<ResLoginModel> loginCall(@Body LoginModel loginModel);
    //=====================UnionBook================================

    // Get all unionbooks
    @POST("api/unionapi/index")
    Call<ResUnionModel> getListUnion(@Body ReqModel model);

    // Change Status
    @POST("api/unionapi/changestatus")
    Call<String> changeStatus(@Query("id") int UnionId);

    // View Union
    @POST("api/unionapi/view/")
    Call<UnionModel> getUnionDetail(@Query("id") int UnionId);

    // Update Union
    @POST("api/unionapi/update")
    Call<String> updateUnion(@Body UnionModel unionModel);

    //=====================Faculty===============================

    // Get list Faculty
    @POST("api/facultyapi/index")
    Call<ResFacultyModel> getListFaculty(@Body ReqModel model);

    // Add Faculty
    @POST("api/facultyapi/insert")
    Call<String> insertFaculty(@Body FacultyModel model);

    // Update Faculty
    @POST("api/facultyapi/update")
    Call<String> updateFaculty(@Body FacultyModel model);

    //==========================Info=================================
    // Change Info
    @POST("api/profileapi/update")
    Call<String> changeInfo(@Body UserModel model);

    //Change password
    @POST("api/profileapi/changepassword")
    Call<String> changePassword(@Body ChangePassModel model);
}
