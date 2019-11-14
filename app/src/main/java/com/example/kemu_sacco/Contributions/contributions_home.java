package com.example.kemu_sacco.Contributions;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.example.kemu_sacco.beanResponse.ContributionRes;
import com.example.kemu_sacco.beanResponse.ContributionTypeRes;
import com.example.kemu_sacco.beanResponse.SaveContributionRes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.kemu_sacco.R.id.text;
import static com.example.kemu_sacco.R.layout.spinner;

public class contributions_home extends AppCompatActivity {


    Context context;

    RecyclerView collection_recycler;
    TextView total_amount;
    FloatingActionButton new_contribution;
    ProgressBar progressbar;
    String TAG = "contriutions";
    SharedPreferenceActivity sharedPreferenceActivity;
    private  contributions_adapter  contributions_adapter;
    private ArrayList<contributions_model> contributionsModels = new ArrayList<>();

    private  contributions_type_adapter  contributions_type_adapter;
    private ArrayList<contributions_type_model> contributionsTypeModels = new ArrayList<>();
    private String pin,selectedItemText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributions_home);

        context = this;

        this.setTitle("My Contributions");

        sharedPreferenceActivity = new SharedPreferenceActivity(context);
        collection_recycler = findViewById(R.id.collections_recycler);
        progressbar = findViewById(R.id.progressBar);
        new_contribution = findViewById(R.id.new_contribution);
        total_amount = findViewById(R.id.total_amount);

        LinearLayoutManager mLayoutManger = new LinearLayoutManager( context, RecyclerView.VERTICAL, false);
        collection_recycler.setLayoutManager(mLayoutManger);
        collection_recycler.setItemAnimator(new DefaultItemAnimator());

        contributions_adapter= new contributions_adapter(context, contributionsModels);
        collection_recycler.setAdapter(contributions_adapter);

        contributions_type_adapter= new contributions_type_adapter(contributionsTypeModels, context);

        new_contribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getContributionTypes();

                final Dialog dialog;

                view = LayoutInflater.from(context).inflate(R.layout.popup, null, false);
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
                final Spinner spinner  = view.findViewById(R.id.contributiontypespinner);

             //   getContributionTypes();

                String[] contributions=getResources().getStringArray(R.array.contribution_types);

                ArrayAdapter<String> adapter=new ArrayAdapter<String>(context, R.layout.spinner, text, contributions);

                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedItemText = (String) parent.getItemAtPosition(position);
                        // Notify the selected item text


                        if (selectedItemText.equals("Education Fund")) {

                            pin = "1";
                        } else if (selectedItemText.equals("Children Fund")) {

                            pin = "2";
                        } else if (selectedItemText.equals("Christmas Fund")) {

                            pin = "3";
                        } else if (selectedItemText.equals("Leave Fund")) {

                            pin = "4";
                        } else if (selectedItemText.equals("Recreation Fund")) {

                            pin = "5";
                        } else if (selectedItemText.equals("Retirement Fund")) {

                            pin = "6";


                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                amount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        codelinear.setVisibility(View.VISIBLE);

                    }
                });

                okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveNewContribution(pin, amount.getText().toString(), code.getText().toString());
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

                                total_amount.setText("Total contribution amount is Ksh " + response.body().getMsg() );

                                sharedPreferenceActivity.putItem(Constant.TOTAL_CONTRIBUTIONS, response.body().getMsg());
                               contributionsModels.clear();
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

    public  void saveNewContribution(String type, String amount, String code ){

        if (!NetworkUtility.isNetworkConnected(contributions_home.this)){
            AppUtilits.displayMessage(contributions_home.this,  getString(R.string.network_not_connected));

        }else {

            //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<SaveContributionRes> call = service.SaveContributionCall("12345", type,amount, sharedPreferenceActivity.getItem(Constant.USER_DATA), code);
            call.enqueue(new Callback<SaveContributionRes>() {
                @Override
                public void onResponse(Call<SaveContributionRes> call, Response<SaveContributionRes> response) {

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {


                          AppUtilits.displayMessage(contributions_home.this, getString(R.string.successful_add_savings));
                            getAllContributions();


                        }else {

                            AppUtilits.displayMessage(contributions_home.this, "failed to make new contribution");
                        }
                    }else {
                        AppUtilits.displayMessage(contributions_home.this, "network error");
                    }


                }

                @Override
                public void onFailure(Call<SaveContributionRes> call, Throwable t) {
                    // Log.e(TAG, "  fail- add to cart item "+ t.toString());

                    AppUtilits.displayMessage(contributions_home.this, "failed to make new contribution");
                }
            });
        }
    }

    public  void getContributionTypes(){

        progressbar.setVisibility(View.VISIBLE);

        if (!NetworkUtility.isNetworkConnected(contributions_home.this)) {
            progressbar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();


        } else {
            progressbar.setVisibility(View.GONE);
            //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<ContributionTypeRes> call = service.ContributionTypeCall("1234");

            call.enqueue(new Callback<ContributionTypeRes>() {
                @Override
                public void onResponse(Call<ContributionTypeRes> call, Response<ContributionTypeRes> response) {
                    Log.e(TAG, "responsez is " + response.body() + "  ---- " + new Gson().toJson(response.body()));
                    Log.e(TAG, "  ss sixe 1 ");
                    if (response.body() != null && response.isSuccessful()) {

                        Log.e(TAG, "  ss sixe 2 ");
                        if (response.body().getStatus() == 1) {
                            Log.e(TAG, "  ss sixe 3 ");


                            if (response.body().getInformation().size()>0){

                                progressbar.setVisibility(View.GONE);

                             contributionsTypeModels.clear();
                                for (int i =0; i<response.body().getInformation().size(); i++){


                                    contributionsTypeModels.add(  new contributions_type_model(response.body().getInformation().get(i).getContributionTypeId(),response.body().getInformation().get(i).getContributionType()));


                                }
                                contributions_type_adapter.notifyDataSetChanged();



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
                public void onFailure(Call<ContributionTypeRes> call, Throwable t) {
                    progressbar.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "please try again. Failed to get user contributions ", Toast.LENGTH_LONG).show();

                }
            });


        }


    }
}
