package com.example.akla.login;

import java.math.BigDecimal;
import java.util.Date;

public class MmsAddgantrytranformer {

    private MmsAddgantrytranformerPK id;
    private String brand;
    private Date dateOfCommissioning;
    private Date dateOfManufacture;
    private String kva;
    private String model;
    private String serialNo;
    private BigDecimal status;
    private String voltageRatio;

    public MmsAddgantrytranformer() {
    }

    public MmsAddgantrytranformerPK getId() {
        return id;
    }

    public void setId(MmsAddgantrytranformerPK id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Date getDateOfCommissioning() {
        return dateOfCommissioning;
    }

    public void setDateOfCommissioning(Date dateOfCommissioning) {
        this.dateOfCommissioning = dateOfCommissioning;
    }

    public Date getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(Date dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
    }

    public String getKva() {
        return kva;
    }

    public void setKva(String kva) {
        this.kva = kva;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
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

    public String getVoltageRatio() {
        return voltageRatio;
    }

    public void setVoltageRatio(String voltageRatio) {
        this.voltageRatio = voltageRatio;
    }

    @Override
    public String toString() {
        return "MmsAddgantrytranformer{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", dateOfCommissioning=" + dateOfCommissioning +
                ", dateOfManufacture=" + dateOfManufacture +
                ", kva='" + kva + '\'' +
                ", model='" + model + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", status=" + status +
                ", voltageRatio='" + voltageRatio + '\'' +
                '}';
    }
}
