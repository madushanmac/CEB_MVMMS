package com.example.akla.login;

import java.math.BigDecimal;

public class MmsAddfeeder {

    private MmsAddfeederPK id;
    private String code;
    private BigDecimal conductor;
    private BigDecimal feederType;
    private BigDecimal isByPassAvai;
    private String name;
    private BigDecimal noAbs;
    private BigDecimal noAr;
    private BigDecimal noDdlo;
    private BigDecimal noLbs;
    private BigDecimal noSa;
    private BigDecimal typeInOut;
    private BigDecimal status;
    private BigDecimal gpsLongitude;
    private BigDecimal gpsLatitude;


    public MmsAddfeeder(){
    }

    public MmsAddfeederPK getId() {
        return id;
    }

    public BigDecimal getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(BigDecimal gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }
    public BigDecimal getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(BigDecimal gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }


    public void setId(MmsAddfeederPK id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getConductor() {
        return conductor;
    }

    public void setConductor(BigDecimal conductor) {
        this.conductor = conductor;
    }

    public BigDecimal getFeederType() {
        return feederType;
    }

    public void setFeederType(BigDecimal feederType) {
        this.feederType = feederType;
    }

    public BigDecimal getIsByPassAvai() {
        return isByPassAvai;
    }

    public void setIsByPassAvai(BigDecimal isByPassAvai) {
        this.isByPassAvai = isByPassAvai;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getNoAbs() {
        return noAbs;
    }

    public void setNoAbs(BigDecimal noAbs) {
        this.noAbs = noAbs;
    }

    public BigDecimal getNoAr() {
        return noAr;
    }

    public void setNoAr(BigDecimal noAr) {
        this.noAr = noAr;
    }

    public BigDecimal getNoDdlo() {
        return noDdlo;
    }

    public void setNoDdlo(BigDecimal noDdlo) {
        this.noDdlo = noDdlo;
    }

    public BigDecimal getNoLbs() {
        return noLbs;
    }

    public void setNoLbs(BigDecimal noLbs) {
        this.noLbs = noLbs;
    }

    public BigDecimal getNoSa() {
        return noSa;
    }

    public void setNoSa(BigDecimal noSa) {
        this.noSa = noSa;
    }

    public BigDecimal getTypeInOut() {
        return typeInOut;
    }

    public void setTypeInOut(BigDecimal typeInOut) {
        this.typeInOut = typeInOut;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "MmsAddfeeder{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", conductor=" + conductor +
                ", feederType=" + feederType +
                ", isByPassAvai=" + isByPassAvai +
                ", name='" + name + '\'' +
                ", noAbs=" + noAbs +
                ", noAr=" + noAr +
                ", noDdlo=" + noDdlo +
                ", noLbs=" + noLbs +
                ", noSa=" + noSa +
                ", typeInOut=" + typeInOut +
                ", status=" + status +
                ", gpsLongitude=" + gpsLongitude +
                ", gpsLatitude=" + gpsLatitude +
                '}';
    }
}
