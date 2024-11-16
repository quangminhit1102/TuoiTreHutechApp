package com.itgenz.studentmanage.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itgenz.studentmanage.models.Province;

import java.util.List;

public class DistrictAdapter extends ArrayAdapter<Province.District> {

    private Context context;
    private List<Province.District> list;

    public DistrictAdapter(@NonNull Context context, int resource, @NonNull List<Province.District> objects) {
        super(context, resource, objects);
        this.context = context;
        this.list = objects;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Province.District getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getPosition(@Nullable Province.District item) {
        if(item == null){
            return -1;
        }
        for(Province.District d : list){
            if(item.getCode() == d.getCode()){
                return list.indexOf(d);
            }
        }
        return -1;
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getCode();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(list.get(position).getName());
        return label;
    }
}
