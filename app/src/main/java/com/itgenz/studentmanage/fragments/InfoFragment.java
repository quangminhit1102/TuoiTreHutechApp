package com.itgenz.studentmanage.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.itgenz.studentmanage.R;
import com.itgenz.studentmanage.activities.AppInfoActivity;
import com.zcw.togglebutton.ToggleButton;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class InfoFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private LinearLayout lNoti, lSensor, lMail, lInfo;
    private ToggleButton tgButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        lInfo = view.findViewById(R.id.infoLinear);
        tgButton = view.findViewById(R.id.noti);
        lInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext().getApplicationContext(), AppInfoActivity.class));
            }
        });
        readPref();
        tgButton.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if(on == true)
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Nhận thông báo?")
                            .setContentText("Bạn sẽ nhận được thông báo từ app!")
                            .setConfirmText("Có")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    savePref("noti","true");
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .setCancelText("Không")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    tgButton.setToggleOff();
                                }
                            })
                            .show();
                }
                else
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Không nhận thông báo?")
                            .setContentText("Bạn sẽ không nhận bất kỳ thông báo từ app!")
                            .setConfirmText("Có")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    savePref("noti","false");
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .setCancelText("Không")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    tgButton.setToggleOn();
                                }
                            })
                            .show();
                }
            }
        });
        return view;
    }
    private void savePref(String key, String value)
    {
        SharedPreferences preferences  = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    private void readPref()
    {
        SharedPreferences preferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String noti = preferences.getString("noti", "");
        if(noti.equalsIgnoreCase(""))
        {
            noti = "true";
            savePref("noti","true");
        }
        if(noti.equals("true"))
        {
            tgButton.setToggleOn();
        }
        else
        {
            tgButton.setToggleOff();
        }
    }
}