package com.example.kemu_sacco.Loans;

public class loans_model {

    private  String  application_id, amount, date,status, loan_id;

    public loans_model(String application_id, String amount, String date, String status, String loan_id) {
        this.application_id = application_id;
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.loan_id = loan_id;
    }

    public void setApplication_id(String application_id) {
        this.application_id = application_id;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLoan_id(String loan_id) {
        this.loan_id = loan_id;
    }

    public String getApplication_id() {
        return application_id;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getLoan_id() {
        return loan_id;
    }
}
