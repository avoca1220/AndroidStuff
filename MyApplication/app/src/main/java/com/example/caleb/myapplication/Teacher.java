package com.example.caleb.myapplication;

public class Teacher {
    private String name;
    private String room;

    public Teacher(String name, String room)
    {
        this.name = name;
        this.room = room;
    }

    public String getName()
    {
        return this.name;
    }

    public String getRoom()
    {
        return this.room;
    }
}
