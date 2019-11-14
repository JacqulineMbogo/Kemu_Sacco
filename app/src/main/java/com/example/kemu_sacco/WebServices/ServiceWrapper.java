package com.example.kemu_sacco.WebServices;


import com.example.kemu_sacco.BuildConfig;
import com.example.kemu_sacco.Utility.Constant;
import com.example.kemu_sacco.beanResponse.ContributionRes;
import com.example.kemu_sacco.beanResponse.ContributionTypeRes;
import com.example.kemu_sacco.beanResponse.LoanPaymentsRes;
import com.example.kemu_sacco.beanResponse.LoansApplicationRes;
import com.example.kemu_sacco.beanResponse.MakeLoanPaymentRes;
import com.example.kemu_sacco.beanResponse.NewLoanApplicationRes;
import com.example.kemu_sacco.beanResponse.NewUserRegistration;
import com.example.kemu_sacco.beanResponse.SaveContributionRes;
import com.example.kemu_sacco.beanResponse.UserSignInRes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ServiceWrapper  {

    private ServiceInterface mServiceInterface;

    public ServiceWrapper(Interceptor mInterceptorheader) {
        mServiceInterface = getRetrofit(mInterceptorheader).create(ServiceInterface.class);
    }

    public Retrofit getRetrofit(Interceptor mInterceptorheader) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient mOkHttpClient = null;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(Constant.API_CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(Constant.API_READ_TIMEOUT, TimeUnit.SECONDS);

//        if (BuildConfig.DEBUG)
//            builder.addInterceptor(loggingInterceptor);

        if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }


        mOkHttpClient = builder.build();
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(mOkHttpClient)
                .build();
        return retrofit;
    }

    public Call<NewUserRegistration> newUserRegistrationCall(String id_number,String first_name, String last_name,  String email, String phone_number, String username, String password){
        return mServiceInterface.NewUserRegistrationCall( convertPlainString(id_number),convertPlainString(first_name),convertPlainString(last_name),convertPlainString(email), convertPlainString(phone_number), convertPlainString( username),
                convertPlainString(password));
    }
    ///  user signin
    public Call<UserSignInRes> UserSigninCall(String id_number, String password){
        return mServiceInterface.UserSigninCall(convertPlainString(id_number), convertPlainString(password));
    }

    ///  get all contributions
    public Call<ContributionRes> ContributionCall(String securecode, String user_id){
        return mServiceInterface.ContributionCall(convertPlainString(securecode), convertPlainString(user_id));
    }

    ///  new contribution
    public Call<SaveContributionRes> SaveContributionCall(String securecode,String contribution_type_id,String amount, String user_id, String code){
        return mServiceInterface.SaveContributionCall(convertPlainString(securecode),convertPlainString(contribution_type_id),convertPlainString(amount), convertPlainString(user_id), convertPlainString(code));
    }


    ///  get all contributions types
    public Call<ContributionTypeRes> ContributionTypeCall(String securecode){
        return mServiceInterface.ContributionTypeCall(convertPlainString(securecode));
    }

    ///  get all loans
    public Call<LoansApplicationRes> LoansCall(String securecode, String user_id){
        return mServiceInterface.LoansCall(convertPlainString(securecode), convertPlainString(user_id));
    }

    ///  get all loan payments
    public Call<LoanPaymentsRes> LoanPaymentCall(String securecode, String user_id, String application_id){
        return mServiceInterface.LoanPaymentCall(convertPlainString(securecode), convertPlainString(user_id),  convertPlainString(application_id));
    }

    ///  new loan
    public Call<NewLoanApplicationRes> SaveNewLoanCall(String securecode, String loan_type_id, String amount, String user_id){
        return mServiceInterface.SaveNewLoanCall(convertPlainString(securecode),convertPlainString(loan_type_id),convertPlainString(amount), convertPlainString(user_id));
    }

    ///  make loan payment
    public Call<MakeLoanPaymentRes> MakeLoanPaymentCall(String securecode, String application_id, String amount, String user_id){
        return mServiceInterface.MakeLoanPaymentCall(convertPlainString(securecode),convertPlainString(application_id),convertPlainString(amount), convertPlainString(user_id));
    }

    // convert aa param into plain text
    public RequestBody convertPlainString(String data){
        RequestBody plainString = RequestBody.create(MediaType.parse("text/plain"), data);
        return  plainString;
    }

}


