package com.example.kemu_sacco.Loans;

public class loan_type_model {

    String loan_type_id, loan_type_name,max, rate;

    public loan_type_model(String loan_type_id, String loan_type_name, String max,String rate) {
        this.loan_type_id = loan_type_id;
        this.loan_type_name = loan_type_name;
        this.max = max;
        this.rate= rate;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getMax() {
        return max;
    }

    public String getRate() {
        return rate;
    }

    public String getLoan_type_id() {
        return loan_type_id;
    }

    public String getLoan_type_name() {
        return loan_type_name;
    }

    public void setLoan_type_id(String loan_type_id) {
        this.loan_type_id = loan_type_id;
    }

    public void setLoan_type_name(String loan_type_name) {
        this.loan_type_name = loan_type_name;
    }
}
