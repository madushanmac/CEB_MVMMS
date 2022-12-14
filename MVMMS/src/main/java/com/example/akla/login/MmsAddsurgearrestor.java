package com.example.akla.login;

import java.math.BigDecimal;
import java.util.Date;

public class MmsAddsurgearrestor {

    private MmsAddsurgearrestorPK id;
    private String brand;
    private Date dateOfManufacture;
    private BigDecimal quantity;
    private String rating;
    private BigDecimal status;
    private BigDecimal type;

    public MmsAddsurgearrestor() {
    }

    public MmsAddsurgearrestorPK getId() {
        return id;
    }

    public void setId(MmsAddsurgearrestorPK id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Date getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(Date dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public BigDecimal getType() {
        return type;
    }

    public void setType(BigDecimal type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MmsAddsurgearrestor{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", dateOfManufacture=" + dateOfManufacture +
                ", quantity=" + quantity +
                ", rating='" + rating + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
