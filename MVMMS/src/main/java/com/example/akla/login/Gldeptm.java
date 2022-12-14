package com.example.akla.login;

import java.math.BigDecimal;

public class Gldeptm {
    private String deptId;
    private String compId;
    private String csPs;
    private String deptNm;
    private String entBy;
    private String entDt;
    private String filter;
    private String logId;
    private String modiBy;
    private String modiDt;
    private BigDecimal status;

    public Gldeptm(){
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getCsPs() {
        return csPs;
    }

    public void setCsPs(String csPs) {
        this.csPs = csPs;
    }

    public String getDeptNm() {
        return deptNm;
    }

    public void setDeptNm(String deptNm) {
        this.deptNm = deptNm;
    }

    public String getEntBy() {
        return entBy;
    }

    public void setEntBy(String entBy) {
        this.entBy = entBy;
    }

    public String getEntDt() {
        return entDt;
    }

    public void setEntDt(String entDt) {
        this.entDt = entDt;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getModiBy() {
        return modiBy;
    }

    public void setModiBy(String modiBy) {
        this.modiBy = modiBy;
    }

    public String getModiDt() {
        return modiDt;
    }

    public void setModiDt(String modiDt) {
        this.modiDt = modiDt;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Gldeptm{" +
                "deptId='" + deptId + '\'' +
                ", compId='" + compId + '\'' +
                ", csPs='" + csPs + '\'' +
                ", deptNm='" + deptNm + '\'' +
                ", entBy='" + entBy + '\'' +
                ", entDt='" + entDt + '\'' +
                ", filter='" + filter + '\'' +
                ", logId='" + logId + '\'' +
                ", modiBy='" + modiBy + '\'' +
                ", modiDt='" + modiDt + '\'' +
                ", status=" + status +
                '}';
    }
}
