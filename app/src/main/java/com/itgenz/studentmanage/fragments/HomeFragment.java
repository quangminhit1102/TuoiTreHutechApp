package com.itgenz.studentmanage.fragments;

import static com.itgenz.studentmanage.interfaces.ServiceAPI.BASE_Service;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itgenz.studentmanage.R;
import com.itgenz.studentmanage.adapters.UnionAdapter;
import com.itgenz.studentmanage.interfaces.ServiceAPI;
import com.itgenz.studentmanage.models.ReqModel;
import com.itgenz.studentmanage.models.ResUnionModel;
import com.itgenz.studentmanage.models.UnionModel;
import com.itgenz.studentmanage.network.RetrofitClient;
import com.itgenz.studentmanage.utils.NetworkChangeReceiver;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Item view init
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;
    private RecyclerView rclView;
    private UnionAdapter unionAdapter;
    private SearchView searchView;
    private Button btnSent, btnWithdraw;
    private TextView txtResult;
    private ArrayList<UnionModel> alUnion = new ArrayList<>();

    //Check if network connected
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
    //Pagination
    int draw = 1, length = 10, start = 0;

    // Filter flag
    private int filterFlag = 0;

    // Model Response API
    ResUnionModel resUnionModel = new ResUnionModel();

    // On Destroy
    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(networkChangeReceiver);
    }

    // On Create View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rclView = view.findViewById(R.id.rclUnion);
        btnSent = view.findViewById(R.id.btnSent);
        btnWithdraw = view.findViewById(R.id.btnWithdraw);
        searchView = view.findViewById(R.id.searchView);
        txtResult = view.findViewById(R.id.txtResult);
        nestedScrollView = view.findViewById(R.id.nestScroll);
        progressBar = view.findViewById(R.id.progress_bar);
        //Register receiver for network alert
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getContext().registerReceiver(networkChangeReceiver,filter);
        super.onStart();
        //=============================================================
        // Init Search View
        initSearchView();

        // Filter By Button
        // BtnSent Click event
        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterSent(alUnion);
                filterFlag = 1;
            }
        });
        //Btn Withdrawn click event
        btnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterWithdraw(alUnion);
                filterFlag = 2;
            }
        });

        // add adapter and LinearLayoutManager To Recycler View - Some extra Setting
        rclView.setHasFixedSize(true);
        unionAdapter = new UnionAdapter(alUnion, getContext().getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rclView.setLayoutManager(linearLayoutManager);
        rclView.setAdapter(unionAdapter);
        //======================================
        getData(draw, start, length);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight() && filterFlag == 0)
                {
                    draw++;
                    start+=10;
                    progressBar.setVisibility(View.VISIBLE);
                    getData(draw, start, length);
                }
            }
        });
        return view;
    }

    // Init Search View
    private void initSearchView()
    {
        filterFlag = 0;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // Search on text change
            @Override
            public boolean onQueryTextChange(String text) {
                String temp = text.toLowerCase();
                txtResult.setVisibility(View.INVISIBLE);
                ArrayList<UnionModel> filterUnion = new ArrayList<>();
                for(UnionModel model: alUnion)
                {
                    if(model.getUnionID().toLowerCase().contains(temp)
                            || model.getFullname().toLowerCase().contains(temp)
                            || model.getStudentCode().toLowerCase().contains(temp)
                    )
                    {
                        filterUnion.add(model);
                    }
                }
                if(filterUnion.size() == 0)
                {
                    txtResult.setVisibility(View.VISIBLE);
                }
                UnionAdapter adapter = new UnionAdapter(filterUnion, getActivity().getApplicationContext());
                rclView.setAdapter(adapter);
                // BtnSent click Event => affect to the nearest list of union Books
                btnSent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        filterSent(filterUnion);
                    }
                });
                // BtnWithdraw click Event => affect to the nearest list of union Books
                btnWithdraw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        filterWithdraw(filterUnion);
                    }
                });
                return false;
            }
        });
    }

    // Handle out filter sent
    private void filterSent(ArrayList<UnionModel> list)
    {
        ArrayList<UnionModel> filterUnion = new ArrayList<>();
        for(UnionModel model:list)
        {
            if(model.getStatus()==1)
            {
                filterUnion.add(model);
            }
        }
        // No Union Alert
        if(filterUnion.size() == 0)
        {
            txtResult.setVisibility(View.VISIBLE);
        }
        else
        {
            txtResult.setVisibility(View.INVISIBLE);
        }
        UnionAdapter adapter = new UnionAdapter(filterUnion, getActivity().getApplicationContext());
        rclView.setAdapter(adapter);
    }

    // Handle out filter withdrawn
    private void filterWithdraw(ArrayList<UnionModel> list)
    {
        ArrayList<UnionModel> filterUnion = new ArrayList<>();
        for(UnionModel model: list)
        {
            if(model.getStatus()==2)
            {
                filterUnion.add(model);
            }
        }
        // No Union Alert
        if(filterUnion.size() == 0)
        {
            txtResult.setVisibility(View.VISIBLE);
        }
        else
        {
            txtResult.setVisibility(View.INVISIBLE);
        }
        UnionAdapter adapter = new UnionAdapter(filterUnion, getActivity().getApplicationContext());
        rclView.setAdapter(adapter);
    }

    // Load date - Pagination
    private void getData(int draw, int start, int length)
    {
        ReqModel model = new ReqModel(draw,start,length);
        Retrofit retrofit =  RetrofitClient.getClient(BASE_Service);
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<ResUnionModel> listUnion =  serviceAPI.getListUnion(model);
        listUnion.enqueue(new Callback<ResUnionModel>() {
            @Override
            public void onResponse(Call<ResUnionModel> call, Response<ResUnionModel> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    progressBar.setVisibility(View.GONE);
                    resUnionModel = response.body();
                    int count = resUnionModel.getData().size();
                    ArrayList<UnionModel> filterUnion = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        alUnion.add(resUnionModel.getData().get(i));
                    }
                }
                unionAdapter = new UnionAdapter(alUnion, getContext());
                rclView.setAdapter(unionAdapter);
            }
            //=============================================================
            @Override
            public void onFailure(Call<ResUnionModel> call, Throwable t) {
                Toast.makeText(getContext(), "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
