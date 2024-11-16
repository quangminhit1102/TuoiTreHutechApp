package com.itgenz.studentmanage.fragments;

import static com.itgenz.studentmanage.interfaces.ServiceAPI.BASE_Service;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itgenz.studentmanage.R;
import com.itgenz.studentmanage.activities.AddFacultyActivity;
import com.itgenz.studentmanage.adapters.FacultyAdapter;
import com.itgenz.studentmanage.adapters.UnionAdapter;
import com.itgenz.studentmanage.interfaces.ServiceAPI;
import com.itgenz.studentmanage.models.FacultyModel;
import com.itgenz.studentmanage.models.ReqModel;
import com.itgenz.studentmanage.models.ResFacultyModel;
import com.itgenz.studentmanage.models.ResUnionModel;
import com.itgenz.studentmanage.models.UnionModel;
import com.itgenz.studentmanage.network.RetrofitClient;
import com.itgenz.studentmanage.utils.MyItemTouchHelperCallback;
import com.itgenz.studentmanage.utils.NetworkChangeReceiver;
import com.itgenz.studentmanage.utils.OnStartDragListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FacultyFragment extends Fragment {

    private int countFaculty = 0;
    @BindView(R.id.rclFal)
    RecyclerView rcl;
    ItemTouchHelper itemTouchHelper;
    FloatingActionButton flBtn;

    //Check if network connected
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    // On Destroy
    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(networkChangeReceiver);
    }

    // On Create
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_faculty, container, false);
        flBtn = view.findViewById(R.id.flBtn);
        //Register receiver for network alert
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getContext().registerReceiver(networkChangeReceiver,filter);
        init(view);
        generateItem();
        flBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(), AddFacultyActivity.class);
                intent.putExtra("countFaculty", countFaculty);
                startActivity(intent);
            }
        });
        return view;
    }

    private void generateItem() {
        ReqModel reqModel = new ReqModel(1,0,50);
        ArrayList<FacultyModel> alFal = new ArrayList<>();
        Retrofit retrofit =  RetrofitClient.getClient(BASE_Service);
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<ResFacultyModel> callModel =  serviceAPI.getListFaculty(reqModel);
        callModel.enqueue(new Callback<ResFacultyModel>() {
            @Override
            public void onResponse(Call<ResFacultyModel> call, Response<ResFacultyModel> response) {
                if(response.body()!=null && response.isSuccessful())
                {
                    int count = response.body().getData().size();
                    countFaculty = count;
                    ArrayList<UnionModel> filterUnion = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        alFal.add(response.body().getData().get(i));
                    }
                    FacultyAdapter adapter = new FacultyAdapter(getContext().getApplicationContext(), alFal, new OnStartDragListener() {
                        @Override
                        public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                            itemTouchHelper.startDrag(viewHolder);
                        }
                    });
                    rcl.setAdapter(adapter);
                    ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(adapter);
                    itemTouchHelper = new ItemTouchHelper(callback);
                    itemTouchHelper.attachToRecyclerView(rcl);

                }
            }

            @Override
            public void onFailure(Call<ResFacultyModel> call, Throwable t) {
                Toast.makeText(getContext().getApplicationContext(), "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(View view)
    {
        ButterKnife.bind(this, view);
        rcl.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext().getApplicationContext(),2);
        rcl.setLayoutManager(layoutManager);
    }
}