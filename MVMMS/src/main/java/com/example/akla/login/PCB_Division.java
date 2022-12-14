package com.example.akla.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.Serializable;

public class PCB_Division{
    private static final long serialVersionUID = 1L;


    private String id;

    private String name;

    public PCB_Division() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "PCB_Division [id=" + id + ", name=" + name + "]";
    }



}


