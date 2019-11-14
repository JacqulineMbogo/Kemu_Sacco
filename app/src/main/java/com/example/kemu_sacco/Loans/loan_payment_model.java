package com.example.kemu_sacco.Loans;

public class loan_payment_model {

    private String payment_id, payment_amount, create_date, status;

    public loan_payment_model(String payment_id, String payment_amount, String create_date, String status) {
        this.payment_id = payment_id;
        this.payment_amount = payment_amount;
        this.create_date = create_date;
        this.status = status;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public void setPayment_amount(String payment_amount) {
        this.payment_amount = payment_amount;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public String getPayment_amount() {
        return payment_amount;
    }

    public String getCreate_date() {
        return create_date;
    }

    public String getStatus() {
        return status;
    }
}
