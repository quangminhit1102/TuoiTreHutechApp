package com.itgenz.studentmanage.activities;

import static com.itgenz.studentmanage.interfaces.ServiceAPI.BASE_Service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.itgenz.studentmanage.R;
import com.itgenz.studentmanage.interfaces.ServiceAPI;
import com.itgenz.studentmanage.models.UnionModel;
import com.itgenz.studentmanage.network.RetrofitClient;
import com.itgenz.studentmanage.utils.MyNotification;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditUnionActivity extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener{

    private EditText edtUnionCode,edtName, edtStuCode, edtPhone, edtEmail, edtBirthday, edtGetUnionDay, edtJoinDay;
    private TextInputLayout lName, lBirthday, lPhone, lEmail, lGetDay, lJoinDay, lStuCode;
    private UnionModel unionModel;
    private DatePickerDialog dpBirthDay, dpGetUnionDay, dpJoinDay;
    private Button btnUpdate;
    private String[] birthDay, returnDay, joinDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_union);
        Intent intent = getIntent();
        int UnionId = intent.getIntExtra("UnionId",0);
        //================================================================================
        edtUnionCode = findViewById(R.id.editUnionCode);
        edtName = findViewById(R.id.editName);
        edtStuCode = findViewById(R.id.editStuCode);
        edtBirthday = findViewById(R.id.editBirthDay);
        edtPhone = findViewById(R.id.editPhone);
        edtEmail = findViewById(R.id.editEmail);
        //edtGetUnionDay = findViewById(R.id.editGetUnionDay);
        edtJoinDay = findViewById(R.id.editJoinDay);
        btnUpdate = findViewById(R.id.btnUpdate);

        lName = findViewById(R.id.lName);
        lBirthday = findViewById(R.id.lBirthday);
        lPhone = findViewById(R.id.lPhone);
        lEmail = findViewById(R.id.lEmail);
        //lGetDay = findViewById(R.id.lGetDay);
        lJoinDay = findViewById(R.id.lJoinDay);
        lStuCode = findViewById(R.id.lStuCode);

        //==============================================================================
        // get Union Detailed
        Retrofit retrofit =  RetrofitClient.getClient(BASE_Service);
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<UnionModel> union =  serviceAPI.getUnionDetail(UnionId);
        union.enqueue(new Callback<UnionModel>() {
            @Override
            public void onResponse(Call<UnionModel> call, Response<UnionModel> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    unionModel = response.body();
                    edtUnionCode.setText(unionModel.getUnionID());
                    edtName.setText(unionModel.getFullname());
                    edtStuCode.setText(unionModel.getStudentCode());

                    edtPhone.setText(unionModel.getPhone());
                    edtEmail.setText(unionModel.getEmail());
                    edtBirthday.setText(unionModel.getBirthDay());
                    //edtGetUnionDay.setText(unionModel.getreturnDate());
                    edtJoinDay.setText(unionModel.getjoinDate());



                    Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH)+1;
                    int year = calendar.get(Calendar.YEAR);
                    String dayNow = day+"/"+month+"/"+year;
                    if(unionModel.getBirthDay().equalsIgnoreCase(""))
                    {
                        birthDay = dayNow.split("/");
                    }
                    else
                    {
                        birthDay = unionModel.getBirthDay().split("/");
                    }
                    if(unionModel.getreturnDate().equalsIgnoreCase(""))
                    {
                        returnDay = dayNow.split("/");
                    }
                    else
                    {
                        returnDay = unionModel.getreturnDate().split("/");
                    }
                    if(unionModel.getjoinDate().equalsIgnoreCase(""))
                    {
                        joinDay = dayNow.split("/");
                    }
                    else
                    {
                        joinDay = unionModel.getjoinDate().split("/");
                    }

                    // BirthDay
                    edtBirthday.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dpBirthDay = DatePickerDialog.newInstance(EditUnionActivity.this,
                                    Integer.parseInt(birthDay[2]),
                                    Integer.parseInt(birthDay[1])-1,
                                    Integer.parseInt(birthDay[0]));
                            dpBirthDay.setTitle("Ngày Sinh");
                            dpBirthDay.setAccentColor(getResources().getColor(R.color.main_blue_color,null));
                            dpBirthDay.show(getSupportFragmentManager(), "DatePicker");
                            dpBirthDay.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                    String day = String.valueOf(dayOfMonth);
                                    if(dayOfMonth<10)
                                    {
                                        day = "0"+dayOfMonth;
                                    }

                                    String month = String.valueOf(monthOfYear+1);
                                    if(monthOfYear<9)
                                    {
                                        month = "0"+(monthOfYear+1);
                                    }
                                    edtBirthday.setText(day+"/"+month+"/"+year);
                                }
                            });
                        }
                    });

                    //Get union day
