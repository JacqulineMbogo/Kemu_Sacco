package com.example.kemu_sacco.LogIn;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kemu_sacco.R;
import com.example.kemu_sacco.Utility.AppUtilits;
import com.example.kemu_sacco.Utility.Constant;
import com.example.kemu_sacco.Utility.DataValidation;
import com.example.kemu_sacco.Utility.NetworkUtility;
import com.example.kemu_sacco.Utility.SharedPreferenceActivity;
import com.example.kemu_sacco.WebServices.ServiceWrapper;
import com.example.kemu_sacco.beanResponse.NewUserRegistration;
import com.example.kemu_sacco.beanResponse.NextofKinRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    Context context;
    SharedPreferenceActivity sharedPreferenceActivity;

    EditText id_number, phone_no, email, password, retype_password, fname, lname, nextname, nextid,nextnumber, nextrelation , dob, nextid2,nextnumber2, nextrelation2, amount;
    TextView create_acc, signin;


    private String TAG = "SignUpActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        context = this;
        sharedPreferenceActivity = new SharedPreferenceActivity(context);
        this.setTitle("Sign Up");

        id_number =  findViewById(R.id.id_number);
        fname =  findViewById(R.id.f_name);
        lname = findViewById(R.id.l_name);
        phone_no =  findViewById(R.id.phone_number);
        email =  findViewById(R.id.email);
        password =  findViewById(R.id.password);
        retype_password =  findViewById(R.id.retype_password);
        signin =  findViewById(R.id.sign_in);
        create_acc = findViewById(R.id.create_account);
        nextname = findViewById(R.id.nextname);
        nextid= findViewById(R.id.nextid);
        nextrelation = findViewById(R.id.nextrelation);
        nextnumber = findViewById(R.id.nextnumber);
        amount= findViewById(R.id.amount);
        dob = findViewById(R.id.dob);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUp.this, LogIn.class);

                startActivity(intent);
                finish();
            }
        });

        create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (DataValidation.isNotValidID(id_number.getText().toString())) {
                    /// show error pupup
                    Toast.makeText(getApplicationContext(), "Invalid id", Toast.LENGTH_LONG).show();

                } else if (DataValidation.isNotValidLName(dob.getText().toString())) {
                    /// show error pupup
                    Toast.makeText(getApplicationContext(), "Invalid username", Toast.LENGTH_LONG).show();

                }else if (DataValidation.isNotValidLName(fname.getText().toString())) {
                    /// show error pupup
                    Toast.makeText(getApplicationContext(), "Invalid first name", Toast.LENGTH_LONG).show();
                } else if (DataValidation.isNotValidFullName(lname.getText().toString())) {
                    /// show error pupup
                    Toast.makeText(getApplicationContext(), "Invalid last name", Toast.LENGTH_LONG).show();
                } else if (DataValidation.isValidPhoneNumber(phone_no.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Invalid phone number.", Toast.LENGTH_LONG).show();

                } else if (DataValidation.isNotValidemail(email.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_LONG).show();

                } else if (DataValidation.isNotValidPassword(password.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Password should be at least 6 characters ", Toast.LENGTH_LONG).show();

                } else if (!password.getText().toString().equals(retype_password.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "passwords do not match", Toast.LENGTH_LONG).show();


                } else  if (DataValidation.isNotValidLName(nextname.getText().toString())) {
                    /// show error pupup
                    Toast.makeText(getApplicationContext(), "Invalid next of kin name", Toast.LENGTH_LONG).show();

                } else   if (DataValidation.isNotValidFullName(nextrelation.getText().toString())) {
                    /// show error pupup
                    Toast.makeText(getApplicationContext(), "Invalid next of kin relation", Toast.LENGTH_LONG).show();

                } else   if (DataValidation.isValidPhoneNumber(nextnumber.getText().toString())) {
                    /// show error pupup
                    Toast.makeText(getApplicationContext(), "Invalid next of kin phone number", Toast.LENGTH_LONG).show();

                } else    if (DataValidation.isNotValidID(nextid.getText().toString())) {
                    /// show error pupup
                    Toast.makeText(getApplicationContext(), "Invalid next of kin  id", Toast.LENGTH_LONG).show();

                } else if (amount.getText().toString().isEmpty()) {
                    /// show error pupup
                    Toast.makeText(getApplicationContext(), "Invalid share amount", Toast.LENGTH_LONG).show();

                } else {

                    sendNewRegistrationReq();
                }

            }
        });
    }


    public void sendNewRegistrationReq() {

        final AlertDialog progressbar = AppUtilits.createProgressBar(this);

        if (!NetworkUtility.isNetworkConnected(SignUp.this)) {
            Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();
            AppUtilits.destroyDialog(progressbar);


        } else { ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<NewUserRegistration> callNewREgistration = serviceWrapper.newUserRegistrationCall(id_number.getText().toString(), fname.getText().toString(), lname.getText().toString(),
                    email.getText().toString(), phone_no.getText().toString(),dob.getText().toString() , amount.getText().toString().trim(), password.getText().toString());
            callNewREgistration.enqueue(new Callback<NewUserRegistration>() {
                @Override
                public void onResponse(Call<NewUserRegistration> call, Response<NewUserRegistration> response) {
                    Log.d(TAG, "reponse : " + response.toString());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            AppUtilits.destroyDialog(progressbar);

                            // store userdata to share prerference
                            sharedPreferenceActivity.putItem(Constant.USER_DATA, response.body().getInformation().getUserId());
                            sharedPreferenceActivity.putItem(Constant.ID_NUMBER, response.body().getInformation().getIdNumber());
                            sharedPreferenceActivity.putItem(Constant.FIRST_NAME, response.body().getInformation().getFirstName());
                            sharedPreferenceActivity.putItem(Constant.LAST_NAME, response.body().getInformation().getLastName());
                            sharedPreferenceActivity.putItem(Constant.USER_name, response.body().getInformation().getUsername());
                            sharedPreferenceActivity.putItem(Constant.USER_email, response.body().getInformation().getEmail());
                            sharedPreferenceActivity.putItem(Constant.USER_phone, response.body().getInformation().getPhoneNumber());


                            sendNextofKin();
                       /*     // start home activity

                            AppUtilits.createToaster(SignUp.this, "Welcome, " + sharedPreferenceActivity.getItem(Constant.FIRST_NAME) +  " " + sharedPreferenceActivity.getItem(Constant.LAST_NAME) + "\n Please continue to sign in upon admin approval", Toast.LENGTH_LONG);
                            Intent intent = new Intent(SignUp.this, LogIn.class);

                            startActivity(intent);
                            finish();*/

                        } else {
                            AppUtilits.destroyDialog(progressbar);
                            AppUtilits.displayMessage(SignUp.this, response.body().getMsg());
                        }
                    } else {
                        AppUtilits.destroyDialog(progressbar);
                        Toast.makeText(getApplicationContext(), "Request failed", Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<NewUserRegistration> call, Throwable t) {
                    AppUtilits.destroyDialog(progressbar);
                    Log.e(TAG, " failure " + t.toString());
                    Toast.makeText(getApplicationContext(), "Request failed", Toast.LENGTH_LONG).show();


                }
            });
        }
    }


    public void sendNextofKin(){

        final AlertDialog progressbar = AppUtilits.createProgressBar(this);

        if (!NetworkUtility.isNetworkConnected(SignUp.this)) {
            Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();
            AppUtilits.destroyDialog(progressbar);


        } else {ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<NextofKinRes> callNextofKinRes = serviceWrapper.NextofKinResCall("1234",
                    nextname.getText().toString(),nextid.getText().toString(),
                    nextrelation.getText().toString(),nextnumber.getText().toString(),
                    amount.getText().toString(),
                    sharedPreferenceActivity.getItem(Constant.USER_DATA));
            callNextofKinRes.enqueue(new Callback<NextofKinRes>() {
                @Override
                public void onResponse(Call<NextofKinRes> call, Response<NextofKinRes> response) {
                    Log.d(TAG, "reponse : " + response.toString());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            AppUtilits.destroyDialog(progressbar);


                            AppUtilits.createToaster(SignUp.this, "Welcome, " + sharedPreferenceActivity.getItem(Constant.FIRST_NAME) +  " " + sharedPreferenceActivity.getItem(Constant.LAST_NAME) + "\n Please continue to sign in upon admin approval", Toast.LENGTH_LONG);
                            Intent intent = new Intent(SignUp.this, LogIn.class);

                            startActivity(intent);
                            finish();

                        } else {
                            AppUtilits.destroyDialog(progressbar);
                            AppUtilits.displayMessage(SignUp.this, response.body().getMsg());
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
}
