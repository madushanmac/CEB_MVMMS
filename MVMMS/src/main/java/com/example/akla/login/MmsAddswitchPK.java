package com.example.akla.login;

public class MmsAddswitchPK {
    private String feederId;
    private long gantryId;
    private String switchId;

    public MmsAddswitchPK(){

    }

    public String getFeederId() {
        return feederId;
    }

    public void setFeederId(String feederId) {
        this.feederId = feederId;
    }

    public long getGantryId() {
        return gantryId;
    }

    public void setGantryId(long gantryId) {
        this.gantryId = gantryId;
    }

    public String getSwitchId() {
        return switchId;
    }

    public void setSwitchId(String switchId) {
        this.switchId = switchId;
    }

    @Override
    public String toString() {
        return "MmsAddswitchPK{" +
                "feederId='" + feederId + '\'' +
                ", gantryId=" + gantryId +
                ", switchId='" + switchId + '\'' +
                '}';
    }
}
