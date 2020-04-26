package com.example.kemu_sacco;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kemu_sacco.Account.account_home;
import com.example.kemu_sacco.Contributions.contributions_home;
import com.example.kemu_sacco.Deposits.deposit_model;
import com.example.kemu_sacco.Deposits.deposits_home;
import com.example.kemu_sacco.Feedback.FeedbackHistory;
import com.example.kemu_sacco.Loans.loans_home;
import com.example.kemu_sacco.Utility.AppUtilits;
import com.example.kemu_sacco.Utility.Constant;
import com.example.kemu_sacco.Utility.NetworkUtility;
import com.example.kemu_sacco.Utility.SharedPreferenceActivity;
import com.example.kemu_sacco.WebServices.ServiceWrapper;
import com.example.kemu_sacco.Withdrawals.withdraw_home;
import com.example.kemu_sacco.beanResponse.DepositRes;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button contributions, loans, account, feedback, withdraw,deposits;
    SharedPreferenceActivity sharedPreferenceActivity;
    Context context;
    String TAG =  "Mainactivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        sharedPreferenceActivity = new SharedPreferenceActivity(context);
        contributions = findViewById(R.id.contributions);
        loans = findViewById(R.id.loans);
        account = findViewById(R.id.account);
        feedback = findViewById(R.id.feedback);
        withdraw = findViewById(R.id.withdraw);
        deposits = findViewById(R.id.deposit);


        contributions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent0 = new Intent( MainActivity.this, contributions_home.class);
                startActivity(intent0);
            }
        });
        deposits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent0 = new Intent( MainActivity.this, deposits_home.class);
                startActivity(intent0);
            }
        });

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent( MainActivity.this, withdraw_home.class);
                startActivity(intent);

            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent( MainActivity.this, account_home.class);
                startActivity(intent1);
            }
        });
        loans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent( MainActivity.this, loans_home.class);
                startActivity(intent1);
            }
        });

       feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent( MainActivity.this, FeedbackHistory.class);
                startActivity(intent1);
            }
        });

       getUserDeposits();

    }

    public void getUserDeposits(){


        if (!NetworkUtility.isNetworkConnected(MainActivity.this)) {
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

                                sharedPreferenceActivity.putItem(Constant.DEPOSIT,response.body().getMsg());
                                Log.d("depo", sharedPreferenceActivity.getItem(Constant.DEPOSIT));

                            }else{

                                sharedPreferenceActivity.putItem(Constant.DEPOSIT,response.body().getMsg());
                            }

                        } else {

                            sharedPreferenceActivity.putItem(Constant.DEPOSIT,response.body().getMsg());
                            //AppUtilits.displayMessage(MainActivity.this, response.body().getMsg());
                        }
                    } else {

                        sharedPreferenceActivity.putItem(Constant.DEPOSIT,"0");
                        //Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<DepositRes> call, Throwable t) {


                   // Toast.makeText(getApplicationContext(), "please try again. Failed to get user contributions ", Toast.LENGTH_LONG).show();

                }
            });


        }




    }
}
