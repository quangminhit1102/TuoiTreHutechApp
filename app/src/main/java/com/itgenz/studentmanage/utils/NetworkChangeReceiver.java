package com.itgenz.studentmanage.utils;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.appcompat.widget.AppCompatButton;

import com.itgenz.studentmanage.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!CommonConnectivity.isConnectedToInternet(context)){
            //Sweet alert dialog here
            new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("Mất kết nối!")
                    .setContentText("Vui lòng thử lại!")
                    .setCustomImage(R.drawable.wifi_off)
                    .setConfirmText("Thử lại")
                    .setConfirmButtonBackgroundColor(Color.CYAN)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            onReceive(context, intent);
                        }
                    })
                    .show();

        }
    }
}
