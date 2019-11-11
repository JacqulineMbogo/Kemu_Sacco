package com.example.kemu_sacco.Contributions;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kemu_sacco.R;
import com.example.kemu_sacco.Utility.AppUtilits;
import com.example.kemu_sacco.Utility.Constant;
import com.example.kemu_sacco.Utility.NetworkUtility;
import com.example.kemu_sacco.Utility.SharedPreferenceActivity;
import com.example.kemu_sacco.WebServices.ServiceWrapper;
import com.example.kemu_sacco.beanResponse.ContributionRes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class contributions_home extends AppCompatActivity {


    Context context;

    RecyclerView collection_recycler;
    FloatingActionButton new_contribution;
    ProgressBar progressbar;
    String TAG = "flock_details";
    SharedPreferenceActivity sharedPreferenceActivity;
    private  contributions_adapter  contributions_adapter;
    private ArrayList<contributions_model> contributionsModels = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributions_home);

        context = this;

        sharedPreferenceActivity = new SharedPreferenceActivity(context);
        collection_recycler = findViewById(R.id.collections_recycler);
        progressbar = findViewById(R.id.progressBar);


        LinearLayoutManager mLayoutManger = new LinearLayoutManager( this, RecyclerView.VERTICAL, false);
        collection_recycler.setLayoutManager(mLayoutManger);
        collection_recycler.setItemAnimator(new DefaultItemAnimator());

        contributions_adapter= new contributions_adapter(this, contributionsModels);
        collection_recycler.setAdapter(contributions_adapter);


        getAllContributions();
    }


    public  void getAllContributions(){

        progressbar.setVisibility(View.VISIBLE);

        if (!NetworkUtility.isNetworkConnected(contributions_home.this)) {
            progressbar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();


        } else {
            progressbar.setVisibility(View.GONE);
            //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<ContributionRes> call = service.ContributionCall("1234", sharedPreferenceActivity.getItem(Constant.USER_DATA));

            call.enqueue(new Callback<ContributionRes>() {
                @Override
                public void onResponse(Call<ContributionRes> call, Response<ContributionRes> response) {
                    Log.e(TAG, "response is " + response.body() + "  ---- " + new Gson().toJson(response.body()));
                    Log.e(TAG, "  ss sixe 1 ");
                    if (response.body() != null && response.isSuccessful()) {

                        Log.e(TAG, "  ss sixe 2 ");
                        if (response.body().getStatus() == 1) {
                            Log.e(TAG, "  ss sixe 3 ");


                            if (response.body().getInformation().size()>0){

                                progressbar.setVisibility(View.GONE);

                               // contributionsModels.clear();
                                for (int i =0; i<response.body().getInformation().size(); i++){


                                    contributionsModels.add(  new contributions_model(response.body().getInformation().get(i).getContributionId(),response.body().getInformation().get(i).getAmount(),response.body().getInformation().get(i).getContributionDate(),response.body().getInformation().get(i).getContributionTypeId()));
                                    Log.d("model",response.body().getInformation().get(i).getContributionId() );

                                }
                                contributions_adapter.notifyDataSetChanged();
                            }

                        } else {
                            progressbar.setVisibility(View.GONE);
                            AppUtilits.displayMessage(contributions_home.this, response.body().getMsg());
                        }
                    } else {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<ContributionRes> call, Throwable t) {
                    progressbar.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "please try again. Failed to get user contributions ", Toast.LENGTH_LONG).show();

                }
            });


        }


    }
}
