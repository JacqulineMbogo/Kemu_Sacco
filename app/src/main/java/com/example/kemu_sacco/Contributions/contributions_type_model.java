package com.example.kemu_sacco.Contributions;

public class contributions_type_model {

    public   String contribution_type_id, contribution_type;

    public contributions_type_model(String contribution_type_id, String contribution_type) {
        this.contribution_type_id = contribution_type_id;
        this.contribution_type = contribution_type;
    }

    public String getContribution_type_id() {
        return contribution_type_id;
    }

    public String getContribution_type() {
        return contribution_type;
    }

    public void setContribution_type_id(String contribution_type_id) {
        this.contribution_type_id = contribution_type_id;
    }

    public void setContribution_type(String contribution_type) {
        this.contribution_type = contribution_type;
    }
}
