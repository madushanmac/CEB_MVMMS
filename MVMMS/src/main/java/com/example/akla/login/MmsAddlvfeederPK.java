package com.example.akla.login;

public class MmsAddlvfeederPK {

    private String feederId;
    private String  subId;

    public MmsAddlvfeederPK() {
    }

    public String getFeederId() {
        return feederId;
    }

    public void setFeederId(String feederId) {
        this.feederId = feederId;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    @Override
    public String toString() {
        return "com.example.akla.login.MmsAddlvfeederPK{" +
                "feederId='" + feederId + '\'' +
                ", subId=" + subId +
                '}';
    }
}
