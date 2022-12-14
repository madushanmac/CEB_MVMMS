package com.example.akla.login;

import java.io.Serializable;

public class MmsModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private MmsAddline mmsAddline;
    /*private MmsAddArea mmsAddArea;
    private MmsAddCondition mmsAddCondition;
    private MmsAddconductortype mmsAddconductortype;
    private MmsAddlinetype mmsAddlinetype;
    private MmsAddPole mmsAddPole;
    private MmsAddSample mmsAddSample;
    private MmsAddsupport mmsAddsupport;
    private MmsAddtowerconfiguration mmsAddtowerconfiguration;
    private MmsCycle mmsCycle;
    private MmsTowertype mmsTowertype;
    private MmsTxntowermaintenance mmsTxntowermaintenance;
    private MmsTxntowermaintenancePK mmsTxntowermaintenancePK;*/


    public MmsAddline getMmsAddline() {
        return mmsAddline;
    }

    public void setMmsAddline(MmsAddline mmsAddline) {
        this.mmsAddline = mmsAddline;
    }

    /*public MmsAddArea getMmsAddArea() {
        return mmsAddArea;
    }

    public void setMmsAddArea(MmsAddArea mmsAddArea) {
        this.mmsAddArea = mmsAddArea;
    }

    public MmsAddCondition getMmsAddCondition() {
        return mmsAddCondition;
    }

    public void setMmsAddCondition(MmsAddCondition mmsAddCondition) {
        this.mmsAddCondition = mmsAddCondition;
    }

    public MmsAddconductortype getMmsAddconductortype() {
        return mmsAddconductortype;
    }

    public void setMmsAddconductortype(MmsAddconductortype mmsAddconductortype) {
        this.mmsAddconductortype = mmsAddconductortype;
    }


    public MmsAddlinetype getMmsAddlinetype() {
        return mmsAddlinetype;
    }

    public void setMmsAddlinetype(MmsAddlinetype mmsAddlinetype) {
        this.mmsAddlinetype = mmsAddlinetype;
    }

    public MmsAddPole getMmsAddPole() {
        return mmsAddPole;
    }

    public void setMmsAddPole(MmsAddPole mmsAddPole) {
        this.mmsAddPole = mmsAddPole;
    }

    public MmsAddSample getMmsAddSample() {
        return mmsAddSample;
    }

    public void setMmsAddSample(MmsAddSample mmsAddSample) {
        this.mmsAddSample = mmsAddSample;
    }

    public MmsAddsupport getMmsAddsupport() {
        return mmsAddsupport;
    }

    public void setMmsAddsupport(MmsAddsupport mmsAddsupport) {
        this.mmsAddsupport = mmsAddsupport;
    }

    public MmsAddtowerconfiguration getMmsAddtowerconfiguration() {
        return mmsAddtowerconfiguration;
    }

    public void setMmsAddtowerconfiguration(MmsAddtowerconfiguration mmsAddtowerconfiguration) {
        this.mmsAddtowerconfiguration = mmsAddtowerconfiguration;
    }

    public MmsCycle getMmsCycle() {
        return mmsCycle;
    }

    public void setMmsCycle(MmsCycle mmsCycle) {
        this.mmsCycle = mmsCycle;
    }

    public MmsTowertype getMmsTowertype() {
        return mmsTowertype;
    }

    public void setMmsTowertype(MmsTowertype mmsTowertype) {
        this.mmsTowertype = mmsTowertype;
    }

    public MmsTxntowermaintenance getMmsTxntowermaintenance() {
        return mmsTxntowermaintenance;
    }

    public void setMmsTxntowermaintenance(MmsTxntowermaintenance mmsTxntowermaintenance) {
        this.mmsTxntowermaintenance = mmsTxntowermaintenance;
    }

    public MmsTxntowermaintenancePK getMmsTxntowermaintenancePK() {
        return mmsTxntowermaintenancePK;
    }

    public void setMmsTxntowermaintenancePK(MmsTxntowermaintenancePK mmsTxntowermaintenancePK) {
        this.mmsTxntowermaintenancePK = mmsTxntowermaintenancePK;
    }*/


    /*@Override
    public String toString() {
        return "MmsModel{" +
                "mmsAddArea=" + mmsAddArea + "\n" +
                "mmsAddCondition=" + mmsAddCondition + "\n" +
                "mmsAddconductortype=" + mmsAddconductortype + "\n" +
                "mmsAddline=" + mmsAddline + "\n" +
                "mmsAddlinetype=" + mmsAddlinetype + "\n" +
                "mmsAddPole=" + mmsAddPole + "\n" +
                "mmsAddSample=" + mmsAddSample + "\n" +
                "mmsAddsupport=" + mmsAddsupport + "\n" +
                "mmsAddtowerconfiguration=" + mmsAddtowerconfiguration + "\n" +
                "mmsCycle=" + mmsCycle + "\n" +
                "mmsTowertype=" + mmsTowertype + "\n" +
                "mmsTxntowermaintenance=" + mmsTxntowermaintenance + "\n" +
                "mmsTxntowermaintenancePK=" + mmsTxntowermaintenancePK +
                '}' + " \n\n " ;
    }*/

    @Override
    public String toString() {
        return "MmsModel{" +
                "mmsAddline=" + mmsAddline +
                '}';
    }
}
