package com.itgenz.studentmanage.activities;

import static com.itgenz.studentmanage.interfaces.ServiceAPI.BASE_Service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itgenz.studentmanage.R;
import com.itgenz.studentmanage.adapters.UnionAdapter;
import com.itgenz.studentmanage.interfaces.ServiceAPI;
import com.itgenz.studentmanage.models.UnionModel;
import com.itgenz.studentmanage.network.RetrofitClient;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class test extends AppCompatActivity {

    private RecyclerView rclView;
    private UnionAdapter unionAdapter;
    private SearchView searchView;
    private Button btnSent, btnWithdraw;
    private TextView txtResult;
    private ArrayList<UnionModel> alUnion = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        // get Union Detailed
        Retrofit retrofit =  RetrofitClient.getClient(BASE_Service);
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<UnionModel> union =  serviceAPI.getUnionDetail(4738);
        union.enqueue(new Callback<UnionModel>() {
            @Override
            public void onResponse(Call<UnionModel> call, Response<UnionModel> response) {
                Toast.makeText(test.this, response.body().getBirthDay(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UnionModel> call, Throwable t) {

            }
        });

    }
}