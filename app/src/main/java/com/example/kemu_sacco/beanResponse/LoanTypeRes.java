package com.example.kemu_sacco.beanResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoanTypeRes {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("Information")
    @Expose
    private List<Information> information = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Information> getInformation() {
        return information;
    }

    public void setInformation(List<Information> information) {
        this.information = information;
    }

    public class Information {

        @SerializedName("loan_type_id")
        @Expose
        private String loanTypeId;
        @SerializedName("loan_type")
        @Expose
        private String loanType;

        public String getLoanTypeId() {
            return loanTypeId;
        }

        public void setLoanTypeId(String loanTypeId) {
            this.loanTypeId = loanTypeId;
        }

        public String getLoanType() {
            return loanType;
        }

        public void setLoanType(String loanType) {
            this.loanType = loanType;
        }

    }

}