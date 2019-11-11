package com.example.kemu_sacco.Contributions;

public class contributions_model {

    private String contribution_id, amount, contribution_date, contribution_type_id;

    public contributions_model(String contribution_id, String amount, String contribution_date, String contribution_type_id) {
        this.contribution_id = contribution_id;
        this.amount = amount;
        this.contribution_date = contribution_date;
        this.contribution_type_id = contribution_type_id;
    }

    public void setContribution_id(String contribution_id) {
        this.contribution_id = contribution_id;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setContribution_date(String contribution_date) {
        this.contribution_date = contribution_date;
    }

    public void setContribution_type_id(String contribution_type_id) {
        this.contribution_type_id = contribution_type_id;
    }

    public String getContribution_id() {
        return contribution_id;
    }

    public String getAmount() {
        return amount;
    }

    public String getContribution_date() {
        return contribution_date;
    }

    public String getContribution_type_id() {
        return contribution_type_id;
    }
}