//                    edtGetUnionDay.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            dpGetUnionDay = DatePickerDialog.newInstance(EditUnionActivity.this,
//                                    Integer.parseInt(returnDay[2]),
//                                    Integer.parseInt(returnDay[1])-1,
//                                    Integer.parseInt(returnDay[0]));
//                            dpGetUnionDay.setTitle("Ngày Trả Sổ");
//                            dpGetUnionDay.setAccentColor(getResources().getColor(R.color.main_blue_color,null));
//                            dpGetUnionDay.show(getSupportFragmentManager(), "DatePicker");
//                            dpGetUnionDay.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
//                                @Override
//                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//                                    String day = String.valueOf(dayOfMonth);
//                                    if(dayOfMonth<10)
//                                    {
//                                        day = "0"+dayOfMonth;
//                                    }
//
//                                    String month = String.valueOf(monthOfYear+1);
//                                    if(monthOfYear<9)
//                                    {
//                                        month = "0"+(monthOfYear+1);
//                                    }
//                                    edtGetUnionDay.setText(day+"/"+month+"/"+year);
//                                }
//                            });
//                        }
//                    });

                    // JoinDay
                    edtJoinDay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dpJoinDay = DatePickerDialog.newInstance(EditUnionActivity.this,
                                    Integer.parseInt(joinDay[2]),
                                    Integer.parseInt(joinDay[1])-1,
                                    Integer.parseInt(joinDay[0]));
                            dpJoinDay.setTitle("Ngày vào đoàn");
                            dpJoinDay.setAccentColor(getResources().getColor(R.color.main_blue_color,null));
                            dpJoinDay.show(getSupportFragmentManager(), "DatePicker");
                            dpJoinDay.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                    String day = String.valueOf(dayOfMonth);
                                    if(dayOfMonth<10)
                                    {
                                        day = "0"+dayOfMonth;
                                    }

                                    String month = String.valueOf(monthOfYear+1);
                                    if(monthOfYear<9)
                                    {
                                        month = "0"+(monthOfYear+1);
                                    }
                                    edtJoinDay.setText(day+"/"+month+"/"+year);
                                }
                            });
                        }
                    });

                }
            }
            @Override
            public void onFailure(Call<UnionModel> call, Throwable t) {
                Toast.makeText(EditUnionActivity.this, "Đã có lỗi xãy ra!", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validEmail = true;
                int count = 0;
                if (edtStuCode.getText().toString().equalsIgnoreCase(""))
                {
                    count++;
                    lStuCode.setHelperText("Vui lòng nhập trường này!");
                    edtStuCode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            lStuCode.setHelperText(null);
                        }
                    });
                }
                if (edtName.getText().toString().equalsIgnoreCase(""))
                {
                    count++;
                    lName.setHelperText("Vui lòng nhập trường này!");
                    edtName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            lName.setHelperText(null);
                        }
                    });
                }
                if (edtBirthday.getText().toString().equalsIgnoreCase(""))
                {
                    count++;
                    lBirthday.setHelperText("Vui lòng chọn ngày!");
                    edtBirthday.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            lBirthday.setHelperText(null);
                            dpBirthDay = DatePickerDialog.newInstance(EditUnionActivity.this,
                                    Integer.parseInt(birthDay[2]),
                                    Integer.parseInt(birthDay[1])-1,
                                    Integer.parseInt(birthDay[0]));
                            dpBirthDay.setTitle("Ngày Sinh");
                            dpBirthDay.setAccentColor(getResources().getColor(R.color.main_blue_color,null));
                            dpBirthDay.show(getSupportFragmentManager(), "DatePicker");
                            dpBirthDay.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                    String day = String.valueOf(dayOfMonth);
                                    if(dayOfMonth<10)
                                    {
                                        day = "0"+dayOfMonth;
                                    }

                                    String month = String.valueOf(monthOfYear+1);
                                    if(monthOfYear<9)
                                    {
                                        month = "0"+(monthOfYear+1);
                                    }
                                    edtBirthday.setText(day+"/"+month+"/"+year);
                                }
                            });
                        }
                    });
                }
                if(edtPhone.getText().toString().equalsIgnoreCase(""))
                {
                    count++;
                    lPhone.setHelperText("Vui lòng nhập trường này!");
                    edtPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            lPhone.setHelperText(null);
                        }
                    });
                }
                if(edtEmail.getText().toString().equalsIgnoreCase(""))
                {
                    count++;
                    lEmail.setHelperText("Vui lòng nhập trường này!");
                    edtEmail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           lEmail.setHelperText(null);
                        }
                    });
                }
                else
                {
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if (!edtEmail.getText().toString().trim().matches(emailPattern))
                    {
                        lEmail.setHelperText("Email không hợp lệ!");
                        validEmail = false;
                    }
                    edtEmail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            lEmail.setHelperText(null);
                        }
                    });
                }
