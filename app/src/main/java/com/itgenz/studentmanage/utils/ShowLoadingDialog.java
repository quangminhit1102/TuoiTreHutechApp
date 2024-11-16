package com.itgenz.studentmanage.utils;

import android.content.Context;
import android.graphics.Color;

import com.itgenz.studentmanage.activities.LoginActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ShowLoadingDialog {
    private static SweetAlertDialog progressDialog;
    public static void Show(Context context)
    {
        progressDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText("Đang tải ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    public static void dismissLoadingDialog()
    {
        progressDialog.dismiss();
    }
}
