package com.example.akla.login;

import java.util.List;

public class PoleModel {

    private MmsAddmvpole mvPole;
    private List<MmsAddmvpolecct> mvPoleCctList;

    public PoleModel() {
    }

    public MmsAddmvpole getMvPole() {
        return mvPole;
    }

    public void setMvPole(MmsAddmvpole mvPole) {
        this.mvPole = mvPole;
    }

    public List<MmsAddmvpolecct> getMvPoleCctList() {
        return mvPoleCctList;
    }

    public void setMvPoleCctList(List<MmsAddmvpolecct> mvPoleCctList) {
        this.mvPoleCctList = mvPoleCctList;
    }

    @Override
    public String toString() {
        return "PoleModel{" +
                "mvPole=" + mvPole +
                ", mvPoleCctList=" + mvPoleCctList +
                '}';
    }
}
