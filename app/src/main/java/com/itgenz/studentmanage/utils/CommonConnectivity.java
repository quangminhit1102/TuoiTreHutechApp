package com.itgenz.studentmanage.utils;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import com.itgenz.studentmanage.R;
import com.itgenz.studentmanage.activities.LoginActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CommonConnectivity {
    public static boolean isConnectedToInternet(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        }else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                   for(int i = 0;i < info.length; i++)
                   {
                       if(info[i].getState()==NetworkInfo.State.CONNECTED)
                           return true;
                   }
                }
            }
        }
//        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
//                .setTitleText("Mất kết nối!")
//                .setContentText("Vui lòng thử lại!")
//                .setCustomImage(R.drawable.wifi_off)
//                .setConfirmText("Thử lại")
//                .setConfirmButtonBackgroundColor(Color.CYAN)
//                .show();
        return false;
    }
}
