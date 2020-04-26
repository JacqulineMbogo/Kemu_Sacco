package com.example.kemu_sacco.LogIn;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kemu_sacco.Account.account_home;
import com.example.kemu_sacco.MainActivity;
import com.example.kemu_sacco.R;
import com.example.kemu_sacco.Utility.AppUtilits;
import com.example.kemu_sacco.Utility.Constant;
import com.example.kemu_sacco.Utility.DataValidation;
import com.example.kemu_sacco.Utility.NetworkUtility;
import com.example.kemu_sacco.Utility.SharedPreferenceActivity;
import com.example.kemu_sacco.WebServices.ServiceWrapper;
import com.example.kemu_sacco.beanResponse.KinRes;
import com.example.kemu_sacco.beanResponse.RegstatusRes;
import com.example.kemu_sacco.beanResponse.UserSignInRes;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn extends AppCompatActivity {

    Context context;

    TextView  signup_btn,status,message;
    LinearLayout login_btn;
    EditText id_number, password;
    SharedPreferenceActivity sharedPreferenceActivity;

    private String TAG = "LoginActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        context = this;

        this.setTitle("LogIn");

        sharedPreferenceActivity= new SharedPreferenceActivity(context);
        login_btn = findViewById(R.id.login_btn);
        signup_btn = findViewById(R.id.signup_btn);

        id_number = findViewById(R.id.id_number);
        password = findViewById(R.id.password);
        status = findViewById(R.id.status);
        message = findViewById(R.id.message);


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this,SignUp.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( DataValidation.isNotValidLName(id_number.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Invalid id number",Toast.LENGTH_LONG).show();

                }else if (DataValidation.isNotValidPassword(password.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Invalid password",Toast.LENGTH_LONG).show();

                }else {

                    LogIn_method();

                }
            }
        });

        getRegStatus();


    }


    public  void  getRegStatus(){


        final AlertDialog progressbar = AppUtilits.createProgressBar(this);


        if (!NetworkUtility.isNetworkConnected(LogIn.this)){
            Toast.makeText(getApplicationContext(),"Network error",Toast.LENGTH_LONG).show();
            AppUtilits.destroyDialog(progressbar);

        }else {

            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<RegstatusRes> RegstatusCall = serviceWrapper.RegstatusCall("12345", sharedPreferenceActivity.getItem(Constant.USER_DATA));
            RegstatusCall.enqueue(new Callback<RegstatusRes>() {
                @Override
                public void onResponse(Call<RegstatusRes> call, Response<RegstatusRes> response) {

                    Log.d(TAG, "reponse : "+ response.toString());
                    if (response.body()!= null && response.isSuccessful()){
                        if (response.body().getStatus() ==1){
                            Log.e(TAG, "  user data "+  response.body().getInformation());


                            status.setText(response.body().getInformation().getStatus());
                            message.setText(response.body().getInformation().getMessage());


                            AppUtilits.destroyDialog(progressbar);






                        }else  if (response.body().getStatus() ==0){
                          /*  AppUtilits.displayMessage(LogIn.this,  response.body().getMsg());*/
                            AppUtilits.destroyDialog(progressbar);
                        }
                    }else {
                        AppUtilits.displayMessage(LogIn.this,  response.body().getMsg());
                        Toast.makeText(getApplicationContext(),"Request failed",Toast.LENGTH_LONG).show();
                        AppUtilits.destroyDialog(progressbar);

                    }
                }

                @Override
                public void onFailure(Call<RegstatusRes> call, Throwable t) {
                    Log.e(TAG, " failure "+ t.toString());
                    Toast.makeText(getApplicationContext(),"Request failed",Toast.LENGTH_LONG).show();
                    AppUtilits.destroyDialog(progressbar);

                }
            });




        }

    }
    public  void  LogIn_method(){

        final AlertDialog progressbar = AppUtilits.createProgressBar(this);


        if (!NetworkUtility.isNetworkConnected(LogIn.this)){
            Toast.makeText(getApplicationContext(),"Network error",Toast.LENGTH_LONG).show();
            AppUtilits.destroyDialog(progressbar);

        }else {

            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<UserSignInRes> userSigninCall = serviceWrapper.UserSigninCall(id_number.getText().toString(), password.getText().toString());
            userSigninCall.enqueue(new Callback<UserSignInRes>() {
                @Override
                public void onResponse(Call<UserSignInRes> call, Response<UserSignInRes> response) {

                    Log.d(TAG, "reponse : "+ response.toString());
                    if (response.body()!= null && response.isSuccessful()){
                        if (response.body().getStatus() ==1){
                            Log.e(TAG, "  user data "+  response.body().getInformation());
                            sharedPreferenceActivity.putItem(Constant.USER_DATA, response.body().getInformation().getUserId());
                            sharedPreferenceActivity.putItem(Constant.ID_NUMBER, response.body().getInformation().getIdNumber());
                            sharedPreferenceActivity.putItem(Constant.FIRST_NAME, response.body().getInformation().getFirstName());
                            sharedPreferenceActivity.putItem(Constant.LAST_NAME, response.body().getInformation().getLastName());
                            sharedPreferenceActivity.putItem(Constant.USER_name, response.body().getInformation().getUsername());
                            sharedPreferenceActivity.putItem(Constant.USER_email, response.body().getInformation().getEmail());
                            sharedPreferenceActivity.putItem(Constant.USER_phone, response.body().getInformation().getPhoneNumber());


                            AppUtilits.destroyDialog(progressbar);
                            getUserKin();
                            // start home activity
                         /*   Intent intent = new Intent(LogIn.this, MainActivity.class);
                            startActivity(intent);
                            finish();
*/




                        }else  if (response.body().getStatus() ==0){
                            AppUtilits.displayMessage(LogIn.this,  response.body().getMsg());
                            AppUtilits.destroyDialog(progressbar);
                        }
                    }else {
                        AppUtilits.displayMessage(LogIn.this,  response.body().getMsg());
                        Toast.makeText(getApplicationContext(),"Request failed",Toast.LENGTH_LONG).show();
                        AppUtilits.destroyDialog(progressbar);

                    }
                }

                @Override
                public void onFailure(Call<UserSignInRes> call, Throwable t) {
                    Log.e(TAG, " failure "+ t.toString());
                    Toast.makeText(getApplicationContext(),"Request failed",Toast.LENGTH_LONG).show();
                    AppUtilits.destroyDialog(progressbar);

                }
            });




        }





    }

    public  void  getUserKin(){

        if (!NetworkUtility.isNetworkConnected(LogIn.this)){
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
                            if(response.body().getInformation().getDetails().size()  > 0){

                                Intent intent = new Intent(LogIn.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }else{


                                Toast.makeText(getApplicationContext(),"Please Add next of  kin details",Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(LogIn.this, account_home.class);
                                startActivity(intent);
                                finish();
                            }



                        }else {
                            AppUtilits.displayMessage(LogIn.this, response.body().getMsg() );
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
