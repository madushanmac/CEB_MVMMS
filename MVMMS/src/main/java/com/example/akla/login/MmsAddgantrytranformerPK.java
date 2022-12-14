package com.example.akla.login;

public class MmsAddgantrytranformerPK {

    private String trId;
    private long gantryId;

    MmsAddgantrytranformerPK(){

    }

    public String getTrId() {
        return trId;
    }

    public void setTrId(String trId) {
        this.trId = trId;
    }

    public long getGantryId() {
        return gantryId;
    }

    public void setGantryId(long gantryId) {
        this.gantryId = gantryId;
    }

    @Override
    public String toString() {
        return "MmsAddgantrytranformerPK{" +
                "trId='" + trId + '\'' +
                ", gantryId=" + gantryId +
                '}';
    }
}
