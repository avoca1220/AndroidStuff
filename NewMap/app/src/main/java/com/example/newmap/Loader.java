package com.example.newmap;

import android.util.Log;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.File;

@Root
public class Loader {
    @Element
    private String[] teacherArray;
    @Element
    private String[] classroomArray;

    public Loader()
    {

    }

    public Loader(String[] defaultTeacherArray, String[] defaultClassroomArray)
    {
        teacherArray = defaultTeacherArray;
        classroomArray = defaultClassroomArray;

        for(int i  = 0; i < teacherArray.length; i++)
        {
            Log.d("strings", teacherArray[i]);
            Log.d("strings", classroomArray[i]);
        }
    }

    public String[] getTeacherArray()
    {
        return teacherArray;
    }

    public String[] getClassroomArray()
    {
        return classroomArray;
    }


    public void addTeacher(String teacher)
    {
        String[] temp = new String[teacherArray.length+1];

        for(int i = 0; i < teacherArray.length; i++)
        {
            temp[i] = teacherArray[i];
        }

        temp[teacherArray.length] = teacher;

        teacherArray = temp;
    }

    public void addRoom(String room)
    {
        String[] temp = new String[classroomArray.length+1];

        for(int i = 0; i < classroomArray.length; i++)
        {
            temp[i] = classroomArray[i];
        }

        temp[classroomArray.length] = room;

        classroomArray = temp;
    }

    public void addEntry(String teacher, String room)
    {
        addTeacher(teacher);
        addRoom(room);
    }


}
