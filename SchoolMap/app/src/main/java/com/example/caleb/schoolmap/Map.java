package com.example.caleb.schoolmap;

public abstract class Map {

    private static int x;
    private static int y;

    public static int getX()
    {
        return x;
    }

    public static int getY()
    {
        return y;
    }

    public static void setX(int val)
    {
        x = val;
    }

    public static void setY(int val)
    {
        y = val;
    }
}
