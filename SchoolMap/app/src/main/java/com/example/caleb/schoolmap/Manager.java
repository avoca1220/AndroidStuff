package com.example.caleb.schoolmap;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Manager
{
    private ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    private ArrayList<Room> rooms = new ArrayList<Room>();

    public Manager()
    {

    }


    public Teacher findMatchingTeacher(Room room, int index)
    {
        for(Teacher teacher : teachers)
        {
            if (teacher.getName().equals(room.getTeachers().get(index).getName()))
            {
                return teacher;
            }
        }

        //If there is no match
        throw new NoSuchElementException();
    }

    public Room findMatchingRoom(Teacher teacher, int index)
    {
        for(Room room : rooms)
        {
            if (room.getName().equals(teacher.getRooms().get(index).getName()))
            {
                return room;
            }
        }

        //If there is no match
        throw new NoSuchElementException();
    }

    public void addTeacher()
    {
        for(Teacher teacher : teachers)
        {

        }
    }

}
