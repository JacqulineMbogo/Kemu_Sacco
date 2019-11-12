package com.example.kemu_sacco.beanResponse;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContributionTypeRes {

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

        @SerializedName("contribution_type_id")
        @Expose
        private String contributionTypeId;
        @SerializedName("contribution_type")
        @Expose
        private String contributionType;

        public String getContributionTypeId() {
            return contributionTypeId;
        }

        public void setContributionTypeId(String contributionTypeId) {
            this.contributionTypeId = contributionTypeId;
        }

        public String getContributionType() {
            return contributionType;
        }

        public void setContributionType(String contributionType) {
            this.contributionType = contributionType;
        }

    }
}