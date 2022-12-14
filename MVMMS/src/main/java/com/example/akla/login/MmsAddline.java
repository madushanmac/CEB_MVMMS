package com.example.akla.login;

import java.math.BigDecimal;
import java.util.Date;

public class MmsAddline {

    private long id;
    private String area;
    private String code;
    private String feeder2;
    private String sinNo;
    private BigDecimal type;
    private Date comdate;
    private String csc;
    private BigDecimal length;
    private String lineName;
    private String lineType;
    private String feederIdentification;
    private String name;
    private BigDecimal noofpoles;
    private BigDecimal nooftowers;
    private BigDecimal conductorType;
    private BigDecimal circuitType;
    private String approvedBy;
    private Date approvedDate;
    private String approvedTime;
    private String entBy;
    private String entTime;
    private Date entDate;
    private String validateBy;
    private Date validateDate;
    private String validateTime;
    private String phmBranch;
    private BigDecimal status;


    public MmsAddline() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFeeder2() {
        return feeder2;
    }

    public void setFeeder2(String feeder2) {
        this.feeder2 = feeder2;
    }

    public String getSinNo() {
        return sinNo;
    }

    public void setSinNo(String sinNo) {
        this.sinNo = sinNo;
    }

    public BigDecimal getType() {
        return type;
    }

    public void setType(BigDecimal type) {
        this.type = type;
    }

    public Date getComdate() {
        return comdate;
    }

    public void setComdate(Date comdate) {
        this.comdate = comdate;
    }

    public String getCsc() {
        return csc;
    }

    public void setCsc(String csc) {
        this.csc = csc;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getFeederIdentification() {
        return feederIdentification;
    }

    public void setFeederIdentification(String feederIdentification) {
        this.feederIdentification = feederIdentification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getNoofpoles() {
        return noofpoles;
    }

    public void setNoofpoles(BigDecimal noofpoles) {
        this.noofpoles = noofpoles;
    }

    public BigDecimal getNooftowers() {
        return nooftowers;
    }

    public void setNooftowers(BigDecimal nooftowers) {
        this.nooftowers = nooftowers;
    }

    public BigDecimal getConductorType() {
        return conductorType;
    }

    public void setConductorType(BigDecimal conductorType) {
        this.conductorType = conductorType;
    }

    public BigDecimal getCircuitType() {
        return circuitType;
    }

    public void setCircuitType(BigDecimal circuitType) {
        this.circuitType = circuitType;
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

    public String getEntTime() {
        return entTime;
    }

    public void setEntTime(String entTime) {
        this.entTime = entTime;
    }

    public Date getEntDate() {
        return entDate;
    }

    public void setEntDate(Date entDate) {
        this.entDate = entDate;
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

    public String getPhmBranch() {
        return phmBranch;
    }

    public void setPhmBranch(String phmBranch) {
        this.phmBranch = phmBranch;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MmsAddline{" +
                "id=" + id +
                ", area='" + area + '\'' +
                ", code='" + code + '\'' +
                ", feeder2='" + feeder2 + '\'' +
                ", sinNo='" + sinNo + '\'' +
                ", type=" + type +
                ", comdate=" + comdate +
                ", csc='" + csc + '\'' +
                ", length=" + length +
                ", lineName='" + lineName + '\'' +
                ", lineType='" + lineType + '\'' +
                ", feederIdentification='" + feederIdentification + '\'' +
                ", name='" + name + '\'' +
                ", noofpoles=" + noofpoles +
                ", nooftowers=" + nooftowers +
                ", conductorType=" + conductorType +
                ", circuitType=" + circuitType +
                ", approvedBy='" + approvedBy + '\'' +
                ", approvedDate=" + approvedDate +
                ", approvedTime='" + approvedTime + '\'' +
                ", entBy='" + entBy + '\'' +
                ", entTime='" + entTime + '\'' +
                ", entDate=" + entDate +
                ", validateBy='" + validateBy + '\'' +
                ", validateDate=" + validateDate +
                ", validateTime='" + validateTime + '\'' +
                ", phmBranch='" + phmBranch + '\'' +
                ", status=" + status +
                '}';
    }
}
