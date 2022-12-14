package com.example.akla.login;

public class MmsAddpoletype {
    private String id;
    private String poleType;
    private String status;

    public MmsAddpoletype(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoleType() {
        return poleType;
    }

    public void setPoleType(String poleType) {
        this.poleType = poleType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MmsAddpoletype{" +
                "id='" + id + '\'' +
                ", poleType='" + poleType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
