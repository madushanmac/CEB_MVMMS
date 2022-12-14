package com.example.akla.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.Serializable;

public class MmsAddCondition  {

    private String equipmentId;

    private String breatherIsGoodConnection;

    private String burnMask;

    private String corrosion;

    private String earthConnection;

    private String lightingArrestersDone;

    private String oilLeaksPresent;

    private String overloadPresent;

    private String terminalAttention;
    public MmsAddCondition() {
    }
    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getBreatherIsGoodConnection() {
        return breatherIsGoodConnection;
    }

    public void setBreatherIsGoodConnection(String breatherIsGoodConnection) {
        this.breatherIsGoodConnection = breatherIsGoodConnection;
    }

    public String getBurnMask() {
        return burnMask;
    }

    public void setBurnMask(String burnMask) {
        this.burnMask = burnMask;
    }

    public String getCorrosion() {
        return corrosion;
    }

    public void setCorrosion(String corrosion) {
        this.corrosion = corrosion;
    }

    public String getEarthConnection() {
        return earthConnection;
    }

    public void setEarthConnection(String earthConnection) {
        this.earthConnection = earthConnection;
    }

    public String getLightingArrestersDone() {
        return lightingArrestersDone;
    }

    public void setLightingArrestersDone(String lightingArrestersDone) {
        this.lightingArrestersDone = lightingArrestersDone;
    }

    public String getOilLeaksPresent() {
        return oilLeaksPresent;
    }

    public void setOilLeaksPresent(String oilLeaksPresent) {
        this.oilLeaksPresent = oilLeaksPresent;
    }

    public String getOverloadPresent() {
        return overloadPresent;
    }

    public void setOverloadPresent(String overloadPresent) {
        this.overloadPresent = overloadPresent;
    }

    public String getTerminalAttention() {
        return terminalAttention;
    }

    public void setTerminalAttention(String terminalAttention) {
        this.terminalAttention = terminalAttention;
    }
}
