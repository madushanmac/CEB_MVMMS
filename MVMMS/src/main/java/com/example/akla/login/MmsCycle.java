package com.example.akla.login;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

public class MmsCycle {

    private LinkedHashMap id;
    private String cycleName;
    private BigDecimal cycleOrder;
    private BigDecimal status;

    public LinkedHashMap getId() {
        return id;
    }

    public void setId(LinkedHashMap id) {
        this.id = id;
    }

    public String getCycleName() {
        return cycleName;
    }

    public void setCycleName(String cycleName) {
        this.cycleName = cycleName;
    }

    public BigDecimal getCycleOrder() {
        return cycleOrder;
    }

    public void setCycleOrder(BigDecimal cycleOrder) {
        this.cycleOrder = cycleOrder;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }
}
