package com.example.akla.login;

import java.math.BigDecimal;

public class MmsAddlvfeeder {

    private MmsAddlvfeederPK id;

    private String area;

    private String code;

    private String name;

    private BigDecimal status;

    public MmsAddlvfeeder() {
    }

    public MmsAddlvfeederPK getId() {
        return id;
    }

    public void setId(MmsAddlvfeederPK id) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "com.example.akla.login.MmsAddlvfeeder{" +
                "id=" + id +
                ", area='" + area + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}

