package com.example.kemu_sacco.Contributions;

public class contribution_type_model {

    public contribution_type_model(String contribution_type_id, String contribution_name) {
        this.contribution_type_id = contribution_type_id;
        this.contribution_name = contribution_name;
    }

    public  String contribution_type_id, contribution_name;

    public String getContribution_type_id() {
        return contribution_type_id;
    }

    public String getContribution_name() {
        return contribution_name;
    }

    public void setContribution_type_id(String contribution_type_id) {
        this.contribution_type_id = contribution_type_id;
    }

    public void setContribution_name(String contribution_name) {
        this.contribution_name = contribution_name;
    }
}
