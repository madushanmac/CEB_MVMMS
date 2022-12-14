package com.example.akla.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.Serializable;

import java.util.Date;

public class MmsAddSample {

    private String equipmentId;

    private String epfNoGroup;

    private String epfNoTestGroup;

    private String extractedTop;

    private Date pcbTestDate;

    private String pcbTestResults;

    private String remarks;

    private Date sampleDate;

    private String sampleSatisified;

    private String samplingNu;

    private String samplingPort;

    private String testRemarks;

    public MmsAddSample() {
    }

    // yata profile kiyala ekak thiye eka aran diyam

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEpfNoGroup() {
        return epfNoGroup;
    }

    public void setEpfNoGroup(String epfNoGroup) {
        this.epfNoGroup = epfNoGroup;
    }

    public String getEpfNoTestGroup() {
        return epfNoTestGroup;
    }

    public void setEpfNoTestGroup(String epfNoTestGroup) {
        this.epfNoTestGroup = epfNoTestGroup;
    }

    public String getExtractedTop() {
        return extractedTop;
    }

    public void setExtractedTop(String extractedTop) {
        this.extractedTop = extractedTop;
    }

    public Date getPcbTestDate() {
        return pcbTestDate;
    }

    public void setPcbTestDate(Date pcbTestDate) {
        this.pcbTestDate = pcbTestDate;
    }

    public String getPcbTestResults() {
        return pcbTestResults;
    }

    public void setPcbTestResults(String pcbTestResults) {
        this.pcbTestResults = pcbTestResults;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getSampleDate() {
        return sampleDate;
    }

    public void setSampleDate(Date sampleDate) {
        this.sampleDate = sampleDate;
    }

    public String getSampleSatisified() {
        return sampleSatisified;
    }

    public void setSampleSatisified(String sampleSatisified) {
        this.sampleSatisified = sampleSatisified;
    }

    public String getSamplingNu() {
        return samplingNu;
    }

    public void setSamplingNu(String samplingNu) {
        this.samplingNu = samplingNu;
    }

    public String getSamplingPort() {
        return samplingPort;
    }

    public void setSamplingPort(String samplingPort) {
        this.samplingPort = samplingPort;
    }

    public String getTestRemarks() {
        return testRemarks;
    }

    public void setTestRemarks(String testRemarks) {
        this.testRemarks = testRemarks;
    }
}
