package com.example.kemu_sacco.beanResponse;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContributionRes {

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

        @SerializedName("contribution_id")
        @Expose
        private String contributionId;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("contribution_date")
        @Expose
        private String contributionDate;
        @SerializedName("contribution_type_id")
        @Expose
        private String contributionTypeId;


        public String getContributionId() {
            return contributionId;
        }

        public void setContributionId(String contributionId) {
            this.contributionId = contributionId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getContributionDate() {
            return contributionDate;
        }

        public void setContributionDate(String contributionDate) {
            this.contributionDate = contributionDate;
        }

        public String getContributionTypeId() {
            return contributionTypeId;
        }

        public void setContributionTypeId(String contributionTypeId) {
            this.contributionTypeId = contributionTypeId;
        }

    }

}