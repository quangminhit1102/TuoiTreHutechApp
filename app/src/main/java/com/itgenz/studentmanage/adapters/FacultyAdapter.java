package com.itgenz.studentmanage.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.itgenz.studentmanage.R;
import com.itgenz.studentmanage.activities.EditFacultyActivity;
import com.itgenz.studentmanage.activities.EditUnionActivity;
import com.itgenz.studentmanage.models.FacultyModel;
import com.itgenz.studentmanage.utils.ItemTouchHelperAdapter;
import com.itgenz.studentmanage.utils.OnStartDragListener;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.ViewHolder>
implements ItemTouchHelperAdapter
{

    private Context context;
    ArrayList<FacultyModel> alFaculty;
    OnStartDragListener listener;

    public FacultyAdapter(Context context, ArrayList<FacultyModel> alFaculty, OnStartDragListener listener) {
        this.context = context;
        this.alFaculty = alFaculty;
        this.listener = listener;
    }

    public FacultyAdapter(Context context, ArrayList<FacultyModel> alFaculty) {
        this.context = context;
        this.alFaculty = alFaculty;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.faculty_item, parent, false));
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(alFaculty.get(position).getFacultyName());
        String phone;
        if(alFaculty.get(position).getPhone()==null)
        {
            phone = "Chưa cập nhật";
        }
        else
        {
           phone = alFaculty.get(position).getPhone();
        }
        holder.txtPhone.setText(phone);
        holder.item.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final  int action = motionEvent.getAction();
                if(action == MotionEvent.ACTION_DOWN)
                    listener.onStartDrag(holder);
                return false;
            }
        });
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext().getApplicationContext(), EditFacultyActivity.class);
                intent.putExtra("FacultyId", alFaculty.get(holder.getAdapterPosition()).getFacultyID());
                intent.putExtra("FacultyName", alFaculty.get(holder.getAdapterPosition()).getFacultyName());
                intent.putExtra("phone", alFaculty.get(holder.getAdapterPosition()).getPhone());
                intent.putExtra("totalClass", alFaculty.get(holder.getAdapterPosition()).getTotalClass());
                intent.putExtra("status", alFaculty.get(holder.getAdapterPosition()).getStatus());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().getApplicationContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return alFaculty.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.txtFalName)
        TextView txtName;
        @BindView(R.id.txtFalPhone)
        TextView txtPhone;
        @BindView(R.id.cardView)
        CardView item;

        Unbinder unbinder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }

    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(alFaculty, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        alFaculty.remove(position);
        notifyItemRemoved(position);
    }

}
