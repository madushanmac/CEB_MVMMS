package com.example.akla.login;

public class MmsAddsurgearrestorPK {
    private String saId;
    private long gantryId;

    public String getSaId() {
        return saId;
    }

    public void setSaId(String saId) {
        this.saId = saId;
    }

    public long getGantryId() {
        return gantryId;
    }

    public void setGantryId(long gantryId) {
        this.gantryId = gantryId;
    }

    @Override
    public String toString() {
        return "MmsAddsurgearrestorPK{" +
                "saId='" + saId + '\'' +
                ", gantryId=" + gantryId +
                '}';
    }
}
