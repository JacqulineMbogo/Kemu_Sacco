package com.example.kemu_sacco.WebServices;


import com.example.kemu_sacco.beanResponse.ContributionRes;
import com.example.kemu_sacco.beanResponse.ContributionTypeRes;
import com.example.kemu_sacco.beanResponse.LoanPaymentsRes;
import com.example.kemu_sacco.beanResponse.LoanTypeRes;
import com.example.kemu_sacco.beanResponse.LoansApplicationRes;
import com.example.kemu_sacco.beanResponse.MakeLoanPaymentRes;
import com.example.kemu_sacco.beanResponse.NewLoanApplicationRes;
import com.example.kemu_sacco.beanResponse.NewUserRegistration;
import com.example.kemu_sacco.beanResponse.NextofKinRes;
import com.example.kemu_sacco.beanResponse.SaveContributionRes;
import com.example.kemu_sacco.beanResponse.UserSignInRes;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceInterface {
    // method,, return type ,, secondary url
    @Multipart
    @POST("kemu_sacco/new_user_registration.php")
    Call<NewUserRegistration> NewUserRegistrationCall(
            @Part("id_number") RequestBody id_number,
            @Part("first_name") RequestBody first_name,
            @Part("last_name") RequestBody last_name,
            @Part("email") RequestBody email,
            @Part("phone_number") RequestBody phone_number,
            @Part("username") RequestBody username,
            @Part("amount") RequestBody amount,
            @Part("password") RequestBody password
    );

    ///  user signin request
    @Multipart
    @POST("kemu_sacco/user_signin.php")
    Call<UserSignInRes> UserSigninCall(
            @Part("id_number") RequestBody id_number,
            @Part("password") RequestBody password
    );

    ///  get all contributions
    @Multipart
    @POST("kemu_sacco/get_all_contributions.php")
    Call<ContributionRes> ContributionCall(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id
    );

    /// save new contributions
    @Multipart
    @POST("kemu_sacco/save_new_contributions.php")
    Call<SaveContributionRes> SaveContributionCall(
            @Part("securecode") RequestBody securecode,
            @Part("contribution_type_id") RequestBody contribution_type_id,
            @Part("amount") RequestBody amount,
            @Part("user_id") RequestBody user_id,
            @Part("code") RequestBody code
    );

    ///  get all contributions types
    @Multipart
    @POST("kemu_sacco/get_contribution_types.php")
    Call<ContributionTypeRes> ContributionTypeCall(
            @Part("securecode") RequestBody securecode

    );

    ///  get loan types
    @Multipart
    @POST("kemu_sacco/get_loan_types.php")
    Call<LoanTypeRes> LoanTypeCall(
            @Part("securecode") RequestBody securecode

    );

    ///  get all loans
    @Multipart
    @POST("kemu_sacco/get_all_loans.php")
    Call<LoansApplicationRes> LoansCall(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id
    );

    ///  get  loan payments
    @Multipart
    @POST("kemu_sacco/get_loan_payments.php")
    Call<LoanPaymentsRes> LoanPaymentCall(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id,
            @Part("application_id") RequestBody application_id
    );

    /// save new loan
    @Multipart
    @POST("kemu_sacco/new_loan_application.php")
    Call<NewLoanApplicationRes> SaveNewLoanCall(
            @Part("securecode") RequestBody securecode,
            @Part("loan_type_id") RequestBody loan_type_id,
            @Part("amount") RequestBody amount,
            @Part("user_id") RequestBody user_id

    );

    /// loan payment
    @Multipart
    @POST("kemu_sacco/new_loan_payment.php")
    Call<MakeLoanPaymentRes> MakeLoanPaymentCall(
            @Part("securecode") RequestBody securecode,
            @Part("application_id") RequestBody application_id,
            @Part("amount") RequestBody amount,
            @Part("user_id") RequestBody user_id,
            @Part("code") RequestBody code

    );

    /// loan payment
    @Multipart
    @POST("kemu_sacco/add_next_of_kin.php")
    Call<NextofKinRes> NextofKinResCall(
            @Part("securecode") RequestBody securecode,
            @Part("name") RequestBody name,
            @Part("id") RequestBody id,
            @Part("relation") RequestBody relation,
            @Part("phone") RequestBody phone,
            @Part("amount") RequestBody amount,
            @Part("user_id") RequestBody user_id


    );



}




