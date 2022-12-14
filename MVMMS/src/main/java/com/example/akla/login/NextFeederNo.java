package com.example.akla.login;

public class NextFeederNo {
    private String FeederNo;

    public NextFeederNo(){

    }

    public String getFeederNo() {

        return FeederNo;
    }

    public void setFeederNo(String feederNo) {

        FeederNo = feederNo;
    }

    @Override
    public String toString() {
        return "NextFeederNo{" +
                "FeederNo='" + FeederNo + '\'' +
                '}';
    }
}
