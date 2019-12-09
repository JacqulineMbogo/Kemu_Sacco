package com.example.kemu_sacco.Withdrawals;

public class withdrawals_model {

    private  String withdrawal_id,withdrawal_amount, withdrawal_date, withdrawal_status;

    public withdrawals_model(String withdrawal_id, String withdrawal_amount, String withdrawal_date, String withdrawal_status) {
        this.withdrawal_id = withdrawal_id;
        this.withdrawal_amount = withdrawal_amount;
        this.withdrawal_date = withdrawal_date;
        this.withdrawal_status = withdrawal_status;
    }

    public String getWithdrawal_id() {
        return withdrawal_id;
    }

    public void setWithdrawal_id(String withdrawal_id) {
        this.withdrawal_id = withdrawal_id;
    }

    public String getWithdrawal_amount() {
        return withdrawal_amount;
    }

    public void setWithdrawal_amount(String withdrawal_amount) {
        this.withdrawal_amount = withdrawal_amount;
    }

    public String getWithdrawal_date() {
        return withdrawal_date;
    }

    public void setWithdrawal_date(String withdrawal_date) {
        this.withdrawal_date = withdrawal_date;
    }

    public String getWithdrawal_status() {
        return withdrawal_status;
    }

    public void setWithdrawal_status(String withdrawal_status) {
        this.withdrawal_status = withdrawal_status;
    }
}