//                if(edtGetUnionDay.getText().toString().equalsIgnoreCase(""))
//                {
//                    count++;
//                    lGetDay.setHelperText("Vui lòng chọn ngày!");
//                    edtGetUnionDay.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            lGetDay.setHelperText(null);
//                        }
//                    });
//                }
                if(edtJoinDay.getText().toString().equalsIgnoreCase(""))
                {
                    count++;
                    lJoinDay.setHelperText("Vui lòng chọn ngày!");
                    edtJoinDay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            lJoinDay.setHelperText(null);
                            dpJoinDay = DatePickerDialog.newInstance(EditUnionActivity.this,
                                    Integer.parseInt(joinDay[2]),
                                    Integer.parseInt(joinDay[1])-1,
                                    Integer.parseInt(joinDay[0]));
                            dpJoinDay.setTitle("Ngày vào đoàn");
                            dpJoinDay.setAccentColor(getResources().getColor(R.color.main_blue_color,null));
                            dpJoinDay.show(getSupportFragmentManager(), "DatePicker");
                            dpJoinDay.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                    String day = String.valueOf(dayOfMonth);
                                    if(dayOfMonth<10)
                                    {
                                        day = "0"+dayOfMonth;
                                    }

                                    String month = String.valueOf(monthOfYear+1);
                                    if(monthOfYear<9)
                                    {
                                        month = "0"+(monthOfYear+1);
                                    }
                                    edtJoinDay.setText(day+"/"+month+"/"+year);
                                }
                            });
                        }
                    });
                }
                if(count == 0 && validEmail == true)
                {
                    unionModel.setFullname(edtName.getText().toString());
                    unionModel.setStudentCode(edtStuCode.getText().toString());
                    unionModel.setBirthDayString(edtBirthday.getText().toString());
                    unionModel.setPhone(edtPhone.getText().toString());
                    unionModel.setEmail(edtEmail.getText().toString());
                    unionModel.setjoinDate(edtJoinDay.getText().toString());


                    Retrofit retrofit =  RetrofitClient.getClient(BASE_Service);
                    ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
                    Call<String> res =  serviceAPI.updateUnion(unionModel);
                    res.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.body().equals("1"))
                            {
                                lName.setHelperText(null);
                                lStuCode.setHelperText(null);
                                lEmail.setHelperText(null);
                                lPhone.setHelperText(null);
                                lJoinDay.setHelperText(null);



                                new SweetAlertDialog(EditUnionActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Thành Công!")
                                        .setContentText("Cập nhật sổ đoàn thành công!")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                // Notification
                                                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                                                if(sharedPreferences.getString("noti","").equals("true"))
                                                {
                                                    createNotification();
                                                }
                                                //=============================================================
                                                startActivity(new Intent(EditUnionActivity.this, MainActivity.class));
                                                finish();
                                                sweetAlertDialog.dismiss();
                                            }
                                        })
                                        .show();

                            }
                            if(response.body().equals("dub"))
                            {
                                new SweetAlertDialog(EditUnionActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Thất Bại!")
                                        .setContentText("Mã số sinh viên đã tồn tại!")
                                        .show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(EditUnionActivity.this, "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }
    private void createNotification()
    {
        Notification notification = new NotificationCompat.Builder(this, MyNotification.CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo_hutech_noti))
                .setSmallIcon(R.drawable.check_circle_24)
                .setContentText(unionModel.getFullname()+" - "+unionModel.getStudentCode())
                .setSubText("Cập nhật sổ đoàn")
                .setContentTitle("Cập nhật sổ đoàn")
                .build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1, notification);
    }
}
