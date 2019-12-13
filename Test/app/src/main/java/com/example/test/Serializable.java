package com.example.test;

import org.simpleframework.xml.*;

@Root
public class Serializable {

    @Element
    private String text;

    @Attribute
    private int index;

    public Serializable() {
        super();
    }

    public Serializable(String text, int index) {
        this.text = text;
        this.index = index;
    }

    public String getMessage() {
        return text;
    }

    public int getId() {
        return index;
    }


}
