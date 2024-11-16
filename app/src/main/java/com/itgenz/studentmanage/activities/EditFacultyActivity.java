package com.itgenz.studentmanage.activities;

import static com.itgenz.studentmanage.interfaces.ServiceAPI.BASE_Service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.itgenz.studentmanage.R;
import com.itgenz.studentmanage.interfaces.ServiceAPI;
import com.itgenz.studentmanage.models.FacultyModel;
import com.itgenz.studentmanage.network.RetrofitClient;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditFacultyActivity extends AppCompatActivity {

    private Button btnAdd;
    private EditText edtName, edtPhone;
    private TextInputLayout lName, lPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_faculty);

        // Get Faculty Info
        Intent intent = getIntent();
        int FacultyId = intent.getIntExtra("FacultyId", 0);
        String FacultyName = intent.getStringExtra("FacultyName");
        String Phone = intent.getStringExtra("phone");

        // Binding View
        btnAdd = findViewById(R.id.btnAdd);
        edtName = findViewById(R.id.editFalName);
        edtPhone = findViewById(R.id.edtFalPhone);
        lName = findViewById(R.id.lFalName);
        lPhone = findViewById(R.id.lFalPhone);
        edtName.setText(FacultyName);
        edtPhone.setText(Phone);

        // Btn Add EventListener
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Progress Dialog
                SweetAlertDialog pDialog = new SweetAlertDialog(EditFacultyActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();

                // Fields Validate
                if(edtName.getText().toString().equalsIgnoreCase(""))
                {
                    lName.setHelperText("Vui lòng nhập tên Khoa, Viện!");
                    edtName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            lName.setHelperText(null);
                        }
                    });
                }
                if(edtPhone.getText().toString().equalsIgnoreCase(""))
                {
                    lPhone.setHelperText("Vui lòng nhập Số điện thoại!");
                    edtPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            lPhone.setHelperText(null);
                        }
                    });
                }
                if(!edtName.getText().toString().equalsIgnoreCase("") && !edtPhone.getText().toString().equalsIgnoreCase(""))
                {
                    FacultyModel model = new FacultyModel(FacultyId ,edtName.getText().toString(), edtPhone.getText().toString());
                    Retrofit retrofit =  RetrofitClient.getClient(BASE_Service);
                    ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
                    Call<String> update =  serviceAPI.updateFaculty(model);
                    update.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.isSuccessful() && response.body().equals("true"))
                            {
                                pDialog.dismiss();// End Progresdialog
                                new SweetAlertDialog(EditFacultyActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Thành Công!")
                                        .setContentText("Cập nhật Khoa/Viên thành công!")
                                        .show();
                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(EditFacultyActivity.this, "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}