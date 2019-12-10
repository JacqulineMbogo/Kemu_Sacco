package com.example.kemu_sacco.Withdrawals;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.kemu_sacco.Loans.loans_home;
import com.example.kemu_sacco.R;
import com.example.kemu_sacco.Utility.AppUtilits;
import com.example.kemu_sacco.Utility.Constant;
import com.example.kemu_sacco.Utility.DataValidation;
import com.example.kemu_sacco.Utility.NetworkUtility;
import com.example.kemu_sacco.Utility.SharedPreferenceActivity;
import com.example.kemu_sacco.WebServices.ServiceWrapper;
import com.example.kemu_sacco.beanResponse.ContributionRes;
import com.example.kemu_sacco.beanResponse.GetWithdrawalsRes;
import com.example.kemu_sacco.beanResponse.WithdrawRes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class withdraw_home extends AppCompatActivity {


    Context context;
    RecyclerView withdraw_recycler;
    TextView total_text;
    SharedPreferenceActivity sharedPreferenceActivity;
    private  withdrawal_adapter withdrawal_adapter;
    FloatingActionButton new_withdraw;
    private ArrayList<withdrawals_model> withdrawalsModels = new ArrayList<>();
    String TAG = "withdrawals";
    Integer balance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);


        context = this;

        this.setTitle("Cash Withdrawals");



        sharedPreferenceActivity = new SharedPreferenceActivity(context);
        withdraw_recycler = findViewById(R.id.withdraw_recycler);
        new_withdraw = findViewById(R.id.new_withdraw);
        total_text = findViewById(R.id.total_text);


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



                final EditText amount = view.findViewById(R.id.withdrawamount);
                 final Button cancel = view.findViewById(R.id.cancel);
                final Button okay = view.findViewById(R.id.ok);



                amount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {



                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if( s.toString().trim().equals("")){


                            Toast.makeText(context, "Enter a value", Toast.LENGTH_SHORT).show();
                        }else {

                            if (Integer.valueOf(s.toString()) > balance) {

                                AppUtilits.displayMessage(withdraw_home.this, "Maximum amount you can withdraw is Ksh" + " " + balance);
                                amount.setText("0");


                            }

                        }
                    }
                });

                okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!amount.getText().toString().isEmpty()){

                                    saveWithdrawal(amount.getText().toString());
                                    dialog.cancel();


                        }else{

                            amount.setError("please input amount");
                            amount.requestFocus();

                        }

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });


            }
        });


        LinearLayoutManager mLayoutManger = new LinearLayoutManager( context, RecyclerView.VERTICAL, false);
        withdraw_recycler.setLayoutManager(mLayoutManger);
        withdraw_recycler.setItemAnimator(new DefaultItemAnimator());

        withdrawal_adapter= new withdrawal_adapter( withdrawalsModels,withdraw_home.this);
        withdraw_recycler.setAdapter(withdrawal_adapter);

        getAllContributions();
        getWithdrawals();

    }




    public  void  saveWithdrawal(String amount){

        if (!NetworkUtility.isNetworkConnected(withdraw_home.this)){
            AppUtilits.displayMessage(withdraw_home.this,  getString(R.string.network_not_connected));

        }else {

            //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<WithdrawRes> call = service.WithdrawResCall("12345", amount, sharedPreferenceActivity.getItem(Constant.USER_DATA));
            call.enqueue(new Callback<WithdrawRes>() {
                @Override
                public void onResponse(Call<WithdrawRes> call, Response<WithdrawRes> response) {

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {


                            AppUtilits.displayMessage(withdraw_home.this, response.body().getMsg());
                            getAllContributions();
                            getWithdrawals();


                        }else {

                            AppUtilits.displayMessage(withdraw_home.this, "failed to make a withdraw");
                        }
                    }else {
                        AppUtilits.displayMessage(withdraw_home.this, "network error");
                    }


                }

                @Override
                public void onFailure(Call<WithdrawRes> call, Throwable t) {
                    // Log.e(TAG, "  fail- add to cart item "+ t.toString());

                    AppUtilits.displayMessage(withdraw_home.this, "failed to make a withdraw");
                }
            });
        }





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

                                sharedPreferenceActivity.putItem(Constant.TOTAL_WITHDRAWALS, response.body().getMsg() );

                                balance = Integer.valueOf(sharedPreferenceActivity.getItem(Constant.TOTAL_CONTRIBUTIONS)) - Integer.valueOf(sharedPreferenceActivity.getItem(Constant.TOTAL_WITHDRAWALS));
                                total_text.setText("Total withdrawn amount is Ksh " + sharedPreferenceActivity.getItem(Constant.TOTAL_WITHDRAWALS)+ " \n " +   "Total contributed  amount is Ksh " + sharedPreferenceActivity.getItem(Constant.TOTAL_CONTRIBUTIONS) + " \n " + "Account balance is " + balance);

                                sharedPreferenceActivity.putItem(Constant.TOTAL_WITHDRAWALS, response.body().getMsg());

                                withdrawalsModels.clear();


                                for (int i =0; i<response.body().getInformation().size(); i++){


                                    withdrawalsModels.add(  new withdrawals_model(response.body().getInformation().get(i).getWithdrawalId(),response.body().getInformation().get(i).getWithdrawalAmount(),response.body().getInformation().get(i).getWithdrawalDate(),response.body().getInformation().get(i).getWithdrawalStatus()) );


                                }
                                withdrawal_adapter.notifyDataSetChanged();
                            }

                        } else {

                            sharedPreferenceActivity.putItem(Constant.TOTAL_WITHDRAWALS, "0");
                            balance = Integer.valueOf(sharedPreferenceActivity.getItem(Constant.TOTAL_CONTRIBUTIONS)) - Integer.valueOf(sharedPreferenceActivity.getItem(Constant.TOTAL_WITHDRAWALS));
                            total_text.setText("Total withdrawn amount is Ksh " + sharedPreferenceActivity.getItem(Constant.TOTAL_WITHDRAWALS)+ " \n " +   "Total contributed  amount is Ksh " + sharedPreferenceActivity.getItem(Constant.TOTAL_CONTRIBUTIONS) + " \n " + "Account balance is " + balance);
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



    public  void getAllContributions(){


        if (!NetworkUtility.isNetworkConnected(withdraw_home.this)) {

            Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();


        } else {

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




                                sharedPreferenceActivity.putItem(Constant.TOTAL_CONTRIBUTIONS, response.body().getMsg());

                            }

                        } else {

                            sharedPreferenceActivity.putItem(Constant.TOTAL_CONTRIBUTIONS, "0");
                            AppUtilits.displayMessage(withdraw_home.this, response.body().getMsg());
                        }
                    } else {

                        Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<ContributionRes> call, Throwable t) {



                    Toast.makeText(getApplicationContext(), "please try again. Failed to get user contributions ", Toast.LENGTH_LONG).show();

                }
            });


        }


    }


}
