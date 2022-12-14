package com.example.akla.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.Serializable;

public class PcbCondition  {

    /**
     * The persistent class for the PCB_CONDITION database table.
     *
     */
        private static final long serialVersionUID = 1L;


        private String equipmentId;

        private String breatherIsGoodConnection;

        private String burnMask;

        private String corrosion;

        private String earthConnection;

        private String lightingArrestersDone;

        private String oilLeaksPresent;

        private String overloadPresent;

        private String terminalAttention;

        public PcbCondition() {
        }

        public String getEquipmentId() {
            return this.equipmentId;
        }

        public void setEquipmentId(String equipmentId) {
            this.equipmentId = equipmentId;
        }

        public String getBreatherIsGoodConnection() {
            return this.breatherIsGoodConnection;
        }

        public void setBreatherIsGoodConnection(String breatherIsGoodConnection) {
            this.breatherIsGoodConnection = breatherIsGoodConnection;
        }

        public String getBurnMask() {
            return this.burnMask;
        }

        public void setBurnMask(String burnMask) {
            this.burnMask = burnMask;
        }

        public String getCorrosion() {
            return this.corrosion;
        }

        public void setCorrosion(String corrosion) {
            this.corrosion = corrosion;
        }

        public String getEarthConnection() {
            return this.earthConnection;
        }

        public void setEarthConnection(String earthConnection) {
            this.earthConnection = earthConnection;
        }

        public String getLightingArrestersDone() {
            return this.lightingArrestersDone;
        }

        public void setLightingArrestersDone(String lightingArrestersDone) {
            this.lightingArrestersDone = lightingArrestersDone;
        }

        public String getOilLeaksPresent() {
            return this.oilLeaksPresent;
        }

        public void setOilLeaksPresent(String oilLeaksPresent) {
            this.oilLeaksPresent = oilLeaksPresent;
        }

        public String getOverloadPresent() {
            return this.overloadPresent;
        }

        public void setOverloadPresent(String overloadPresent) {
            this.overloadPresent = overloadPresent;
        }

        public String getTerminalAttention() {
            return this.terminalAttention;
        }

        public void setTerminalAttention(String terminalAttention) {
            this.terminalAttention = terminalAttention;
        }

    }

