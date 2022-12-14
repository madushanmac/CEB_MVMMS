package com.example.akla.login;

import java.math.BigDecimal;
import java.util.Date;

public class MmsAddtransformer {

    private MmsAddtransformerPK id;
    private BigDecimal brandName;
    private Date dateOfCommissioning;
    private Date dateOfManufacturing;
    private BigDecimal kva;
    private BigDecimal model;
    private String serialNo;
    private BigDecimal status;
    private BigDecimal voltageRatio;

    public MmsAddtransformer() {
    }

    public MmsAddtransformerPK getId() {
        return id;
    }

    public void setId(MmsAddtransformerPK id) {
        this.id = id;
    }

    public BigDecimal getBrandName() {
        return brandName;
    }

    public void setBrandName(BigDecimal brandName) {
        this.brandName = brandName;
    }

    public Date getDateOfCommissioning() {
        return dateOfCommissioning;
    }

    public void setDateOfCommissioning(Date dateOfCommissioning) {
        this.dateOfCommissioning = dateOfCommissioning;
    }

    public Date getDateOfManufacturing() {
        return dateOfManufacturing;
    }

    public void setDateOfManufacturing(Date dateOfManufacturing) {
        this.dateOfManufacturing = dateOfManufacturing;
    }

    public BigDecimal getKva() {
        return kva;
    }

    public void setKva(BigDecimal kva) {
        this.kva = kva;
    }

    public BigDecimal getModel() {
        return model;
    }

    public void setModel(BigDecimal model) {
        this.model = model;
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

    public BigDecimal getVoltageRatio() {
        return voltageRatio;
    }

    public void setVoltageRatio(BigDecimal voltageRatio) {
        this.voltageRatio = voltageRatio;
    }

    @Override
    public String toString() {
        return "MmsAddtransformer{" +
                "id=" + id +
                ", brandName=" + brandName +
                ", dateOfCommissioning=" + dateOfCommissioning +
                ", dateOfManufacturing=" + dateOfManufacturing +
                ", kva=" + kva +
                ", model=" + model +
                ", serialNo='" + serialNo + '\'' +
                ", status=" + status +
                ", voltageRatio=" + voltageRatio +
                '}';
    }
}

