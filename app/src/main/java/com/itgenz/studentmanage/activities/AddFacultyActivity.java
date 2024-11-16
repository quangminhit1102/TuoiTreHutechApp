package com.itgenz.studentmanage.activities;

import static com.itgenz.studentmanage.interfaces.ServiceAPI.BASE_Service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.itgenz.studentmanage.R;
import com.itgenz.studentmanage.interfaces.ServiceAPI;
import com.itgenz.studentmanage.models.FacultyModel;
import com.itgenz.studentmanage.models.UnionModel;
import com.itgenz.studentmanage.network.RetrofitClient;
import com.itgenz.studentmanage.utils.MyNotification;

import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddFacultyActivity extends AppCompatActivity {

    private int countFaculty = 0;
    private Button btnAdd;
    private EditText edtName, edtPhone;
    private TextInputLayout lName, lPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        //Binding view
        btnAdd = findViewById(R.id.btnAdd);
        edtName = findViewById(R.id.editFalName);
        edtPhone = findViewById(R.id.edtFalPhone);
        lName = findViewById(R.id.lFalName);
        lPhone = findViewById(R.id.lFalPhone);

        //Get total
        Intent intent = getIntent();
        countFaculty = intent.getIntExtra("countFaculty",0);

        // Btn add event Listener
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Progress Dialog
                SweetAlertDialog pDialog = new SweetAlertDialog(AddFacultyActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();

                // Fields validation
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
                    FacultyModel model = new FacultyModel(edtName.getText().toString(), edtPhone.getText().toString());
                    Retrofit retrofit =  RetrofitClient.getClient(BASE_Service);
                    ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
                    Call<String> insert =  serviceAPI.insertFaculty(model);
                    insert.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.isSuccessful() && response.body().equals("true"))
                            {
                                pDialog.dismiss();// End Progresdialog
                                new SweetAlertDialog(AddFacultyActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Thành Công!")
                                        .setContentText("Thêm Khoa/Viên thành công!")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                // Notification
                                                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                                                if(sharedPreferences.getString("noti","").equals("true"))
                                                {
                                                    createNotification();
                                                }

                                                sweetAlertDialog.dismiss();
                                            }
                                        })
                                        .show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(AddFacultyActivity.this, "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    private void createNotification()
    {
        Notification notification = new NotificationCompat.Builder(this, MyNotification.CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo_hutech_noti))
                .setSmallIcon(R.drawable.check_circle_24)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(edtName.getText().toString()+" - "+edtPhone.getText().toString()+"\nTổng cộng: "+(countFaculty+1)+" Khoa viện"))
                .setSubText("Thêm mới Khoa/Viện")
                .setContentTitle("Thêm Khoa viện thành công")
                .build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(getNotificationId(), notification);
    }
    private int getNotificationId()
    {
        return (int) new Date().getTime();
    }
}