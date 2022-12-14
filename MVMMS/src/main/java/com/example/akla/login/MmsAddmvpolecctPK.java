package com.example.akla.login;

import java.util.Objects;

public class MmsAddmvpolecctPK {

    private String id;
    private long poleId;

    public MmsAddmvpolecctPK() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPoleId() {
        return poleId;
    }

    public void setPoleId(long poleId) {
        this.poleId = poleId;
    }

    @Override
    public boolean equals(Object other)  {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MmsAddmvpolecctPK)) {
            return false;
        }
        MmsAddmvpolecctPK castOther = (MmsAddmvpolecctPK)other;
        return
                this.id.equals(castOther.id)
                        && (this.poleId == castOther.poleId);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.id.hashCode();
        hash = hash * prime + ((int) (this.poleId ^ (this.poleId >>> 32)));

        return hash;
    }

    @Override
    public String toString() {
        return "MmsAddmvpolecctPK{" +
                "id='" + id + '\'' +
                ", poleId=" + poleId +
                '}';
    }
}
