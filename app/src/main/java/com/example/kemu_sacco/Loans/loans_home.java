package com.example.kemu_sacco.Loans;

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

import com.example.kemu_sacco.Contributions.contributions_adapter;
import com.example.kemu_sacco.R;
import com.example.kemu_sacco.Utility.AppUtilits;
import com.example.kemu_sacco.Utility.Constant;
import com.example.kemu_sacco.Utility.NetworkUtility;
import com.example.kemu_sacco.Utility.SharedPreferenceActivity;
import com.example.kemu_sacco.WebServices.ServiceWrapper;
import com.example.kemu_sacco.beanResponse.LoansApplicationRes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loans_home extends AppCompatActivity {

    Context context;
    RecyclerView loan_recycler;
    TextView total_loans;
    FloatingActionButton new_loan;
    ProgressBar progressbar;
    String TAG = "loans";
    SharedPreferenceActivity sharedPreferenceActivity;
    private  loans_adapter loans_adapter;
    private ArrayList<loans_model> loansModels = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loans_home);

        context= this;
        sharedPreferenceActivity = new SharedPreferenceActivity(context);

        this.setTitle("My loans Activity");

       loan_recycler = findViewById(R.id.loans_recycler);
        progressbar = findViewById(R.id.progressBar);
        new_loan = findViewById(R.id.new_loan);
        total_loans = findViewById(R.id.total_loans);

        LinearLayoutManager mLayoutManger = new LinearLayoutManager( context, RecyclerView.VERTICAL, false);
       loan_recycler.setLayoutManager(mLayoutManger);
        loan_recycler.setItemAnimator(new DefaultItemAnimator());

        loans_adapter= new loans_adapter(loansModels, context );
        loan_recycler.setAdapter(loans_adapter);

        new_loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getUserLoans();

                final Dialog dialog;

                view = LayoutInflater.from(context).inflate(R.layout.loan_popup, null, false);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                alertDialog.setView(view);

                alertDialog.setCancelable(true);


                dialog = alertDialog.create();

                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                dialog.show();


                final Window dialogWindow = dialog.getWindow();
                dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);



                final EditText amount = view.findViewById(R.id.contributionamount);
                final Button cancel = view.findViewById(R.id.cancel);
                final Button okay = view.findViewById(R.id.ok);
                final Spinner spinner  = view.findViewById(R.id.loantypespinner);




                amount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {



                    }
                });

                okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.cancel();
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

        getUserLoans();

    }

    public void getUserLoans(){

        progressbar.setVisibility(View.VISIBLE);

        if (!NetworkUtility.isNetworkConnected(loans_home.this)) {
            progressbar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();


        } else {
            progressbar.setVisibility(View.GONE);
            //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<LoansApplicationRes> call = service.LoansCall("1234", sharedPreferenceActivity.getItem(Constant.USER_DATA));

            call.enqueue(new Callback<LoansApplicationRes>() {
                @Override
                public void onResponse(Call<LoansApplicationRes> call, Response<LoansApplicationRes> response) {
                    Log.e(TAG, "response is " + response.body() + "  ---- " + new Gson().toJson(response.body()));
                    Log.e(TAG, "  ss sixe 1 ");
                    if (response.body() != null && response.isSuccessful()) {

                        Log.e(TAG, "  ss sixe 2 ");
                        if (response.body().getStatus() == 1) {
                            Log.e(TAG, "  ss sixe 3 ");


                            if (response.body().getInformation().size()>0){

                                progressbar.setVisibility(View.GONE);

                                total_loans.setText("Total loan amount is Ksh " + response.body().getMsg() );

                                sharedPreferenceActivity.putItem(Constant.TOTAL_CONTRIBUTIONS, String.valueOf(response.body().getMsg()));
                                loansModels.clear();
                                for (int i =0; i<response.body().getInformation().size(); i++){


                                    loansModels.add(  new loans_model(response.body().getInformation().get(i).getApplicationId(),response.body().getInformation().get(i).getAmount(),response.body().getInformation().get(i).getDate(),response.body().getInformation().get(i).getStatus(),response.body().getInformation().get(i).getLoanId() ));

                                }
                               loans_adapter.notifyDataSetChanged();
                            }

                        } else {
                            progressbar.setVisibility(View.GONE);
                            AppUtilits.displayMessage(loans_home.this, response.body().getMsg());
                        }
                    } else {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<LoansApplicationRes> call, Throwable t) {
                    progressbar.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "please try again. Failed to get user contributions ", Toast.LENGTH_LONG).show();

                }
            });


        }




    }
}
