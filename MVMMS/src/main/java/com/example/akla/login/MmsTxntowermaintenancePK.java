package com.example.akla.login;

import java.io.Serializable;

public class MmsTxntowermaintenancePK implements Serializable {

    private static final long serialVersionUID = 1L;

    private long visitid;

    private long towerid;

    public MmsTxntowermaintenancePK() {
    }
    public long getVisitid() {
        return this.visitid;
    }
    public void setVisitid(long visitid) {
        this.visitid = visitid;
    }
    public long getTowerid() {
        return this.towerid;
    }
    public void setTowerid(long towerid) {
        this.towerid = towerid;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MmsTxntowermaintenancePK)) {
            return false;
        }
        MmsTxntowermaintenancePK castOther = (MmsTxntowermaintenancePK)other;
        return
                (this.visitid == castOther.visitid)
                        && (this.towerid == castOther.towerid);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + ((int) (this.visitid ^ (this.visitid >>> 32)));
        hash = hash * prime + ((int) (this.towerid ^ (this.towerid >>> 32)));

        return hash;
    }

    /*private static final long serialVersionUID = 1L;

    private long visitid;

    private long towerid;
    public MmsTxntowermaintenancePK() {
    }
    public long getVisitid() {
        return this.visitid;
    }
    public void setVisitid(long visitid) {
        this.visitid = visitid;
    }
    public long getTowerid() {
        return this.towerid;
    }
    public void setTowerid(long towerid) {
        this.towerid = towerid;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MmsTxntowermaintenancePK)) {
            return false;
        }
        MmsTxntowermaintenancePK castOther = (MmsTxntowermaintenancePK)other;
        return
                (this.visitid == castOther.visitid)
                        && (this.towerid == castOther.towerid);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + ((int) (this.visitid ^ (this.visitid >>> 32)));
        hash = hash * prime + ((int) (this.towerid ^ (this.towerid >>> 32)));

        return hash;
    }

    public void setTowerid(String s) {
    }*/
}




