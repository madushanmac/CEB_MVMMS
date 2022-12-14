package com.example.akla.login;

public class MmsAddfeederPK {

    private String feederId;
    private long gantryId;

   public MmsAddfeederPK(){

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

    @Override
    public String toString() {
        return "MmsAddfeederPK{" +
                "feederId='" + feederId + '\'' +
                ", gantryId=" + gantryId +
                '}';
    }
}
