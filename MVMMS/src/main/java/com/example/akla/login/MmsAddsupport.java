package com.example.akla.login;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class MmsAddsupport implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;

    private BigDecimal gantryId;

    private String approvalLevel;

    private String area;

    private String bodyExtension;

    private BigDecimal conductorType;

    private String csc;

    private BigDecimal earthConductorType;

    private String filepath;

    private BigDecimal gpsLatitude;

    private BigDecimal gpsLongitude;

    private BigDecimal lineId;

    private String lineName;

    private BigDecimal noOfCircuits;

    private String phmBranch;

    private BigDecimal status;

    private BigDecimal supportType;

    private BigDecimal towerConfiguration;

    private String towerNo;

    private BigDecimal towerType;

    private BigDecimal tapping;

    private BigDecimal mapId;

    private String approvedBy;

    private Date approvedDate;

    private String approvedTime;

    private String entBy;

    private String entTime;

    private Date entDate;

    private String validateBy;

    private Date validateDate;

    private String validateTime;

    private byte[] image;

    private String feederIdentification;

    private String responsibleArea;

    private String sectionId;

    private String sectionIdTo;

    public String getResponsibleArea() {
        return this.responsibleArea;
    }

    public void setResponsibleArea(String responsibleArea) {
        this.responsibleArea = responsibleArea;
    }

    public String getSectionId() {
        return this.sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionIdTo() {
        return this.sectionIdTo;
    }

    public void setSectionIdTo(String sectionIdTo) {
        this.sectionIdTo = sectionIdTo;
    }





    public MmsAddsupport() {
    }


    public BigDecimal getMapId() {
        return mapId;
    }


    public void setMapId(BigDecimal mapId) {
        this.mapId = mapId;
    }

    public BigDecimal getTapping() {
        return tapping;
    }

    public void setTapping(BigDecimal tapping) {
        this.tapping = tapping;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApprovalLevel() {
        return this.approvalLevel;
    }

    public void setApprovalLevel(String approvalLevel) {
        this.approvalLevel = approvalLevel;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBodyExtension() {
        return this.bodyExtension;
    }

    public void setBodyExtension(String bodyExtension) {
        this.bodyExtension = bodyExtension;
    }

    public BigDecimal getConductorType() {
        return this.conductorType;
    }

    public void setConductorType(BigDecimal conductorType) {
        this.conductorType = conductorType;
    }

    public String getCsc() {
        return this.csc;
    }

    public void setCsc(String csc) {
        this.csc = csc;
    }

    public BigDecimal getEarthConductorType() {
        return this.earthConductorType;
    }

    public void setEarthConductorType(BigDecimal earthConductorType) {
        this.earthConductorType = earthConductorType;
    }

    public String getFilepath() {
        return this.filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public BigDecimal getGpsLatitude() {
        return this.gpsLatitude;
    }

    public void setGpsLatitude(BigDecimal gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public BigDecimal getGpsLongitude() {
        return this.gpsLongitude;
    }

    public void setGpsLongitude(BigDecimal gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public BigDecimal getLineId() {
        return this.lineId;
    }

    public void setLineId(BigDecimal lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return this.lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public BigDecimal getNoOfCircuits() {
        return this.noOfCircuits;
    }

    public void setNoOfCircuits(BigDecimal noOfCircuits) {
        this.noOfCircuits = noOfCircuits;
    }

    public String getPhmBranch() {
        return this.phmBranch;
    }

    public void setPhmBranch(String phmBranch) {
        this.phmBranch = phmBranch;
    }

    public BigDecimal getStatus() {
        return this.status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public BigDecimal getSupportType() {
        return this.supportType;
    }

    public void setSupportType(BigDecimal supportType) {
        this.supportType = supportType;
    }

    public BigDecimal getTowerConfiguration() {
        return this.towerConfiguration;
    }

    public void setTowerConfiguration(BigDecimal towerConfiguration) {
        this.towerConfiguration = towerConfiguration;
    }

    public String getTowerNo() {
        return this.towerNo;
    }

    public void setTowerNo(String towerNo) {
        this.towerNo = towerNo;
    }

    public BigDecimal getTowerType() {
        return this.towerType;
    }

    public void setTowerType(BigDecimal towerType) {
        this.towerType = towerType;
    }

    public String getEntBy() {
        return this.entBy;
    }

    public void setEntBy(String entBy) {
        this.entBy = entBy;
    }
    public String getEntTime() {
        return this.entTime;
    }

    public void setEntTime(String entTime) {
        this.entTime = entTime;
    }

    public String getValidateBy() {
        return this.validateBy;
    }

    public void setValidateBy(String validateBy) {
        this.validateBy = validateBy;
    }

    public Date getValidateDate() {
        return this.validateDate;
    }

    public void setValidateDate(Date validateDate) {
        this.validateDate = validateDate;
    }

    public String getValidateTime() {
        return this.validateTime;
    }

    public void setValidateTime(String validateTime) {
        this.validateTime = validateTime;
    }

    public String getApprovedTime() {
        return this.approvedTime;
    }

    public void setApprovedTime(String approvedTime) {
        this.approvedTime = approvedTime;
    }


    public Date getEntDate() {
        return entDate;
    }

    public void setEntDate(Date entDate) {
        this.entDate = entDate;
    }

    public BigDecimal getGantryId() {
        return gantryId;
    }

    public void setGantryId(BigDecimal gantryId) {
        this.gantryId = gantryId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFeederIdentification() {
        return feederIdentification;
    }

    public void setFeederIdentification(String feederIdentification) {
        this.feederIdentification = feederIdentification;
    }


    private Date interruptedDate;

    public Date getInterruptedDate() {
        return interruptedDate;
    }

    public void setInterruptedDate(Date interruptedDate) {
        this.interruptedDate = interruptedDate;
    }

    private BigDecimal interruptedYes;

    public BigDecimal getInterruptedYes() {
        return interruptedYes;
    }

    public void setInterruptedYes(BigDecimal interruptedYes) {
        this.interruptedYes = interruptedYes;
    }
    private String interruptionNo;


    public String getInterruptionNo() {
        return interruptionNo;
    }

    public void setInterruptionNo(String interruptionNo) {
        this.interruptionNo = interruptionNo;
    }


    @Override
    public String toString() {
        return "MmsAddsupport{" +
                "id=" + id +
                ", gantryId=" + gantryId +
                ", approvalLevel='" + approvalLevel + '\'' +
                ", area='" + area + '\'' +
                ", bodyExtension='" + bodyExtension + '\'' +
                ", conductorType=" + conductorType +
                ", csc='" + csc + '\'' +
                ", earthConductorType=" + earthConductorType +
                ", filepath='" + filepath + '\'' +
                ", gpsLatitude=" + gpsLatitude +
                ", gpsLongitude=" + gpsLongitude +
                ", lineId=" + lineId +
                ", lineName='" + lineName + '\'' +
                ", noOfCircuits=" + noOfCircuits +
                ", phmBranch='" + phmBranch + '\'' +
                ", status=" + status +
                ", supportType=" + supportType +
                ", towerConfiguration=" + towerConfiguration +
                ", towerNo='" + towerNo + '\'' +
                ", towerType=" + towerType +
                ", tapping=" + tapping +
                ", mapId=" + mapId +
                ", approvedBy='" + approvedBy + '\'' +
                ", approvedDate=" + approvedDate +
                ", approvedTime='" + approvedTime + '\'' +
                ", entBy='" + entBy + '\'' +
                ", entTime='" + entTime + '\'' +
                ", entDate=" + entDate +
                ", validateBy='" + validateBy + '\'' +
                ", validateDate=" + validateDate +
                ", validateTime='" + validateTime + '\'' +
                ", image=" + Arrays.toString(image) +
                ", feederIdentification='" + feederIdentification + '\'' +
                '}';
    }


}

