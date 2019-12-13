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


}
