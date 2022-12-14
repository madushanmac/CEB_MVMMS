package com.example.akla.login;

public class MmsGantrylocPK {
    private long gantryId;
    private long pointId;

    public MmsGantrylocPK(){

    }

    public long getGantryId() {
        return gantryId;
    }

    public void setGantryId(long gantryId) {
        this.gantryId = gantryId;
    }

    public long getPointId() {
        return pointId;
    }

    public void setPointId(long pointId) {
        this.pointId = pointId;
    }

    @Override
    public String toString() {
        return "MmsGantrylocPK{" +
                "gantryId=" + gantryId +
                ", pointId=" + pointId +
                '}';
    }
}
