package com.dabox.dabox;

import java.io.Serializable;

public class Trigger implements Serializable {
    public String name;
    public String description;

    Trigger(String name, String description){
        this.name = name;
        this.description = description;
    }

}