package com.example.kemu_sacco.beanResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanPaymentsRes {

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

        @SerializedName("payment_id")
        @Expose
        private String paymentId;
        @SerializedName("payment_amount")
        @Expose
        private String paymentAmount;
        @SerializedName("create_date")
        @Expose
        private String createDate;
        @SerializedName("status")
        @Expose
        private String status;

        public String getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(String paymentId) {
            this.paymentId = paymentId;
        }

        public String getPaymentAmount() {
            return paymentAmount;
        }

        public void setPaymentAmount(String paymentAmount) {
            this.paymentAmount = paymentAmount;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

}