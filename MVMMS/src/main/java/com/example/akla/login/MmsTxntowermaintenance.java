package com.example.akla.login;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MmsTxntowermaintenance implements Serializable {

    private static final long serialVersionUID = 1L;

    private MmsTxntowermaintenancePK id;

    public BigDecimal getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(BigDecimal completionStatus) {
        this.completionStatus = completionStatus;
    }

    private BigDecimal completionStatus;


    public String getCompletionRemarks() {
        return completionRemarks;
    }

    public void setCompletionRemarks(String completionRemarks) {
        this.completionRemarks = completionRemarks;
    }

    private String completionRemarks;

    private String anticlimbingstatus;

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    private Date completionDate;

    private String approvalLevel;

    private String attentionstatus;

    private String baseconcretestatus;

    private String comments;

    private String conductorstatus;

    private String earthconductorstatus;

    private BigDecimal endfittingset;

    private String flashoversetno;

    private BigDecimal fungussetno;

    private String hotLineMnt;

    private BigDecimal hotpossible;

    private Date insDate;

    private String jumperstatus;

    private String legPainting;

    private Date ludt;

    private Date maintenancedate;

    private BigDecimal nofflashoversets;

    private BigDecimal noofmissingparts;

    private BigDecimal nooftappings;

    private BigDecimal pinpole1;

    private BigDecimal pinpole2;

    private BigDecimal pinpole3;

    private BigDecimal status;

    private String switchdev1;

    private String switchdev2;

    private String switchdev3;

    private String towerspecial;

    private String wayleavestatus;

    private BigDecimal wpinset;

    private String cycle;

    private String approvedBy;

    private Date approvedDate;

    private String approvedTime;

    private String entBy;

    private String phmBranch;

    private String validateBy;

    private Date validateDate;

    private String validateTime;

    private String insTime;





    public MmsTxntowermaintenance() {
    }

    public MmsTxntowermaintenancePK getId() {
        return this.id;
    }

    public void setId(MmsTxntowermaintenancePK id) {
        this.id = id;
    }

    public String getApprovedBy() {
        return this.approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDate() {
        return this.approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getApprovedTime() {
        return this.approvedTime;
    }

    public void setApprovedTime(String approvedTime) {
        this.approvedTime = approvedTime;
    }
    public String getEntBy() {
        return this.entBy;
    }

    public void setEntBy(String entBy) {
        this.entBy = entBy;
    }
    public String getInsTime() {
        return this.insTime;
    }

    public void setInsTime(String insTime) {
        this.insTime = insTime;
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




    public String getPhmBranch() {
        return this.phmBranch;
    }

    public void setPhmBranch(String phmBranch) {
        this.phmBranch = phmBranch;
    }



    public String getAnticlimbingstatus() {
        return this.anticlimbingstatus;
    }

    public void setAnticlimbingstatus(String anticlimbingstatus) {
        this.anticlimbingstatus = anticlimbingstatus;
    }

    public String getApprovalLevel() {
        return this.approvalLevel;
    }

    public void setApprovalLevel(String approvalLevel) {
        this.approvalLevel = approvalLevel;
    }

    public String getAttentionstatus() {
        return this.attentionstatus;
    }

    public void setAttentionstatus(String attentionstatus) {
        this.attentionstatus = attentionstatus;
    }

    public String getBaseconcretestatus() {
        return this.baseconcretestatus;
    }

    public void setBaseconcretestatus(String baseconcretestatus) {
        this.baseconcretestatus = baseconcretestatus;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getConductorstatus() {
        return this.conductorstatus;
    }

    public void setConductorstatus(String conductorstatus) {
        this.conductorstatus = conductorstatus;
    }

    public String getEarthconductorstatus() {
        return this.earthconductorstatus;
    }

    public void setEarthconductorstatus(String earthconductorstatus) {
        this.earthconductorstatus = earthconductorstatus;
    }

    public BigDecimal getEndfittingset() {
        return this.endfittingset;
    }

    public void setEndfittingset(BigDecimal endfittingset) {
        this.endfittingset = endfittingset;
    }

    public String getFlashoversetno() {
        return this.flashoversetno;
    }

    public void setFlashoversetno(String flashoversetno) {
        this.flashoversetno = flashoversetno;
    }

    public BigDecimal getFungussetno() {
        return this.fungussetno;
    }

    public void setFungussetno(BigDecimal fungussetno) {
        this.fungussetno = fungussetno;
    }

    public String getHotLineMnt() {
        return this.hotLineMnt;
    }

    public void setHotLineMnt(String hotLineMnt) {
        this.hotLineMnt = hotLineMnt;
    }

    public BigDecimal getHotpossible() {
        return this.hotpossible;
    }

    public void setHotpossible(BigDecimal hotpossible) {
        this.hotpossible = hotpossible;
    }

    public Date getInsDate() {
        return this.insDate;
    }

    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    public String getJumperstatus() {
        return this.jumperstatus;
    }

    public void setJumperstatus(String jumperstatus) {
        this.jumperstatus = jumperstatus;
    }

    public String getLegPainting() {
        return this.legPainting;
    }

    public void setLegPainting(String legPainting) {
        this.legPainting = legPainting;
    }

    public Date getLudt() {
        return this.ludt;
    }

    public void setLudt(Date ludt) {
        this.ludt = ludt;
    }

    public Date getMaintenancedate() {
        return this.maintenancedate;
    }

    public void setMaintenancedate(Date maintenancedate) {
        this.maintenancedate = maintenancedate;
    }

    public BigDecimal getNofflashoversets() {
        return this.nofflashoversets;
    }

    public void setNofflashoversets(BigDecimal nofflashoversets) {
        this.nofflashoversets = nofflashoversets;
    }

    public BigDecimal getNoofmissingparts() {
        return this.noofmissingparts;
    }

    public void setNoofmissingparts(BigDecimal noofmissingparts) {
        this.noofmissingparts = noofmissingparts;
    }

    public BigDecimal getNooftappings() {
        return this.nooftappings;
    }

    public void setNooftappings(BigDecimal nooftappings) {
        this.nooftappings = nooftappings;
    }

    public BigDecimal getPinpole1() {
        return this.pinpole1;
    }

    public void setPinpole1(BigDecimal pinpole1) {
        this.pinpole1 = pinpole1;
    }

    public BigDecimal getPinpole2() {
        return this.pinpole2;
    }

    public void setPinpole2(BigDecimal pinpole2) {
        this.pinpole2 = pinpole2;
    }

    public BigDecimal getPinpole3() {
        return this.pinpole3;
    }

    public void setPinpole3(BigDecimal pinpole3) {
        this.pinpole3 = pinpole3;
    }

    public BigDecimal getStatus() {
        return this.status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public String getSwitchdev1() {
        return this.switchdev1;
    }

    public void setSwitchdev1(String switchdev1) {
        this.switchdev1 = switchdev1;
    }

    public String getSwitchdev2() {
        return this.switchdev2;
    }

    public void setSwitchdev2(String switchdev2) {
        this.switchdev2 = switchdev2;
    }

    public String getSwitchdev3() {
        return this.switchdev3;
    }

    public void setSwitchdev3(String switchdev3) {
        this.switchdev3 = switchdev3;
    }

    public String getTowerspecial() {
        return this.towerspecial;
    }

    public void setTowerspecial(String towerspecial) {
        this.towerspecial = towerspecial;
    }

    public String getWayleavestatus() {
        return this.wayleavestatus;
    }

    public void setWayleavestatus(String wayleavestatus) {
        this.wayleavestatus = wayleavestatus;
    }

    public BigDecimal getWpinset() {
        return this.wpinset;
    }

    public void setWpinset(BigDecimal wpinset) {
        this.wpinset = wpinset;
    }

    public String getCycle() {
        return this.cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    @Override
    public String toString() {
        return "MmsTxntowermaintenance{" +
                "id=" + id +
                ", completionStatus=" + completionStatus +
                ", completionRemarks='" + completionRemarks + '\'' +
                ", anticlimbingstatus='" + anticlimbingstatus + '\'' +
                ", completionDate=" + completionDate +
                ", approvalLevel='" + approvalLevel + '\'' +
                ", attentionstatus='" + attentionstatus + '\'' +
                ", baseconcretestatus='" + baseconcretestatus + '\'' +
                ", comments='" + comments + '\'' +
                ", conductorstatus='" + conductorstatus + '\'' +
                ", earthconductorstatus='" + earthconductorstatus + '\'' +
                ", endfittingset=" + endfittingset +
                ", flashoversetno='" + flashoversetno + '\'' +
                ", fungussetno=" + fungussetno +
                ", hotLineMnt='" + hotLineMnt + '\'' +
                ", hotpossible=" + hotpossible +
                ", insDate=" + insDate +
                ", jumperstatus='" + jumperstatus + '\'' +
                ", legPainting='" + legPainting + '\'' +
                ", ludt=" + ludt +
                ", maintenancedate=" + maintenancedate +
                ", nofflashoversets=" + nofflashoversets +
                ", noofmissingparts=" + noofmissingparts +
                ", nooftappings=" + nooftappings +
                ", pinpole1=" + pinpole1 +
                ", pinpole2=" + pinpole2 +
                ", pinpole3=" + pinpole3 +
                ", status=" + status +
                ", switchdev1='" + switchdev1 + '\'' +
                ", switchdev2='" + switchdev2 + '\'' +
                ", switchdev3='" + switchdev3 + '\'' +
                ", towerspecial='" + towerspecial + '\'' +
                ", wayleavestatus='" + wayleavestatus + '\'' +
                ", wpinset=" + wpinset +
                ", cycle='" + cycle + '\'' +
                ", approvedBy='" + approvedBy + '\'' +
                ", approvedDate=" + approvedDate +
                ", approvedTime='" + approvedTime + '\'' +
                ", entBy='" + entBy + '\'' +
                ", phmBranch='" + phmBranch + '\'' +
                ", validateBy='" + validateBy + '\'' +
                ", validateDate=" + validateDate +
                ", validateTime='" + validateTime + '\'' +
                ", insTime='" + insTime + '\'' +
                '}';
    }

    /*@Override
    public String toString() {
        return "MmsTxntowermaintenance [id=" + id + ", anticlimbingstatus="
                + anticlimbingstatus + ", approvalLevel=" + approvalLevel
                + ", attentionstatus=" + attentionstatus
                + ", baseconcretestatus=" + baseconcretestatus + ", comments="
                + comments + ", conductorstatus=" + conductorstatus
                + ", earthconductorstatus=" + earthconductorstatus
                + ", endfittingset=" + endfittingset + ", flashoversetno="
                + flashoversetno + ", fungussetno=" + fungussetno
                + ", hotLineMnt=" + hotLineMnt + ", hotpossible=" + hotpossible
                + ", insDate=" + insDate + ", jumperstatus=" + jumperstatus
                + ", legPainting=" + legPainting + ", ludt=" + ludt
                + ", maintenancedate=" + maintenancedate
                + ", nofflashoversets=" + nofflashoversets
                + ", noofmissingparts=" + noofmissingparts + ", nooftappings="
                + nooftappings + ", pinpole1=" + pinpole1 + ", pinpole2="
                + pinpole2 + ", pinpole3=" + pinpole3 + ", status=" + status
                + ", switchdev1=" + switchdev1 + ", switchdev2=" + switchdev2
                + ", switchdev3=" + switchdev3 + ", towerspecial="
                + towerspecial + ", wayleavestatus=" + wayleavestatus
                + ", wpinset=" + wpinset + ", cycle=" + cycle + ", approvedBy="
                + approvedBy + ", approvedDate=" + approvedDate
                + ", approvedTime=" + approvedTime + ", entBy=" + entBy
                + ", phmBranch=" + phmBranch + ", validateBy=" + validateBy
                + ", validateDate=" + validateDate + ", validateTime="
                + validateTime + ", insTime=" + insTime + "]";
    }
*/
    /*private static final long serialVersionUID = 1L;

    private MmsTxntowermaintenancePK id;

    private String anticlimbingstatus;

    private String attentionstatus;

    private String baseconcretestatus;

    private String comments;

    private String conductorstatus;

    private String earthconductorstatus;

    private BigDecimal endfittingset;

    private String flashoversetno;

    private BigDecimal fungussetno;

    private String hotLineMnt;

    private BigDecimal hotpossible;

    private  String TowerNo;

    private String insDate;

    private String jumperstatus;

    private String legPainting;

    private Date ludt;

    private Date maintenancedate;

    private BigDecimal nofflashoversets;

    private BigDecimal noofmissingparts;

    private BigDecimal nooftappings;

    private BigDecimal pinpole1;

    private BigDecimal pinpole2;

    private BigDecimal pinpole3;

    private String switchdev1;

    private String switchdev2;

    private String switchdev3;

    private String towerspecial;

    private String wayleavestatus;

    private BigDecimal wpinset;

    private BigDecimal status;

    private String approvalLevel;

    private String cycle;

    private String approvedBy;
    private String approvedDate;
    private String approvedTime;
    private String entBy;
    private String validateBy;
    private String validateDate;
    private String validateTime;
    private String insTime;


    public MmsTxntowermaintenance() {
    }

    public String getCycle() {
        return this.cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getApprovalLevel() {
        return this.approvalLevel;
    }

    public void setApprovalLevel(String approvalLevel) {
        this.approvalLevel = approvalLevel;
    }

    public BigDecimal getStatus() {
        return this.status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public MmsTxntowermaintenancePK getId() {
        return this.id;
    }

    public void setId(MmsTxntowermaintenancePK id) {
        this.id = id;
    }

    public String getAnticlimbingstatus() {
        return this.anticlimbingstatus;
    }

    public void setAnticlimbingstatus(String anticlimbingstatus) {
        this.anticlimbingstatus = anticlimbingstatus;
    }

    public String getAttentionstatus() {
        return this.attentionstatus;
    }

    public void setAttentionstatus(String attentionstatus) {
        this.attentionstatus = attentionstatus;
    }

    public String getBaseconcretestatus() {
        return this.baseconcretestatus;
    }

    public void setBaseconcretestatus(String baseconcretestatus) {
        this.baseconcretestatus = baseconcretestatus;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getConductorstatus() {
        return this.conductorstatus;
    }

    public void setConductorstatus(String conductorstatus) {
        this.conductorstatus = conductorstatus;
    }

    public String getEarthconductorstatus() {
        return this.earthconductorstatus;
    }

    public void setEarthconductorstatus(String earthconductorstatus) {
        this.earthconductorstatus = earthconductorstatus;
    }

    public BigDecimal getEndfittingset() {
        return this.endfittingset;
    }

    public void setEndfittingset(BigDecimal endfittingset) {
        this.endfittingset = endfittingset;
    }

    public String getFlashoversetno() {
        return this.flashoversetno;
    }

    public void setFlashoversetno(String flashoversetno) {
        this.flashoversetno = flashoversetno;
    }

    public BigDecimal getFungussetno (){return this.fungussetno; }

    public void setFungussetno(BigDecimal fungussetno) {
        this.fungussetno = fungussetno;
    }

    public String getHotLineMnt() {
        return this.hotLineMnt;
    }

    public void setHotLineMnt(String hotLineMnt) {
        this.hotLineMnt = hotLineMnt;
    }

    public BigDecimal getHotpossible() {
        return this.hotpossible;
    }

    public void setHotpossible(BigDecimal hotpossible) {
        this.hotpossible = hotpossible;
    }

    public String getInsDate() {
        return this.insDate;
    }

    public void setInsDate(String insDate) {
        this.insDate = insDate;
    }

    public String getJumperstatus() {
        return this.jumperstatus;
    }

    public void setJumperstatus(String jumperstatus) {
        this.jumperstatus = jumperstatus;
    }

    public String getLegPainting() {
        return this.legPainting;
    }

    public void setLegPainting(String legPainting) {
        this.legPainting = legPainting;
    }

    public Date getLudt() {
        return this.ludt;
    }

    public void setLudt(Date ludt) {
        this.ludt = ludt;
    }

    public Date getMaintenancedate() {
        return this.maintenancedate;
    }

    public void setMaintenancedate(Date maintenancedate) {
        this.maintenancedate = maintenancedate;
    }

    public BigDecimal getNofflashoversets() {
        return this.nofflashoversets;
    }

    public void setNofflashoversets(BigDecimal nofflashoversets) {
        this.nofflashoversets = nofflashoversets;
    }

    public BigDecimal getNoofmissingparts() {
        return this.noofmissingparts;
    }

    public void setNoofmissingparts(BigDecimal noofmissingparts) {
        this.noofmissingparts = noofmissingparts;
    }

    public BigDecimal getNooftappings() {
        return this.nooftappings;
    }

    public void setNooftappings(BigDecimal nooftappings) {
        this.nooftappings = nooftappings;
    }

    public BigDecimal getPinpole1() {
        return this.pinpole1;
    }

    public void setPinpole1(BigDecimal pinpole1) {
        this.pinpole1 = pinpole1;
    }

    public BigDecimal getPinpole2() {
        return this.pinpole2;
    }

    public void setPinpole2(BigDecimal pinpole2) {
        this.pinpole2 = pinpole2;
    }

    public String getSwitchdev1() {
        return this.switchdev1;
    }

    public void setSwitchdev1(String switchdev1) {
        this.switchdev1 = switchdev1;
    }

    public String getSwitchdev2() {
        return this.switchdev2;
    }

    public void setSwitchdev2(String switchdev2) {
        this.switchdev2 = switchdev2;
    }

    public String getTowerspecial() {
        return this.towerspecial;
    }

    public void setTowerspecial(String towerspecial) {
        this.towerspecial = towerspecial;
    }

    public String getWayleavestatus() {
        return this.wayleavestatus;
    }

    public void setWayleavestatus(String wayleavestatus) {
        this.wayleavestatus = wayleavestatus;
    }

    public BigDecimal getWpinset() {
        return this.wpinset;
    }

    public void setWpinset(BigDecimal wpinset) {
        this.wpinset = wpinset;
    }

    public String getSwitchdev3() { return switchdev3; }

    public void setSwitchdev3(String switchdev3) {
        this.switchdev3 = switchdev3;
    }

    public BigDecimal getPinpole3() {
        return pinpole3;
    }

    public void setPinpole3(BigDecimal pinpole3) {
        this.pinpole3 = pinpole3;
    }

    public String getTowerNo() { return TowerNo; }

    public void setTowerNo(String towerNo) { this.TowerNo = towerNo; }



    public String getApprovedBy() {
        return this.approvedBy;
    }
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
    public String getApprovedDate() {
        return this.approvedDate;
    }
    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }
    public String getApprovedTime() {
        return this.approvedTime;
    }
    public void setApprovedTime(String approvedTime) {
        this.approvedTime = approvedTime;
    }
    public String getEntBy() {
        return this.entBy;
    }
    public void setEntBy(String entBy) {
        this.entBy = entBy;
    }
    public String getInsTime() {
        return this.insTime;
    }
    public void setInsTime(String insTime) {
        this.insTime = insTime;
    }
    public String getValidateBy() {
        return this.validateBy;
    }
    public void setValidateBy(String validateBy) {
        this.validateBy = validateBy;
    }
    public String getValidateDate() {
        return this.validateDate;
    }
    public void setValidateDate(String validateDate) {
        this.validateDate = validateDate;
    }
    public String getValidateTime() {
        return this.validateTime;
    }
    public void setValidateTime(String validateTime) {
        this.validateTime = validateTime;
    }

 *//*   public String toString() {
        return "MmsTxntowermaintenance [id=" + id + ", anticlimbingstatus="
                + anticlimbingstatus + ", attentionstatus=" + attentionstatus
                + ", baseconcretestatus=" + baseconcretestatus + ", comments="
                + comments + ", conductorstatus=" + conductorstatus
                + ", earthconductorstatus=" + earthconductorstatus
                + ", endfittingset=" + endfittingset + ", flashoversetno="
                + flashoversetno + ", fungussetno=" + fungussetno
                + ", hotLineMnt=" + hotLineMnt + ", hotpossible=" + hotpossible
                + ", insDate=" + insDate + ", jumperstatus=" + jumperstatus
                + ", legPainting=" + legPainting + ", ludt=" + ludt
                + ", maintenancedate=" + maintenancedate
                + ", nofflashoversets=" + nofflashoversets
                + ", noofmissingparts=" + noofmissingparts + ", nooftappings="
                + nooftappings + ", pinpole1=" + pinpole1 + ", pinpole2="
                + pinpole2 + ", switchdev1=" + switchdev1 + ", switchdev2="
                + switchdev2 + ", towerspecial=" + towerspecial
                + ", wayleavestatus=" + wayleavestatus + ", wpinset=" + wpinset
                + "]";
    }*//*

    @Override
    public String toString() {
        return "MmsTxntowermaintenance [id=" + id + ", anticlimbingstatus="
                + anticlimbingstatus + ", approvalLevel=" + approvalLevel
                + ", attentionstatus=" + attentionstatus
                + ", baseconcretestatus=" + baseconcretestatus + ", comments="
                + comments + ", conductorstatus=" + conductorstatus
                + ", earthconductorstatus=" + earthconductorstatus
                + ", endfittingset=" + endfittingset + ", flashoversetno="
                + flashoversetno + ", fungussetno=" + fungussetno
                + ", hotLineMnt=" + hotLineMnt + ", hotpossible=" + hotpossible
                + ", insDate=" + insDate + ", jumperstatus=" + jumperstatus
                + ", legPainting=" + legPainting + ", ludt=" + ludt
                + ", maintenancedate=" + maintenancedate
                + ", nofflashoversets=" + nofflashoversets
                + ", noofmissingparts=" + noofmissingparts + ", nooftappings="
                + nooftappings + ", pinpole1=" + pinpole1 + ", pinpole2="
                + pinpole2 + ", pinpole3=" + pinpole3 + ", status=" + status
                + ", switchdev1=" + switchdev1 + ", switchdev2=" + switchdev2
                + ", switchdev3=" + switchdev3 + ", towerspecial="
                + towerspecial + ", wayleavestatus=" + wayleavestatus
                + ", wpinset=" + wpinset + ", cycle=" + cycle + ", approvedBy=" + approvedBy
                + ",approvedDate=" + approvedDate + ", approvedTime=" + approvedTime
                + "entBy=" + entBy + ", validateBy=" + validateBy + ", validateDate=" + validateDate
                + "validateTime=" + validateTime + ", insTime=" + insTime + "]";
    }*/


}