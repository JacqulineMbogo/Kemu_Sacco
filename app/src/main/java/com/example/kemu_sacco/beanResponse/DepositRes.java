package com.example.kemu_sacco.beanResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DepositRes  {

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

        @SerializedName("sharest_id")
        @Expose
        private String sharestId;
        @SerializedName("shares_amount")
        @Expose
        private String sharesAmount;
        @SerializedName("month")
        @Expose
        private String month;
        @SerializedName("staff")
        @Expose
        private String staff;

        public String getSharestId() {
            return sharestId;
        }

        public void setSharestId(String sharestId) {
            this.sharestId = sharestId;
        }

        public String getSharesAmount() {
            return sharesAmount;
        }

        public void setSharesAmount(String sharesAmount) {
            this.sharesAmount = sharesAmount;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getStaff() {
            return staff;
        }

        public void setStaff(String staff) {
            this.staff = staff;
        }

    }

}