package com.itgenz.studentmanage.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.itgenz.studentmanage.R;
import com.itgenz.studentmanage.interfaces.ServiceAPI;
import com.itgenz.studentmanage.models.ChangePassModel;
import com.itgenz.studentmanage.network.RetrofitClient;
import com.itgenz.studentmanage.utils.SessionManager;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText editOldPass,editNewPass,editConfirmNewPass;
    private TextInputLayout layoutOldPass, layoutNewPass, layoutConfirmNewPass;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        //Bind View
        editOldPass = findViewById(R.id.editOldPass);
        editNewPass = findViewById(R.id.editNewPass);
        editConfirmNewPass = findViewById(R.id.editConfirmNewPass);
        layoutOldPass = findViewById(R.id.layoutOldPass);
        layoutNewPass = findViewById(R.id.layoutNewPass);
        layoutConfirmNewPass = findViewById(R.id.layoutConfirmNewPass);
        btnUpdate = findViewById(R.id.btnUpdate);
        //Update password button
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO ServiceAPI call change password here
                //Temporary show password
                //Toast.makeText(getApplicationContext(), editOldPass.getText().toString()+" - "+editNewPass.getText().toString()+" - "+editConfirmNewPass.getText().toString(), Toast.LENGTH_LONG).show();
                //Temporary finish activity
                //finish();
                //Validate empty input
                int error = 0;
                if(editOldPass.getText().toString().isEmpty()){
                    layoutOldPass.setHelperText("Vui lòng nhập mật khẩu cũ");
                    error = 1;
                }
                //Validate password length
                else if(editOldPass.getText().toString().length() < 8){
                    layoutOldPass.setHelperText("Mật khẩu cũ phải từ 8 ký tự trở lên");
                    error = 1;
                }
                //---------------------------------------------------------------------------------
                if(editNewPass.getText().toString().isEmpty()){
                    layoutNewPass.setHelperText("Vui lòng nhập mật khẩu mới");
                    error = 1;
                }
                else if(editNewPass.getText().toString().length() < 8){
                    layoutNewPass.setHelperText("Mật khẩu mới phải từ 8 ký tự trở lên");
                    error = 1;
                }
                //---------------------------------------------------------------------------------
                if(editConfirmNewPass.getText().toString().isEmpty()){
                    layoutConfirmNewPass.setHelperText("Vui lòng nhập xác nhận mật khẩu mới");
                    error = 1;
                }
                //Validate two new password not match
                else if(!editConfirmNewPass.getText().toString().equals(editNewPass.getText().toString())){
                    layoutConfirmNewPass.setHelperText("Hai mật khẩu không khớp");
                    error = 1;
                }
                //---------------------------------------------------------------------------------
                //Check if validation pass
                if(error != 1){
                    //Clear focus to trigger clear error text (if any)
                    editOldPass.clearFocus();
                    editNewPass.clearFocus();
                    editConfirmNewPass.clearFocus();
                    //Retrofit to call API
                    Retrofit retrofit = RetrofitClient.getClient(ServiceAPI.BASE_Service);
                    ServiceAPI api = retrofit.create(ServiceAPI.class);
                    int userId = getSharedPreferences("userLoginSession", Context.MODE_PRIVATE).getInt(SessionManager.KEY_USERID,0);
                    ChangePassModel changePassModel = new ChangePassModel(
                            userId,
                            editOldPass.getText().toString(),
                            editNewPass.getText().toString(),
                            editConfirmNewPass.getText().toString());
                    Call<String> call = api.changePassword(changePassModel);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String resultMessage = response.body();
                            if(resultMessage == null){resultMessage = "";}
                            if(resultMessage.equals("true")){
                                SweetAlertDialog dialog = new SweetAlertDialog(ChangePasswordActivity.this,SweetAlertDialog.SUCCESS_TYPE);
                                dialog.setTitle("Thành công");
                                dialog.setContentText("Thay đổi mật khẩu thành công. Vui lòng đăng nhập lại");
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        SessionManager sessionManager = new SessionManager(ChangePasswordActivity.this);
                                        sessionManager.logoutSession();
                                        Intent intent = new Intent(ChangePasswordActivity.this,LoginActivity.class);
                                        sweetAlertDialog.dismiss();
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                    }
                                });
                                dialog.show();
                            }
                            else if(resultMessage.equals("passwordError")){
                                SweetAlertDialog errorDialog = new SweetAlertDialog(ChangePasswordActivity.this,SweetAlertDialog.ERROR_TYPE);
                                errorDialog.setTitle("Thất bại");
                                errorDialog.setContentText("Mật khẩu cũ không chính xác");
                                errorDialog.setCanceledOnTouchOutside(false);
                                errorDialog.setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        errorDialog.dismissWithAnimation();
                                    }
                                });
                                errorDialog.show();
                            }
                            else{
                                SweetAlertDialog errorDialog = new SweetAlertDialog(ChangePasswordActivity.this,SweetAlertDialog.ERROR_TYPE);
                                errorDialog.setTitle("Thất bại");
                                errorDialog.setContentText("Dữ liệu gửi lên server không hợp lệ");
                                errorDialog.setCanceledOnTouchOutside(false);
                                errorDialog.setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        errorDialog.dismissWithAnimation();
                                    }
                                });
                                errorDialog.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Không truy cập được API", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        //Remove error text when user change focus out
        editOldPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    layoutOldPass.setHelperText("");
                }
            }
        });
        editNewPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    layoutNewPass.setHelperText("");
                }
            }
        });
        editConfirmNewPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    layoutConfirmNewPass.setHelperText("");
                }
            }
        });
    }
}