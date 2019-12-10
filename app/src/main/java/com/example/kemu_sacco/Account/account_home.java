package com.example.kemu_sacco.Account;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kemu_sacco.LogIn.LogIn;
import com.example.kemu_sacco.LogIn.SignUp;
import com.example.kemu_sacco.R;
import com.example.kemu_sacco.Utility.AppUtilits;
import com.example.kemu_sacco.Utility.Constant;
import com.example.kemu_sacco.Utility.DataValidation;
import com.example.kemu_sacco.Utility.NetworkUtility;
import com.example.kemu_sacco.Utility.SharedPreferenceActivity;
import com.example.kemu_sacco.WebServices.ServiceWrapper;
import com.example.kemu_sacco.beanResponse.KinRes;
import com.example.kemu_sacco.beanResponse.NextofKinRes;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class account_home extends AppCompatActivity {


    TextView  fname, lname,id, username, email, phone, newkin;
    Context context;
    SharedPreferenceActivity sharedPreferenceActivity;
    private RecyclerView recycler_kin;
    private  String TAG =" account_home";

    private Kin_Adapter kinAdapter;
    private ArrayList<Kin_Model> kinModels = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        context= this;
        sharedPreferenceActivity = new SharedPreferenceActivity(context);
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        id=findViewById(R.id.id);
        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        recycler_kin = findViewById(R.id.recycler_kin);
        newkin = findViewById(R.id.newkin);

        LinearLayoutManager mLayoutManger3 = new LinearLayoutManager( this, RecyclerView.VERTICAL, false);
        recycler_kin.setLayoutManager(mLayoutManger3);
        recycler_kin.setItemAnimator(new DefaultItemAnimator());




        kinAdapter= new Kin_Adapter(kinModels,this);
        recycler_kin.setAdapter(kinAdapter);

        fname.setText(sharedPreferenceActivity.getItem(Constant.FIRST_NAME));
        lname.setText(sharedPreferenceActivity.getItem(Constant.LAST_NAME));
        id.setText(sharedPreferenceActivity.getItem(Constant.ID_NUMBER));
        username.setText(sharedPreferenceActivity.getItem(Constant.USER_name));
        email.setText(sharedPreferenceActivity.getItem(Constant.USER_email));
        phone.setText("+254 "+sharedPreferenceActivity.getItem(Constant.USER_phone));

        newkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final View  view;
                final Dialog dialog;

                view = LayoutInflater.from(context).inflate(R.layout.kin_popup, null, false);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                alertDialog.setView(view);

                alertDialog.setCancelable(true);


                dialog = alertDialog.create();

                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                dialog.show();


                final Window dialogWindow = dialog.getWindow();
                dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                final EditText nextname = view.findViewById(R.id.nextname);
                final EditText nextrelation = view.findViewById(R.id.nextrelation);
                final EditText nextnumber= view.findViewById(R.id.nextnumber);
                final EditText nextid = view.findViewById(R.id.nextid);
                final Button cancel = view.findViewById(R.id.cancel);
                final Button ok = view.findViewById(R.id.ok);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (DataValidation.isNotValidLName(nextname.getText().toString())) {
                            /// show error pupup
                            Toast.makeText(getApplicationContext(), "Invalid name", Toast.LENGTH_LONG).show();

                        } else   if (DataValidation.isNotValidFullName(nextrelation.getText().toString())) {
                            /// show error pupup
                            Toast.makeText(getApplicationContext(), "Invalid relation", Toast.LENGTH_LONG).show();

                        } else   if (DataValidation.isValidPhoneNumber(nextnumber.getText().toString())) {
                            /// show error pupup
                            Toast.makeText(getApplicationContext(), "Invalid phone number", Toast.LENGTH_LONG).show();

                        } else    if (DataValidation.isNotValidID(nextid.getText().toString())) {
                            /// show error pupup
                            Toast.makeText(getApplicationContext(), "Invalid id", Toast.LENGTH_LONG).show();

                        } else{

                            sendNextofKin(nextname.getText().toString(),nextrelation.getText().toString(),nextid.getText().toString(),nextnumber.getText().toString());


                        }
                    }
                });


            }

        });

        getUserKin();
    }



    public void sendNextofKin(String nextname, String nextrelation, String nextid, String nextphone){

        final AlertDialog progressbar = AppUtilits.createProgressBar(this);

        if (!NetworkUtility.isNetworkConnected(account_home.this)) {
            Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();
            AppUtilits.destroyDialog(progressbar);


        } else {ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<NextofKinRes> callNextofKinRes = serviceWrapper.NextofKinResCall("1234",
                    nextname,
                    nextid,
                    nextrelation,
                    nextphone,
                    "",
                    sharedPreferenceActivity.getItem(Constant.USER_DATA));
            callNextofKinRes.enqueue(new Callback<NextofKinRes>() {
                @Override
                public void onResponse(Call<NextofKinRes> call, Response<NextofKinRes> response) {
                    Log.d(TAG, "reponse : " + response.toString());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            AppUtilits.destroyDialog(progressbar);


                            AppUtilits.createToaster(account_home.this, "Welcome, " + sharedPreferenceActivity.getItem(Constant.FIRST_NAME) +  " " + sharedPreferenceActivity.getItem(Constant.LAST_NAME) + "\n Please continue to sign in upon admin approval", Toast.LENGTH_LONG);
                            Intent intent = new Intent(account_home.this, LogIn.class);

                            startActivity(intent);
                            finish();

                        } else {
                            AppUtilits.destroyDialog(progressbar);
                            AppUtilits.displayMessage(account_home.this, response.body().getMsg());
                        }
                    } else {
                        AppUtilits.destroyDialog(progressbar);
                        Toast.makeText(getApplicationContext(), "Request failed", Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<NextofKinRes> call, Throwable t) {
                    AppUtilits.destroyDialog(progressbar);
                    Log.e(TAG, " failure " + t.toString());
                    Toast.makeText(getApplicationContext(), "Request failed", Toast.LENGTH_LONG).show();


                }
            });
        }
    }

    public  void  getUserKin(){

        if (!NetworkUtility.isNetworkConnected(account_home.this)){
            Toast.makeText(getApplicationContext(),"Network error",Toast.LENGTH_LONG).show();

        }else {

            //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<KinRes> call = service.KinResCall( "1234" , sharedPreferenceActivity.getItem(Constant.USER_DATA));

            call.enqueue(new Callback<KinRes>() {
                @Override
                public void onResponse(Call<KinRes> call, Response<KinRes> response) {
                    Log.e(TAG, "response is "+ response.body() + "  ---- "+ new Gson().toJson(response.body()));
                    Log.e(TAG, "  ss sixe 1 ");
                    if (response.body() != null && response.isSuccessful()) {
                        Log.e(TAG, "  ss sixe 2 ");
                        if (response.body().getStatus() == 1) {
                            Log.e(TAG, "  ss sixe 3 ");


                              Log.e(TAG, " size is  "+ String.valueOf(response.body().getInformation().getDetails().size()));

                            kinModels.clear();

                            for (int i=0; i<response.body().getInformation().getDetails().size(); i++){


                                kinModels.add( new Kin_Model(response.body().getInformation().getDetails().get(i).getName(),response.body().getInformation().getDetails().get(i).getPhone(),response.body().getInformation().getDetails().get(i).getRelation(),response.body().getInformation().getDetails().get(i).getId()));

                            }

                            kinAdapter.notifyDataSetChanged();



                        }else {
                            AppUtilits.displayMessage(account_home.this, response.body().getMsg() );
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Please try again",Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<KinRes> call, Throwable t) {
                    Log.e(TAG, "  fail- add to cart item "+ t.toString());
                    Toast.makeText(getApplicationContext(),"please try again. Failed to get user kin",Toast.LENGTH_LONG).show();

                }
            });


        }


    }

}
