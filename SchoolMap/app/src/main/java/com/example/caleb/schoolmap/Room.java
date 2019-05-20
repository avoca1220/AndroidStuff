package com.example.caleb.schoolmap;

import java.util.ArrayList;

public class Room
{
    private String name;
    private ArrayList<Teacher> teachers = new ArrayList<Teacher>();

    public Room(String name)
    {
        this.name = name;
    }

    public boolean multipleTeachers()
    {
        return (teachers.size() > 1);
    }

    public String getName()
    {
        return this.name;
    }

    public ArrayList<Teacher> getTeachers()
    {
        return teachers;
    }

    public void addTeacher(Teacher teacher)
    {
        teachers.add(teacher);
    }






}
