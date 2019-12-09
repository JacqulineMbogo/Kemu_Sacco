package com.example.kemu_sacco.Withdrawals;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.example.kemu_sacco.beanResponse.GetWithdrawalsRes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class withdraw_home extends AppCompatActivity {


    Context context;
    RecyclerView withdraw_recycler;
    SharedPreferenceActivity sharedPreferenceActivity;
    private  withdrawal_adapter withdrawal_adapter;
    FloatingActionButton new_withdraw;
    private ArrayList<withdrawals_model> withdrawalsModels = new ArrayList<>();
    String TAG = "withdrawals";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);


        context = this;

        this.setTitle("Cash Withdrawals");

        sharedPreferenceActivity = new SharedPreferenceActivity(context);
        withdraw_recycler = findViewById(R.id.withdraw_recycler);
        new_withdraw = findViewById(R.id.new_withdraw);

        new_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View  view;
                final Dialog dialog;

                view = LayoutInflater.from(context).inflate(R.layout.withdraw_popup, null, false);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                alertDialog.setView(view);

                alertDialog.setCancelable(true);


                dialog = alertDialog.create();

                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                dialog.show();


                final Window dialogWindow = dialog.getWindow();
                dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

            }
        });

        LinearLayoutManager mLayoutManger = new LinearLayoutManager( context, RecyclerView.VERTICAL, false);
        withdraw_recycler.setLayoutManager(mLayoutManger);
        withdraw_recycler.setItemAnimator(new DefaultItemAnimator());

        withdrawal_adapter= new withdrawal_adapter( withdrawalsModels,withdraw_home.this);
        withdraw_recycler.setAdapter(withdrawal_adapter);

        getWithdrawals();

    }

    public  void  getWithdrawals(){




        if (!NetworkUtility.isNetworkConnected(context)) {

            Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();


        } else {

            //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<GetWithdrawalsRes> call = service.GetWithdrawalsResCall("1234", sharedPreferenceActivity.getItem(Constant.USER_DATA));


            call.enqueue(new Callback<GetWithdrawalsRes>() {
                @Override
                public void onResponse(Call<GetWithdrawalsRes> call, Response<GetWithdrawalsRes> response) {
                    Log.e(TAG, "response is " + response.body() + "  ---- " + new Gson().toJson(response.body()));
                    Log.e(TAG, "  ss sixe 1 ");
                    if (response.body() != null && response.isSuccessful()) {

                        Log.e(TAG, "  ss sixe 2 ");
                        if (response.body().getStatus() == 1) {
                            Log.e(TAG, "  ss sixe 3 ");


                            if (response.body().getInformation().size()>0){

                                withdrawalsModels.clear();


                                for (int i =0; i<response.body().getInformation().size(); i++){


                                    withdrawalsModels.add(  new withdrawals_model(response.body().getInformation().get(i).getWithdrawalId(),response.body().getInformation().get(i).getWithdrawalAmount(),response.body().getInformation().get(i).getWithdrawalDate(),response.body().getInformation().get(i).getWithdrawalStatus()) );


                                }
                                withdrawal_adapter.notifyDataSetChanged();
                            }

                        } else {

                            AppUtilits.displayMessage(context, response.body().getMsg());
                        }
                    } else {

                        Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<GetWithdrawalsRes> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), "please try again. Failed to get cash withdrawals ", Toast.LENGTH_LONG).show();

                }
            });


        }




    }


}
