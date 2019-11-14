package com.example.kemu_sacco.Loans;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kemu_sacco.Contributions.contributions_home;
import com.example.kemu_sacco.R;
import com.example.kemu_sacco.Utility.AppUtilits;
import com.example.kemu_sacco.Utility.Constant;
import com.example.kemu_sacco.Utility.NetworkUtility;
import com.example.kemu_sacco.Utility.SharedPreferenceActivity;
import com.example.kemu_sacco.WebServices.ServiceWrapper;
import com.example.kemu_sacco.beanResponse.LoanPaymentsRes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loan_payments_home extends AppCompatActivity {

    Context context;
    String application_id, loan_amount;
    Integer balance, overpaymnet;

    RecyclerView loanpayments_recycler;
    TextView total_payments;
    FloatingActionButton new_contribution;
    ProgressBar progressbar;
    String TAG = "contriutions";
    SharedPreferenceActivity sharedPreferenceActivity;
    private  loan_payments_adapter  loan_payments_adapter;
    private ArrayList<loan_payment_model> loanPaymentModels= new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_payments_home);

        context = this;

        this.setTitle("My Loan Payments");

        final Intent intent = getIntent();
        sharedPreferenceActivity = new SharedPreferenceActivity(context);
        application_id = intent.getExtras().getString("application_id");
        loan_amount= intent.getExtras().getString("loan_amount");

        loanpayments_recycler = findViewById(R.id.loanpayments_recycler);
        progressbar = findViewById(R.id.progressBar);
        new_contribution = findViewById(R.id.new_contribution);
        total_payments= findViewById(R.id.total_payment);


        LinearLayoutManager mLayoutManger = new LinearLayoutManager( context, RecyclerView.VERTICAL, false);
        loanpayments_recycler.setLayoutManager(mLayoutManger);
       loanpayments_recycler.setItemAnimator(new DefaultItemAnimator());

        loan_payments_adapter= new loan_payments_adapter(loanPaymentModels, context);
       loanpayments_recycler.setAdapter(loan_payments_adapter);


       getLoanPayments();

    }
    public  void  getLoanPayments(){

        progressbar.setVisibility(View.VISIBLE);

        if (!NetworkUtility.isNetworkConnected(loan_payments_home.this)) {
            progressbar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();


        } else {
            progressbar.setVisibility(View.GONE);
            //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<LoanPaymentsRes> call = service.LoanPaymentCall("1234", sharedPreferenceActivity.getItem(Constant.USER_DATA),application_id);

            call.enqueue(new Callback<LoanPaymentsRes>() {
                @Override
                public void onResponse(Call<LoanPaymentsRes> call, Response<LoanPaymentsRes> response) {
                    Log.e(TAG, "response is " + response.body() + "  ---- " + new Gson().toJson(response.body()));
                    Log.e(TAG, "  ss sixe 1 ");
                    if (response.body() != null && response.isSuccessful()) {

                        Log.e(TAG, "  ss sixe 2 ");
                        if (response.body().getStatus() == 1) {
                            Log.e(TAG, "  ss sixe 3 ");


                            if (response.body().getInformation().size()>0){

                                balance = Integer.valueOf(loan_amount) - Integer.valueOf(response.body().getMsg());
                                overpaymnet = Integer.valueOf(response.body().getMsg()) - Integer.valueOf(loan_amount);
                                progressbar.setVisibility(View.GONE);

                                        if(balance > 0) {

                                    total_payments.setText("Total payments received for this loan  is Ksh " + response.body().getMsg() + " " +"Remaining balance is " + balance);
                                                    }else if(balance<0 ){

                                            total_payments.setText("Total payments received for this loan  is Ksh " + response.body().getMsg() +  " " + "You have an overpayment of " + overpaymnet);
                                        }else {

                                            total_payments.setText("Total payments received for this loan  is Ksh " + response.body().getMsg() + " " +"Your loan is fully settled");

                                        }

                                loanPaymentModels.clear();
                                for (int i =0; i<response.body().getInformation().size(); i++){


                                    loanPaymentModels.add(  new loan_payment_model(response.body().getInformation().get(i).getPaymentId(),response.body().getInformation().get(i).getPaymentAmount(),response.body().getInformation().get(i).getCreateDate(),response.body().getInformation().get(i).getStatus()));

                                }
                                loan_payments_adapter.notifyDataSetChanged();
                            }

                        } else {
                            progressbar.setVisibility(View.GONE);
                            AppUtilits.displayMessage(loan_payments_home.this, response.body().getMsg());
                        }
                    } else {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<LoanPaymentsRes> call, Throwable t) {
                    progressbar.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "please try again. Failed to get user contributions ", Toast.LENGTH_LONG).show();

                }
            });


        }





    }
}
