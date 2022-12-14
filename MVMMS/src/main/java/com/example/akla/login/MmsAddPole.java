package com.example.akla.login;

import java.math.BigDecimal;
import java.util.Arrays;

public class MmsAddPole {

    private long id;
    private String approvalLevel;
    private String area;
    private BigDecimal conductorType;
    private String csc;
    private String dpetId;
    private String feederIdentification;
    private String filepath;
    private BigDecimal gpsLatitude;
    private BigDecimal gpsLongitude;
    private byte[] image;
    private BigDecimal lineFeerderId;
    private BigDecimal mapId;
    private BigDecimal noOfConsumers1ph;
    private BigDecimal noOfConsumers3ph;
    private BigDecimal noOfStay;
    private BigDecimal noOfStruts;
    private String poleName;
    private String poleNo;
    private BigDecimal poleType;
    private BigDecimal status;
    private String transformerId;

    public MmsAddPole() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApprovalLevel() {
        return approvalLevel;
    }

    public void setApprovalLevel(String approvalLevel) {
        this.approvalLevel = approvalLevel;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public BigDecimal getConductorType() {
        return conductorType;
    }

    public void setConductorType(BigDecimal conductorType) {
        this.conductorType = conductorType;
    }

    public String getCsc() {
        return csc;
    }

    public void setCsc(String csc) {
        this.csc = csc;
    }

    public String getDpetId() {
        return dpetId;
    }

    public void setDpetId(String dpetId) {
        this.dpetId = dpetId;
    }

    public String getFeederIdentification() {
        return feederIdentification;
    }

    public void setFeederIdentification(String feederIdentification) {
        this.feederIdentification = feederIdentification;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public BigDecimal getLineFeerderId() {
        return lineFeerderId;
    }

    public void setLineFeerderId(BigDecimal lineFeerderId) {
        this.lineFeerderId = lineFeerderId;
    }

    public BigDecimal getMapId() {
        return mapId;
    }

    public void setMapId(BigDecimal mapId) {
        this.mapId = mapId;
    }

    public BigDecimal getNoOfConsumers1ph() {
        return noOfConsumers1ph;
    }

    public void setNoOfConsumers1ph(BigDecimal noOfConsumers1ph) {
        this.noOfConsumers1ph = noOfConsumers1ph;
    }

    public BigDecimal getNoOfConsumers3ph() {
        return noOfConsumers3ph;
    }

    public void setNoOfConsumers3ph(BigDecimal noOfConsumers3ph) {
        this.noOfConsumers3ph = noOfConsumers3ph;
    }

    public BigDecimal getNoOfStay() {
        return noOfStay;
    }

    public void setNoOfStay(BigDecimal noOfStay) {
        this.noOfStay = noOfStay;
    }

    public BigDecimal getNoOfStruts() {
        return noOfStruts;
    }

    public void setNoOfStruts(BigDecimal noOfStruts) {
        this.noOfStruts = noOfStruts;
    }

    public String getPoleName() {
        return poleName;
    }

    public void setPoleName(String poleName) {
        this.poleName = poleName;
    }

    public String getPoleNo() {
        return poleNo;
    }

    public void setPoleNo(String poleNo) {
        this.poleNo = poleNo;
    }

    public BigDecimal getPoleType() {
        return poleType;
    }

    public void setPoleType(BigDecimal poleType) {
        this.poleType = poleType;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public String getTransformerId() {
        return transformerId;
    }

    public void setTransformerId(String transformerId) {
        this.transformerId = transformerId;
    }

    @Override
    public String toString() {
        return "MmsAddPole{" +
                "id=" + id +
                ", approvalLevel='" + approvalLevel + '\'' +
                ", area='" + area + '\'' +
                ", conductorType=" + conductorType +
                ", csc='" + csc + '\'' +
                ", dpetId='" + dpetId + '\'' +
                ", feederIdentification='" + feederIdentification + '\'' +
                ", filepath='" + filepath + '\'' +
                ", gpsLatitude=" + gpsLatitude +
                ", gpsLongitude=" + gpsLongitude +
                ", image=" + Arrays.toString(image) +
                ", lineFeerderId=" + lineFeerderId +
                ", mapId=" + mapId +
                ", noOfConsumers1ph=" + noOfConsumers1ph +
                ", noOfConsumers3ph=" + noOfConsumers3ph +
                ", noOfStay=" + noOfStay +
                ", noOfStruts=" + noOfStruts +
                ", poleName='" + poleName + '\'' +
                ", poleNo='" + poleNo + '\'' +
                ", poleType=" + poleType +
                ", status=" + status +
                ", transformerId=" + transformerId +
                '}';
    }
}
