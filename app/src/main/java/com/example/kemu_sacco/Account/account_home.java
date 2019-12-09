package com.example.kemu_sacco.Account;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.example.kemu_sacco.beanResponse.KinRes;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class account_home extends AppCompatActivity {


    TextView  fname, lname,id, username, email, phone;
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


        getUserKin();
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
