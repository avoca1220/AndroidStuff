package com.example.newmap;

import android.util.Log;

import java.security.InvalidParameterException;

public abstract class Resources {

    /**
     * Take an array of teacher names and return an array of Teacher objects
     * @param teachers
     * @return
     */
    public static Teacher[] getTeacherObjectArray(String[] teachers)
    {
        Teacher[] teacherArray = new Teacher[teachers.length];

        for(int i = 0; i < teachers.length; i++)
        {
            teacherArray[i] = new Teacher(teachers[i]);
        }

        return teacherArray;
    }

    /**
     * Take arrays of classroom names, x-coordinates, and y-coordinates, and return an array of Classroom values
     * @param classrooms
     * @param xCoord
     * @param yCoord
     * @return
     */
    public static Classroom[] getClassroomObjectArray(String[] classrooms, int[] xCoord, int[] yCoord)
    {

        //Make sure the arrays are valid
        if(classrooms.length != xCoord.length || xCoord.length != yCoord.length)
        {
            throw new InvalidParameterException();
        }
        else {
            //Create the aforementioned array of Classroom objects
            Classroom[] classroomArray = new Classroom[classrooms.length];

            for (int i = 0; i < classrooms.length; i++) {
                classroomArray[i] = new Classroom(classrooms[i], xCoord[i], yCoord[i]);
            }

            return classroomArray;
        }
    }

    public static Teacher getTeacherByName(String name, Teacher[] teachers)
    {
        for(int i = 0; i < teachers.length; i++)
        {
            if(teachers[i].getName().equals(name))
            {
                return teachers[i];
            }
        }

        return null;
    }

    public static Classroom getClassroomByName(String name, Classroom[] classrooms)
    {
        for(int i = 0; i < classrooms.length; i++)
        {
            if(classrooms[i].getName().equals(name))
            {
                return classrooms[i];
            }
        }

        return null;
    }
}
