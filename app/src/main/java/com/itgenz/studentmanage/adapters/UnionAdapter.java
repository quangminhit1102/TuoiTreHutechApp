package com.itgenz.studentmanage.adapters;

import static com.itgenz.studentmanage.interfaces.ServiceAPI.BASE_Service;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itgenz.studentmanage.R;
import com.itgenz.studentmanage.activities.EditUnionActivity;
import com.itgenz.studentmanage.activities.MainActivity;
import com.itgenz.studentmanage.interfaces.ServiceAPI;
import com.itgenz.studentmanage.models.ResUnionModel;
import com.itgenz.studentmanage.models.UnionModel;
import com.itgenz.studentmanage.network.RetrofitClient;
import com.itgenz.studentmanage.services.GMailSender;
import com.zcw.togglebutton.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UnionAdapter extends RecyclerView.Adapter<UnionAdapter.ViewHolder>{
    // list union book
    ArrayList<UnionModel> alUnion;
    // UnionModel
    private UnionModel unionModel = new UnionModel();
    SweetAlertDialog pDialog;
    // Context
    private Context context;
    // Constructor
    private int unionId;
    public UnionAdapter(ArrayList<UnionModel> alUnion, Context context) {
        this.alUnion = alUnion;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.union_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtUnionCode.setText(String.valueOf(alUnion.get(position).getUnionID()));
        holder.txtName.setText(alUnion.get(position).getFullname());
        holder.txtStuCode.setText(String.valueOf(alUnion.get(position).getStudentCode()));
        // Datetime Trim
        String dateCreate = alUnion.get(position).getCreate_at().split(" ")[0];
        holder.txtDate.setText(dateCreate);
        // handle Status
        if(alUnion.get(position).getStatus()==1)
        {
            holder.tgBtn.setToggleOn(true);
            holder.linearLayout.setBackgroundColor(Color.parseColor("#4481eb"));
        }
        if(alUnion.get(position).getStatus()==2)
        {
            holder.tgBtn.setToggleOn(false);
            holder.linearLayout.setBackgroundColor(Color.parseColor("#7E7E7E"));
        }
        holder.popupIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater().inflate(R.menu.recycler_popup, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.edit_union)
                        {
                            Intent intent = new Intent(view.getContext(), EditUnionActivity.class);
                            intent.putExtra("UnionId", alUnion.get(holder.getAdapterPosition()).getId());
                            view.getContext().startActivity(intent);
                        }
                        return false;
                    }
                });
            }
        });
        holder.tgBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                // Change Status
                Retrofit retrofit_status =  RetrofitClient.getClient(BASE_Service);
                ServiceAPI serviceAPI_status = retrofit_status.create(ServiceAPI.class);
                Call<String> res = serviceAPI_status.changeStatus(alUnion.get(holder.getAdapterPosition()).getId());
                res.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        // Set Color
                        if(on == false)
                        {
                            holder.linearLayout.setBackgroundColor(Color.parseColor("#7E7E7E"));
                        }
                        if(on == true)
                        {
                            holder.linearLayout.setBackgroundColor(Color.parseColor("#4481eb"));
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(context.getApplicationContext(), "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                    }
                });

                // get Union Detailed
                Retrofit retrofit =  RetrofitClient.getClient(BASE_Service);
                ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
                Call<UnionModel> union =  serviceAPI.getUnionDetail(alUnion.get(holder.getAdapterPosition()).getId());
                union.enqueue(new Callback<UnionModel>() {
                    @Override
                    public void onResponse(Call<UnionModel> call, Response<UnionModel> response) {
                        if(response.isSuccessful() && response.body()!=null) {
                            unionModel = response.body();
                            // Send Email
                            new SweetAlertDialog(holder.itemView.getContext(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Bạn có muốn gửi Email xác nhận?")
                                    .setContentText("Sinh viên: "+unionModel.getFullname()+"-"+unionModel.getStudentCode())
                                    .setConfirmText("Có")
                                    .setCancelText("Không")
                                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            // Set Color
                                            if(on == false)
                                            {
                                                holder.linearLayout.setBackgroundColor(Color.parseColor("#7E7E7E"));
                                            }
                                            if(on == true)
                                            {
                                                holder.linearLayout.setBackgroundColor(Color.parseColor("#4481eb"));
                                            }
                                            sweetAlertDialog.dismiss();
                                        }
                                    })
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            String receiver = unionModel.getEmail();
                                            if(receiver==null)
                                            {
                                                Toast.makeText(context.getApplicationContext(), "Email chưa cập nhật!", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                String title = "Trường Đại Học Công nghệ TP Hồ Chí Minh - ";
                                                if(alUnion.get(holder.getAdapterPosition()).getStatus()==1)
                                                {
                                                    title += "Xác nhận rút sổ đoàn";
                                                }
                                                if(alUnion.get(holder.getAdapterPosition()).getStatus()==2)
                                                {
                                                    title += "Xác nhận nộp sổ đoàn";
                                                }
                                                String[] temp = unionModel.getCreate_At().split(" ");
                                                String create_date = temp[0];
                                                String message = "<div style='width: 750px; padding: 10px ;margin: 0 auto; font-size: 1.2rem;'> <div style='border: 3px solid #222; padding: 1px; background-color: #fb0000cf;'> <div style='border: 3px solid #222; padding: 20px;background: #fdf6ec;'> <div style='text-align: center;'> <h1 style='font-size: 1.5rem; margin-top: 0; margin-bottom: 5px; text-transform: uppercase;'>Đoàn trường đại học công nghệ tp.hcm</h1> <span>-----o0o-----</span> <h3 style='font-size: 1.6rem; margin-top: 9px; text-transform: uppercase;'>biên nhận hồ sơ đoàn viên</h3> </div><div style='text-align: end;'> <p style='margin: 0;'>Sổ "+unionModel.getUnionID()+"/"+ Calendar.getInstance().get(Calendar.YEAR)+"</p></div><div style='display:flex;'> <p style='margin: 9px 0;'>Họ và tên:</p><p style='margin: 9px 0;'>&emsp;"+unionModel.getFullname()+"</p></div><div style='display:flex;'> <div style='display:flex; width:50%'> <p style='margin: 9px 0;'>Ngày sinh:</p><p style='margin: 9px 0;'>&emsp;"+unionModel.getBirthDay()+"</p></div><div style='display:flex; width:50%'> <p style='margin: 9px 0;'>MSSV:</p><p style='margin: 9px 0;'>&emsp;"+unionModel.getStudentCode()+"</p></div></div><div style='display:flex;'> <div style='display:flex; width:50%'> <p style='margin: 9px 0;'>Lớp:</p><p style='margin: 9px 0;'>&emsp;"+unionModel.getClassName()+"</p></div><div style='display:flex; width:50%'> <p style='margin: 9px 0;'>Khoa:</p><p style='margin: 9px 0;'>&emsp;"+unionModel.getFacultyName()+"</p></div></div><div style='margin-top: 20px;'> <p style='margin: 7px 0;'><b style='text-decoration:underline;'>Lưu ý:</b> Giữ gìn biên nhận cẩn thận, mang theo biên nhận khi rút sổ.</p><p style='margin: 7px 0;'>Vui lòng lấy sổ trước ngày:"+unionModel.getreturnDate()+"</p><p style='margin: 7px 0;'>Thắc mắc xin liên hệ <b>(028)-3512 0293</b> hoặc <b>doanthanhnien@hutech.edu.vn</b> </p></div><div style='text-align: end;'> <p>TP.Hồ Chí Minh, ngày ... tháng ... năm 20..</p><div style='width: 20%; margin-left: auto; margin-right: 80px; text-align: center;'> <p style='margin: 5px 0; font-weight: 600;'>Người nhận</p><p style='margin: 14px 0;'></p><p style='margin: 14px 0;'></p></div></div></div></div></div>";
                                                pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                                pDialog.setTitleText("Loading");
                                                pDialog.setCancelable(false);
                                                pDialog.show();
                                                sendEmail(context.getString(R.string.main_email), context.getString(R.string.pass_email),receiver,title,message);
                                                if(alUnion.get(holder.getAdapterPosition()).getStatus()==1)
                                                {
                                                    alUnion.get(holder.getAdapterPosition()).setStatus(2);
                                                }
                                                else
                                                {
                                                    alUnion.get(holder.getAdapterPosition()).setStatus(1);
                                                }
                                                sDialog.dismiss();
                                            }

                                        }
                                    })
                                    .show();
                        }
                    }
                    @Override
                    public void onFailure(Call<UnionModel> call, Throwable t) {
                        Toast.makeText(context.getApplicationContext(), "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return alUnion.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtUnionCode,txtName, txtStuCode, txtDate;
        public ToggleButton tgBtn;
        public ImageView popupIcon;
        public LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các item
            txtUnionCode = itemView.findViewById(R.id.txtUnionCode);
            txtName = itemView.findViewById(R.id.txtName);
            txtStuCode = itemView.findViewById(R.id.txtStuCode);
            txtDate = itemView.findViewById(R.id.txtDate);
            tgBtn = itemView.findViewById(R.id.toggleButton);
            popupIcon = itemView.findViewById(R.id.popup_menu);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }

    // Send Email Thread Service
    private void sendEmail(final String Sender,final String Password,final String Receiver,final String Title,final String Message)
    {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender(Sender,Password);
                    sender.sendMail(Title, "<b>"+Message+"</b>", Sender, Receiver);
                    pDialog.dismiss();
                    successAlert();

                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }

        }).start();
    }
    // Success sent email alert
    private void successAlert(){
        ((Activity)context).runOnUiThread(new Runnable() {
            public void run() {
                // Send email success alert
                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Thành công!")
                        .setContentText("Gửi Email Thành Công!")
                        .show();
            }
        });
    }
}

