package com.dabox.dabox;

import java.io.Serializable;

public class Trigger implements Serializable {
    // TODO: 2016. 03. 04. Add setter/getter functions  

    public final String name;
    public final String description;

    Trigger(String name, String description){
        this.name = name;
        this.description = description;
    }

}
