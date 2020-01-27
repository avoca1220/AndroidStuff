package com.example.mvhs;

import android.util.Log;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

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

    public void addTeacher(String teacher, int index)
    {
        String[] temp = new String[teacherArray.length+1];

        for(int i = 0; i < index; i++)
        {
            temp[i] = teacherArray[i];
        }

        temp[index] = teacher;

        for(int i = index; i < teacherArray.length; i++)
        {
            temp[i+1] = teacherArray[i];
        }

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

    public void addRoom(String room, int index)
    {
        String[] temp = new String[classroomArray.length+1];

        for(int i = 0; i < index; i++)
        {
            temp[i] = classroomArray[i];
        }

        temp[index] = room;

        for(int i = index; i < classroomArray.length; i++)
        {
            temp[i+1] = classroomArray[i];
        }

        classroomArray = temp;
    }

    public void addEntry(String teacher, String room)
    {
        for(int i = 0; i < teacherArray.length; i++)
        {
            if(teacher.compareTo(teacherArray[i]) < 0)
            {
                addTeacher(teacher, i);
                addRoom(room, i);
                break;
            }
        }
    }

    public void removeEntry(int index)
    {
        String[] tempTeacher = new String[teacherArray.length-1];
        String[] tempClassroom = new String[classroomArray.length-1];

        for(int i = 0; i < index; i++)
        {
            tempTeacher[i] = teacherArray[i];
        }
        for(int i = index+1; i < teacherArray.length; i++)
        {
            tempTeacher[i-1] = teacherArray[i];
        }

        for(int i = 0; i < index; i++)
        {
            tempClassroom[i] = classroomArray[i];
        }
        for(int i = index+1; i < classroomArray.length; i++)
        {
            tempClassroom[i-1] = classroomArray[i];
        }

        teacherArray = tempTeacher;
        classroomArray = tempClassroom;
    }



}
