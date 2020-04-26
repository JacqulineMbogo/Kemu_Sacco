package com.example.kemu_sacco.Deposits;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
import com.example.kemu_sacco.beanResponse.DepositRes;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class deposits_home extends AppCompatActivity {


    Context context;
    SharedPreferenceActivity sharedPreferenceActivity;
    RecyclerView deposits_recycler;

    private deposits_adapter deposits_adapter;
    private ArrayList<deposit_model> depositModels= new ArrayList<>();
    TextView total_amount;

    String TAG = "deposits home";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposits_home);

        context = this;
        sharedPreferenceActivity = new SharedPreferenceActivity(context);
        deposits_recycler = findViewById(R.id.deposits_recycler);
        total_amount = findViewById(R.id.total_amount);

        LinearLayoutManager mLayoutManger = new LinearLayoutManager( context, RecyclerView.VERTICAL, false);
        deposits_recycler.setLayoutManager(mLayoutManger);
        deposits_recycler.setItemAnimator(new DefaultItemAnimator());

        deposits_adapter= new deposits_adapter(depositModels ,context );
        deposits_recycler.setAdapter(deposits_adapter);


        getUserDeposits();




    }

    public void getUserDeposits(){


        if (!NetworkUtility.isNetworkConnected(deposits_home.this)) {
            Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();


        } else {

            //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<DepositRes> call = service.DepositResCall("1234", sharedPreferenceActivity.getItem(Constant.USER_DATA));

            call.enqueue(new Callback<DepositRes>() {
                @Override
                public void onResponse(Call<DepositRes> call, Response<DepositRes> response) {
                    Log.e(TAG, "response is " + response.body() + "  ---- " + new Gson().toJson(response.body()));
                    Log.e(TAG, "  ss sixe 1 ");
                    if (response.body() != null && response.isSuccessful()) {

                        Log.e(TAG, "  ss sixe 2 ");
                        if (response.body().getStatus() == 1) {
                            Log.e(TAG, "  ss sixe 3 ");


                            if (response.body().getInformation().size()>0){



                                total_amount.setText("Total Deposit Amount" + " " + response.body().getMsg());

                                depositModels.clear();
                                for (int i =0; i<response.body().getInformation().size(); i++){


                                    depositModels.add(  new deposit_model(response.body().getInformation().get(i).getSharestId(),response.body().getInformation().get(i).getSharesAmount(),response.body().getInformation().get(i).getMonth(),response.body().getInformation().get(i).getStaff()));

                                }
                                deposits_adapter.notifyDataSetChanged();
                            }

                        } else {

                            AppUtilits.displayMessage(deposits_home.this, response.body().getMsg());
                        }
                    } else {

                        Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<DepositRes> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), "please try again. Failed to get user contributions ", Toast.LENGTH_LONG).show();

                }
            });


        }




    }
}
