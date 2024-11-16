package com.itgenz.studentmanage.activities;

import static android.widget.Toast.LENGTH_LONG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.android.material.textfield.TextInputLayout;
import com.itgenz.studentmanage.R;
import com.itgenz.studentmanage.adapters.ProvinceAdapter;
import com.itgenz.studentmanage.interfaces.ProvinceAPI;
import com.itgenz.studentmanage.interfaces.ServiceAPI;
import com.itgenz.studentmanage.models.ChangeInfoModel;
import com.itgenz.studentmanage.models.LoginModel;
import com.itgenz.studentmanage.models.Province;
import com.itgenz.studentmanage.models.ResLoginModel;
import com.itgenz.studentmanage.models.UserModel;
import com.itgenz.studentmanage.utils.RetroClient;
import com.itgenz.studentmanage.utils.SessionManager;
import com.itgenz.studentmanage.utils.ShowLoadingDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoActivity extends AppCompatActivity {

    private EditText editName, editEmail, editBirthDay, editPhone, editAddress;
    private Button btnUpdate;
    private TextView fullname;
    private SmartMaterialSpinner<Province> spinnerProvince;
    private SmartMaterialSpinner<Province.District> spinnerDistrict;
    private SmartMaterialSpinner<Province.District.Ward> spinnerWard;
    private TextInputLayout layoutEditName, layoutEditEmail, layoutEditBirthDay, layoutEditPhone;
    int provinceIdSelected = 0;
    int districtIdSelected = 0;
    int wardIdSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        //Bind view
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editBirthDay = findViewById(R.id.editBirthDay);
        editPhone = findViewById(R.id.editPhone);
        editAddress = findViewById(R.id.editAddress);
        btnUpdate = findViewById(R.id.btnUpdate);
        layoutEditName = findViewById(R.id.layoutEditName);
        layoutEditEmail = findViewById(R.id.layoutEditEmail);
        layoutEditBirthDay = findViewById(R.id.layoutEditBirthDay);
        layoutEditPhone = findViewById(R.id.layoutEditPhone);
        btnUpdate = findViewById(R.id.btnUpdate);
        fullname = findViewById(R.id.fullname);
        //Clear error text when lost focus
        editName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                layoutEditName.setHelperText("");
            }
        });
        editEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                layoutEditEmail.setHelperText("");
            }
        });
        editBirthDay.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                layoutEditBirthDay.setHelperText("");
            }
        });
        editPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                layoutEditPhone.setHelperText("");
            }
        });
        //Dropdown menu for province
        spinnerProvince = findViewById(R.id.spinnerProvince);
        spinnerDistrict = findViewById(R.id.spinnerDistrict);
        spinnerWard = findViewById(R.id.spinnerWard);
        // -=-=-=-=-=-=-=-= Call test API for get info =-=-=-=-=-=-=-=-
        ShowLoadingDialog.Show(InfoActivity.this);
        Retrofit apiClient = RetroClient.getInstance(ServiceAPI.BASE_Service);
        ServiceAPI api = apiClient.create(ServiceAPI.class);
        LoginModel login = new LoginModel();
        login.setUsername(getSharedPreferences("userLoginSession",MODE_PRIVATE).getString(SessionManager.KEY_USERNAME,""));
        login.setPassword("123456789");
        Call<ResLoginModel> call = api.loginCall(login);
        call.enqueue(new Callback<ResLoginModel>() {
            @Override
            public void onResponse(Call<ResLoginModel> call, Response<ResLoginModel> response) {
                UserModel model = response.body().getUser();
                editName.setText(model.getFullname());
                editEmail.setText(model.getEmail());
                editBirthDay.setText(model.getBirthDay());
                editPhone.setText(model.getPhone());
                editAddress.setText(model.getAddress());
                fullname.setText(model.getFullname());
                //Get id of province, district and ward
                int provinceId = model.getCityID();
                int districtId = model.getDistrictID();
                int wardId = model.getWardID();
                //Assign to global variable
                provinceIdSelected = provinceId;
                districtIdSelected = districtId;
                wardIdSelected = wardId;
                // -=-=-=-=-=-=-=-=-=-= Start of retrofit for province api =-=-=-=-=-=-=-=-=-=-
                Retrofit retrofit = new Retrofit.Builder().baseUrl(ProvinceAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                ProvinceAPI api = retrofit.create(ProvinceAPI.class);
                Call<List<Province>> callProvince = api.getListProvince();
                callProvince.enqueue(new Callback<List<Province>>() {
                    @Override
                    public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                        ShowLoadingDialog.dismissLoadingDialog();
                        //Get data to list
                        List<Province> listProvince = response.body();
                        //Load data to spinner district
                        spinnerProvince.setItem(listProvince);
                        spinnerProvince.setSelection(listProvince.indexOf(new Province(provinceId)));
                        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                                provinceIdSelected = listProvince.get(i).getCode();
                                List<Province.District> listDistrict = listProvince.get(i).getDistricts();
                                spinnerDistrict.setItem(listDistrict);
                                //Set item to empty list for ward
                                spinnerWard.setItem(new ArrayList<>());
                                //Set district and ward selected id to 0
                                districtIdSelected = 0;
                                wardIdSelected = 0;
                                spinnerDistrict.setSelection(listDistrict.indexOf(new Province.District(districtId)));
                                spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int j, long id) {
                                        districtIdSelected = listDistrict.get(j).getCode();
                                        List<Province.District.Ward> listWard = listDistrict.get(j).getWards();
                                        //Set ward selected id to 0
                                        wardIdSelected = 0;
                                        spinnerWard.setItem(listWard);
                                        spinnerWard.setSelection(listWard.indexOf(new Province.District.Ward(wardId)));
                                        spinnerWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int k, long id) {
                                                wardIdSelected = listWard.get(k).getCode();
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<Province>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Không truy cập được API", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<ResLoginModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Không truy cập được API", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Validate input
                int error = 0;
                if(editName.getText().toString().isEmpty()){
                    layoutEditName.setHelperText("Vui lòng nhập họ tên");
                    error = 1;
                }
                if(editEmail.getText().toString().isEmpty()){
                    layoutEditEmail.setHelperText("Vui lòng nhập email");
                    error = 1;
                }
                if(editBirthDay.getText().toString().isEmpty()){
                    layoutEditBirthDay.setHelperText("Vui lòng nhập ngày sinh");
                    error = 1;
                }
                if(editPhone.getText().toString().isEmpty()){
                    layoutEditPhone.setHelperText("Vui lòng nhập điện thoại");
                    error = 1;
                }
                if(error!=1){
                    editName.clearFocus();
                    editEmail.clearFocus();
                    editBirthDay.clearFocus();
                    editPhone.clearFocus();
                    //Create model for push to server
                    UserModel model = new UserModel();
                    model.setUserID(1);
                    model.setGroupID(0);
                    model.setPositionID(1);
                    model.setClassID(0);
                    model.setFullname(editName.getText().toString());
                    model.setEmail(editEmail.getText().toString());
                    model.setPhone(editPhone.getText().toString());
                    model.setGender(1);
                    model.setBirthDayString(editBirthDay.getText().toString());
                    model.setJoinDate(editBirthDay.getText().toString());
                    model.setStudentCode("admin");
                    model.setCityID(provinceIdSelected);
                    model.setDistrictID(districtIdSelected);
                    model.setWardID(wardIdSelected);
                    model.setClassID(0);
                    model.setAddress(editAddress.getText().toString());
                    model.setFacultyName("Công Nghệ Thông Tin");
                    model.setFacultyID(0);
                    model.setClassName("18DTHD1");
                    model.setTemplateId(1);
                    //Call api for update info
                    Call<String> updateCall = api.changeInfo(model);
                    updateCall.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String result = response.body();
                            if(result == null){Toast.makeText(getApplicationContext(),"Có vấn đề trong việc nhận kết quả từ API. Lỗi "+response.code(),Toast.LENGTH_SHORT).show(); }
                            else if(result.equals("true")){
                                SweetAlertDialog successDialog = new SweetAlertDialog(InfoActivity.this,SweetAlertDialog.SUCCESS_TYPE);
                                successDialog.setTitle("Thành công");
                                successDialog.setContentText("Cập nhật thông tin thành công");
                                successDialog.setCanceledOnTouchOutside(false);
                                successDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        finish();
                                    }
                                });
                                successDialog.show();
                            }
                            else{
                                SweetAlertDialog errorDialog = new SweetAlertDialog(InfoActivity.this,SweetAlertDialog.SUCCESS_TYPE);
                                errorDialog.setTitle("Thất bại");
                                errorDialog.setContentText("Cập nhật thông tin không thành công");
                                errorDialog.setCanceledOnTouchOutside(false);
                                errorDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
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
                            Toast.makeText(getApplicationContext(),"Không thể truy cập API",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        //TODO enqueue
    }

}