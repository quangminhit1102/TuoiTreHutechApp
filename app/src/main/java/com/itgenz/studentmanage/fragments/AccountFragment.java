package com.itgenz.studentmanage.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.CompletionInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itgenz.studentmanage.R;
import com.itgenz.studentmanage.activities.ChangePasswordActivity;
import com.itgenz.studentmanage.activities.InfoActivity;
import com.itgenz.studentmanage.activities.LoginActivity;
import com.itgenz.studentmanage.adapters.DistrictAdapter;
import com.itgenz.studentmanage.adapters.ProvinceAdapter;
import com.itgenz.studentmanage.adapters.WardAdapter;
import com.itgenz.studentmanage.interfaces.ProvinceAPI;
import com.itgenz.studentmanage.interfaces.ServiceAPI;
import com.itgenz.studentmanage.models.ChangeInfoModel;
import com.itgenz.studentmanage.models.Province;
import com.itgenz.studentmanage.utils.RetroClient;
import com.itgenz.studentmanage.utils.SessionManager;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountFragment extends Fragment {

    private Button userInformBtn, changePassBtn, logoutBtn;
    private TextView txtFullname, txtEmail;
    private Context context;

    public AccountFragment(Context context){
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        //Bind view
        userInformBtn = view.findViewById(R.id.userInformBtn);
        changePassBtn = view.findViewById(R.id.changePassBtn);
        txtFullname = view.findViewById(R.id.textView14);
        txtEmail = view.findViewById(R.id.textView15);
        logoutBtn = view.findViewById(R.id.logoutBtn);

        //Set name and email information
        txtFullname.setText(context.getSharedPreferences("userLoginSession",Context.MODE_PRIVATE).getString(SessionManager.KEY_FULLNAME,""));
        txtEmail.setText(context.getSharedPreferences("userLoginSession",Context.MODE_PRIVATE).getString(SessionManager.KEY_EMAIL,""));

        //Button click event
        userInformBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InfoActivity.class);
                startActivity(intent);
            }
        });
        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sesManager = new SessionManager(context);
                sesManager.logoutSession();
                Toast.makeText(context, "Bạn đã đăng xuất thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        return view;
    }
}