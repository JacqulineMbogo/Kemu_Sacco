package com.example.kemu_sacco.Loans;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kemu_sacco.Contributions.contributions_home;
import com.example.kemu_sacco.R;
import com.example.kemu_sacco.Utility.AppUtilits;
import com.example.kemu_sacco.Utility.Constant;
import com.example.kemu_sacco.Utility.DataValidation;
import com.example.kemu_sacco.Utility.NetworkUtility;
import com.example.kemu_sacco.Utility.SharedPreferenceActivity;
import com.example.kemu_sacco.WebServices.ServiceWrapper;
import com.example.kemu_sacco.beanResponse.LoanPaymentsRes;
import com.example.kemu_sacco.beanResponse.MakeLoanPaymentRes;
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
    FloatingActionButton new_payment;
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
        new_payment = findViewById(R.id.new_payment);
        total_payments= findViewById(R.id.total_payment);


        new_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getLoanPayments();

                final Dialog dialog;

                view = LayoutInflater.from(context).inflate(R.layout.payment_popup, null, false);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                alertDialog.setView(view);

                alertDialog.setCancelable(true);


                dialog = alertDialog.create();

                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                dialog.show();


                final Window dialogWindow = dialog.getWindow();
                dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);



                final LinearLayout codelinear = view.findViewById(R.id.codelinear);
                final EditText amount = view.findViewById(R.id.contributionamount);
                final EditText code = view.findViewById(R.id.code);
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

                            codelinear.setVisibility(View.GONE);
                            Toast.makeText(context, "Enter a value", Toast.LENGTH_SHORT).show();
                        }else {
                            codelinear.setVisibility(View.VISIBLE);
                        }
                    }
                });



                okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!amount.getText().toString().isEmpty()){
                            if (!code.getText().toString().isEmpty()) {

                                if (!DataValidation.isNotValidcode(code.getText().toString())) {
                                    saveNewLoanPayment(amount.getText().toString(), code.getText().toString());
                                    dialog.cancel();

                                }else{

                                    code.setError("Invalid code length. Should be 10 characters.");
                                    code.requestFocus();

                                }
                            }else{
                                code.setError("please input transaction code ");
                                code.requestFocus();

                            }
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
        loanpayments_recycler.setLayoutManager(mLayoutManger);
       loanpayments_recycler.setItemAnimator(new DefaultItemAnimator());

        loan_payments_adapter= new loan_payments_adapter(loanPaymentModels, context);
       loanpayments_recycler.setAdapter(loan_payments_adapter);


       getLoanPayments();

    }

    public void saveNewLoanPayment(String amount, String code){


        if (!NetworkUtility.isNetworkConnected(loan_payments_home.this)){
            AppUtilits.displayMessage(loan_payments_home.this,  getString(R.string.network_not_connected));

        }else {

            //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<MakeLoanPaymentRes> call = service.MakeLoanPaymentCall("12345", application_id,amount,sharedPreferenceActivity.getItem(Constant.USER_DATA),code);
            call.enqueue(new Callback<MakeLoanPaymentRes>() {
                @Override
                public void onResponse(Call<MakeLoanPaymentRes> call, Response<MakeLoanPaymentRes> response) {

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {


                            AppUtilits.displayMessage(loan_payments_home.this, getString(R.string.successful_add_payments));
                            getLoanPayments();


                        }else {

                            AppUtilits.displayMessage(loan_payments_home.this, "failed to make new contribution");
                        }
                    }else {
                        AppUtilits.displayMessage(loan_payments_home.this, "network error");
                    }


                }

                @Override
                public void onFailure(Call<MakeLoanPaymentRes> call, Throwable t) {
                    // Log.e(TAG, "  fail- add to cart item "+ t.toString());

                    AppUtilits.displayMessage(loan_payments_home.this, "Your loan application has failed");
                }
            });
        }


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
