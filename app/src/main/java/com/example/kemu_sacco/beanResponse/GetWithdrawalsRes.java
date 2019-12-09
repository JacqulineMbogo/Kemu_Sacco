package com.example.kemu_sacco.beanResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetWithdrawalsRes {
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

        @SerializedName("withdrawal_id")
        @Expose
        private String withdrawalId;
        @SerializedName("withdrawal_amount")
        @Expose
        private String withdrawalAmount;
        @SerializedName("withdrawal_date")
        @Expose
        private String withdrawalDate;
        @SerializedName("withdrawal_status")
        @Expose
        private String withdrawalStatus;

        public String getWithdrawalId() {
            return withdrawalId;
        }

        public void setWithdrawalId(String withdrawalId) {
            this.withdrawalId = withdrawalId;
        }

        public String getWithdrawalAmount() {
            return withdrawalAmount;
        }

        public void setWithdrawalAmount(String withdrawalAmount) {
            this.withdrawalAmount = withdrawalAmount;
        }

        public String getWithdrawalDate() {
            return withdrawalDate;
        }

        public void setWithdrawalDate(String withdrawalDate) {
            this.withdrawalDate = withdrawalDate;
        }

        public String getWithdrawalStatus() {
            return withdrawalStatus;
        }

        public void setWithdrawalStatus(String withdrawalStatus) {
            this.withdrawalStatus = withdrawalStatus;
        }

    }

}