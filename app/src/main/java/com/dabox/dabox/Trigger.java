package com.dabox.dabox;

import java.io.Serializable;

public class Trigger implements Serializable {
    // TODO: 2016. 03. 04. Add setter/getter functions  

    public final String name;
    public final String description;
    public boolean state;

    Trigger(String name, String description, boolean state){
        this.name = name;
        this.description = description;
        this.state = state;

    }

}
