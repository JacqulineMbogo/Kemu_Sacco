package com.example.kemu_sacco.Deposits;

public class deposit_model {

    public  String id, amount, date, staff;

    public deposit_model(String id, String amount, String date, String staff) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.staff = staff;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
