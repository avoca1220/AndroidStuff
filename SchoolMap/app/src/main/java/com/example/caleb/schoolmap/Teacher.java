package com.example.caleb.schoolmap;

import java.util.ArrayList;

public class Teacher
{
    private String name;
    private ArrayList<Room> rooms = new ArrayList<Room>();

    public Teacher(String name)
    {
        this.name = name;
    }

    public boolean multipleRooms()
    {
        return (rooms.size() > 1);
    }

    public String getName()
    {
        return this.name;
    }

    public ArrayList<Room> getRooms()
    {
        return rooms;
    }

    public void addRoom(Room room)
    {
        rooms.add(room);
    }



}
