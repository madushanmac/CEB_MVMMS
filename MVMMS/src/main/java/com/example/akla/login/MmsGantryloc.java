package com.example.akla.login;

import java.math.BigDecimal;
import java.util.Date;

public class MmsGantryloc {

    private MmsGantrylocPK id;
    private String approvedBy;
    private String approvedDate;
    private String approvedTime;
    private String entBy;
    private Date entDate;
    private String entTime;
    private BigDecimal gpsLatitude;
    private BigDecimal gpsLongitude;
    private BigDecimal mapId;
    private BigDecimal status;
    private String validateBy;
    private Date validateDate;
    private String validateTime;

    ///////////// Created on 2019 - 11 - 19

    public MmsGantryloc(){

    }

    public MmsGantrylocPK getId() {
        return id;
    }

    public void setId(MmsGantrylocPK id) {
        this.id = id;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getApprovedTime() {
        return approvedTime;
    }

    public void setApprovedTime(String approvedTime) {
        this.approvedTime = approvedTime;
    }

    public String getEntBy() {
        return entBy;
    }

    public void setEntBy(String entBy) {
        this.entBy = entBy;
    }

    public Date getEntDate() {
        return entDate;
    }

    public void setEntDate(Date entDate) {
        this.entDate = entDate;
    }

    public String getEntTime() {
        return entTime;
    }

    public void setEntTime(String entTime) {
        this.entTime = entTime;
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

    public BigDecimal getMapId() {
        return mapId;
    }

    public void setMapId(BigDecimal mapId) {
        this.mapId = mapId;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public String getValidateBy() {
        return validateBy;
    }

    public void setValidateBy(String validateBy) {
        this.validateBy = validateBy;
    }

    public Date getValidateDate() {
        return validateDate;
    }

    public void setValidateDate(Date validateDate) {
        this.validateDate = validateDate;
    }

    public String getValidateTime() {
        return validateTime;
    }

    public void setValidateTime(String validateTime) {
        this.validateTime = validateTime;
    }

    @Override
    public String toString() {
        return "MmsGantryloc{" +
                "id=" + id +
                ", approvedBy='" + approvedBy + '\'' +
                ", approvedDate='" + approvedDate + '\'' +
                ", approvedTime='" + approvedTime + '\'' +
                ", entBy='" + entBy + '\'' +
                ", entDate=" + entDate +
                ", entTime='" + entTime + '\'' +
                ", gpsLatitude=" + gpsLatitude +
                ", gpsLongitude=" + gpsLongitude +
                ", mapId=" + mapId +
                ", status=" + status +
                ", validateBy='" + validateBy + '\'' +
                ", validateDate=" + validateDate +
                ", validateTime='" + validateTime + '\'' +
                '}';
    }
}
