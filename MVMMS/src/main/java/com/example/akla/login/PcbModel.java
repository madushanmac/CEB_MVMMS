package com.example.akla.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.Serializable;
import java.util.List;

public class PcbModel implements Serializable {


        private static final long serialVersionUID = 1L;

        private PcbEquipment pcbEquipment;
        private PcbSample pcbSample;
        private PcbCondition pcbCondition;
        private PcbLocation pcbLocation;
        private List<PCB_Division> divisionList;
        private List<String> equipmentIdList;

        public PcbEquipment getPcbEquipment() {
            return pcbEquipment;
        }

        public void setPcbEquipment(PcbEquipment pcbEquipment) {
            this.pcbEquipment = pcbEquipment;
        }

        public PcbSample getPcbSample() {
            return pcbSample;
        }

        public void setPcbSample(PcbSample pcbSample) {
            this.pcbSample = pcbSample;
        }

        public PcbCondition getPcbCondition() {
            return pcbCondition;
        }

        public void setPcbCondition(PcbCondition pcbCondition) {
            this.pcbCondition = pcbCondition;
        }

        public PcbLocation getPcbLocation() {
            return pcbLocation;
        }

        public void setPcbLocation(PcbLocation pcbLocation) {
            this.pcbLocation = pcbLocation;
        }

        public List<PCB_Division> getDivisionList() {
            return divisionList;
        }

        public void setDivisionList(List<PCB_Division> divisionList) {
            this.divisionList = divisionList;
        }

        public List<String> getEquipmentIdList() {
            return equipmentIdList;
        }

        public void setEquipmentIdList(List<String> equipmentIdList) {
            this.equipmentIdList = equipmentIdList;
        }


    }

