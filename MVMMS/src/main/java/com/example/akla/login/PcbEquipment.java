package com.example.akla.login;

import java.math.BigDecimal;
import java.util.Date;

public class PcbEquipment  {

    private String equipmentId;
    private String itiConfPositive;
    private String itiResults;
    private String sentToIti;
    private String branch;
    private String area;
    private BigDecimal capacity;
    private String condition;
    private String divison;
    private Date enteredDate;
    private String manufactureBrand;
    private BigDecimal year;
    private Date manufactureDate;
    private String manufactureLtl;
    private BigDecimal oilWeight;
    private String photoRef;
    private String photoTaken;
    private String primarySub;
    private String referenceNo;
    private String seriaType;
    private String  serialNo;
    private BigDecimal status;
    private String type;
    private String unit;
    private Date updatedDate;
    private BigDecimal voltage;
    private String volumeOfOil;
    private BigDecimal weightTransformer;
    private BigDecimal volOil;
    private String photoRef2;
    private String photoRef3;
    private String voltageStr;


    public PcbEquipment() {
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getItiConfPositive() {
        return itiConfPositive;
    }

    public void setItiConfPositive(String itiConfPositive) {
        this.itiConfPositive = itiConfPositive;
    }

    public String getItiResults() {
        return itiResults;
    }

    public void setItiResults(String itiResults) {
        this.itiResults = itiResults;
    }

    public String getSentToIti() {
        return sentToIti;
    }

    public void setSentToIti(String sentToIti) {
        this.sentToIti = sentToIti;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDivison() {
        return divison;
    }

    public void setDivison(String divison) {
        this.divison = divison;
    }

    public Date getEnteredDate() {
        return enteredDate;
    }

    public void setEnteredDate(Date enteredDate) {
        this.enteredDate = enteredDate;
    }

    public String getManufactureBrand() {
        return manufactureBrand;
    }

    public void setManufactureBrand(String manufactureBrand) {
        this.manufactureBrand = manufactureBrand;
    }

    public BigDecimal getYear() {
        return year;
    }

    public void setYear(BigDecimal year) {
        this.year = year;
    }

    public Date getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(Date manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getManufactureLtl() {
        return manufactureLtl;
    }

    public void setManufactureLtl(String manufactureLtl) {
        this.manufactureLtl = manufactureLtl;
    }

    public BigDecimal getOilWeight() {
        return oilWeight;
    }

    public void setOilWeight(BigDecimal oilWeight) {
        this.oilWeight = oilWeight;
    }

    public String getPhotoRef() {
        return photoRef;
    }

    public void setPhotoRef(String photoRef) {
        this.photoRef = photoRef;
    }

    public String getPhotoTaken() {
        return photoTaken;
    }

    public void setPhotoTaken(String photoTaken) {
        this.photoTaken = photoTaken;
    }

    public String getPrimarySub() {
        return primarySub;
    }

    public void setPrimarySub(String primarySub) {
        this.primarySub = primarySub;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getSeriaType() {
        return seriaType;
    }

    public void setSeriaType(String seriaType) {
        this.seriaType = seriaType;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public BigDecimal getVoltage() {
        return voltage;
    }

    public void setVoltage(BigDecimal voltage) {
        this.voltage = voltage;
    }

    public String getVolumeOfOil() {
        return volumeOfOil;
    }

    public void setVolumeOfOil(String volumeOfOil) {
        this.volumeOfOil = volumeOfOil;
    }

    public BigDecimal getWeightTransformer() {
        return weightTransformer;
    }

    public void setWeightTransformer(BigDecimal weightTransformer) {
        this.weightTransformer = weightTransformer;
    }

    public BigDecimal getVolOil() {
        return volOil;
    }

    public void setVolOil(BigDecimal volOil) {
        this.volOil = volOil;
    }

    public String getPhotoRef2() {
        return photoRef2;
    }

    public void setPhotoRef2(String photoRef2) {
        this.photoRef2 = photoRef2;
    }

    public String getPhotoRef3() {
        return photoRef3;
    }

    public void setPhotoRef3(String photoRef3) {
        this.photoRef3 = photoRef3;
    }

    public String getVoltageStr() {
        return voltageStr;
    }

    public void setVoltageStr(String voltageStr) {
        this.voltageStr = voltageStr;
    }

    @Override
    public String toString() {
        return "PcbEquipment{" +
                "equipmentId='" + equipmentId + '\'' +
                ", itiConfPositive='" + itiConfPositive + '\'' +
                ", itiResults='" + itiResults + '\'' +
                ", sentToIti='" + sentToIti + '\'' +
                ", branch='" + branch + '\'' +
                ", area='" + area + '\'' +
                ", capacity=" + capacity +
                ", condition='" + condition + '\'' +
                ", divison='" + divison + '\'' +
                ", enteredDate=" + enteredDate +
                ", manufactureBrand='" + manufactureBrand + '\'' +
                ", year=" + year +
                ", manufactureDate=" + manufactureDate +
                ", manufactureLtl='" + manufactureLtl + '\'' +
                ", oilWeight=" + oilWeight +
                ", photoRef='" + photoRef + '\'' +
                ", photoTaken='" + photoTaken + '\'' +
                ", primarySub='" + primarySub + '\'' +
                ", referenceNo='" + referenceNo + '\'' +
                ", seriaType='" + seriaType + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", status=" + status +
                ", type='" + type + '\'' +
                ", unit='" + unit + '\'' +
                ", updatedDate=" + updatedDate +
                ", voltage=" + voltage +
                ", volumeOfOil='" + volumeOfOil + '\'' +
                ", weightTransformer=" + weightTransformer +
                ", volOil=" + volOil +
                ", photoRef2='" + photoRef2 + '\'' +
                ", photoRef3='" + photoRef3 + '\'' +
                ", voltageStr='" + voltageStr + '\'' +
                '}';
    }
}

