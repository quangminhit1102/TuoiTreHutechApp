package com.itgenz.studentmanage.activities;

import static com.itgenz.studentmanage.interfaces.ServiceAPI.BASE_Service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.itgenz.studentmanage.R;
import com.itgenz.studentmanage.interfaces.ServiceAPI;
import com.itgenz.studentmanage.models.LoginModel;
import com.itgenz.studentmanage.models.ResLoginModel;
import com.itgenz.studentmanage.utils.NetworkChangeReceiver;
import com.itgenz.studentmanage.utils.SecureSharedPref;
import com.itgenz.studentmanage.utils.SessionManager;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity{
    private TextInputEditText edtUsername, edtPassword;
    private MaterialCheckBox ckSave;
    private MaterialButton btnLogin, btnForget;
    SweetAlertDialog pDialog;

    @Override
    protected void onStart() {
        // check session
        SessionManager mySession = new SessionManager(LoginActivity.this);
        if(mySession.checkLogin() == true)
        {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        //Register receiver for network alert
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver,filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeReceiver);
        super.onStop();
    }

    //Check if network connected
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Find View item
        edtUsername = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);
        ckSave = findViewById(R.id.ckSave);
        btnLogin = findViewById(R.id.btnLogin);
        btnForget = findViewById(R.id.btnForget);
        //==========================================================================================
        SharedPreferences getPref = SecureSharedPref.getEncryptedSharedPreferences( LoginActivity.this);
        boolean isSaved = getPref.getBoolean("saved", false);
        if(isSaved==true)
        {

            edtUsername.setText(getPref.getString("username", null));
            edtPassword.setText(getPref.getString("password", null));
            ckSave.setChecked(true);
        }
        // btnLogin click event
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Đang tải ...");
                pDialog.setCancelable(false);
                pDialog.show();
                if(edtUsername.getText().toString().equalsIgnoreCase("") || edtPassword.getText().toString().equalsIgnoreCase(""))
                {
                    pDialog.dismiss();
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Thông báo!")
                            .setContentText("Vui lòng nhập đầy đủ thông tin")
                            .show();
                }
                else
                {
                    // Request API with LoginModel
                    LoginModel model= new LoginModel(edtUsername.getText().toString(),edtPassword.getText().toString());
                    callApi(model);
                }
            }
        });
        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
    // callAPI Login
    //=>Return UserModel if have account
    private void callApi(LoginModel model){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        new CompositeDisposable().add(requestInterface.login(model)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }
    private void handleResponse(ResLoginModel model)
    {
        pDialog.dismiss();
       if(model.getStatus()==200)
       {
           // save if Checkbox is checked
           SharedPreferences.Editor editor = SecureSharedPref.getEncryptedSharedPreferences(LoginActivity.this).edit();
           if(ckSave.isChecked())
           {
               editor.putString("username", edtUsername.getText().toString());
               editor.putString("password", edtPassword.getText().toString());
               editor.putBoolean("saved", true);
               editor.commit();
           }
           else
           {
               editor.clear();
               editor.putBoolean("saved", false);
               editor.commit();
           }
           //==============================================================================
           SessionManager mySession = new SessionManager(LoginActivity.this);
           mySession.createLoginSession(model.getUser().getUserID(),
                   model.getUser().getStudentCode(),
                   model.getUser().getFullname(),
                   model.getUser().getEmail(),
                   model.getUser().getPhone(),
                   model.getUser().getBirthDay());
           startActivity(new Intent(LoginActivity.this, MainActivity.class));
       }
       else
       {
           new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                   .setTitleText("Thất bại!")
                   .setContentText("Tên đăng nhập hoặc mật khẩu sai!")
                   .show();
       }
    }
    private void handleError(Throwable error)
    {
        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Thất bại!")
                .setContentText("Vui lòng kiểm tra kết nối!")
                .show();
    }
}
