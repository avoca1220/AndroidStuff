package com.example.mvhs;

public class Classroom {

    public String name;

    private int xCoord;

    private int yCoord;

    public Classroom(String name, int xCoord, int yCoord)
    {
        this.name = name;
        this.xCoord = xCoord;
        this.yCoord = yCoord;

    }

    public String getName()
    {
        return this.name;
    }

    public int getX()
    {
        return this.xCoord;
    }

    public int getY()
    {
        return this.yCoord;
    }
}
