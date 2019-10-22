package com.example.newmap;

import android.util.Log;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Set;

public class TeacherClassroomMap {


    private String[] teacherArray;
    private String[] classroomArray;

    private int[] xCoords;
    private int[] yCoords;

    private HashMap<String, String[]> stringMap = new HashMap<String, String[]>();
    //private HashMap<Teacher, Classroom[]> objectMap = new HashMap<Teacher ,Classroom[]>();

    private Teacher[] teacherObjectArray;
    private Classroom[] classroomObjectArray;

    /**
     * Create a map of Teacher objects and their corresponding Classroom objects
     * @param teacherArray
     * @param classroomArray
     */
    public TeacherClassroomMap(String[] teacherArray, String[] classroomArray)
    {
        this.teacherArray = teacherArray;
        this.classroomArray = classroomArray;

        //this.teacherObjectArray = Resources.getTeacherObjectArray(teacherArray);
        //this.classroomObjectArray = Resources.getClassroomObjectArray(classroomArray, xCoords, yCoords);

        //Make sure the arrays are valid (of equal length)
        if (teacherArray.length != classroomArray.length)
        {
            throw new InvalidParameterException();
        }

        //Start with String array for mapping out relationships. Add the necessary key-value pairs,
        //and add the classrooms to teachers that already exist (if they do indeed exist).
        //Also all of the values are classroom arrays so that we can have multiple.
        for (int i = 0; i < teacherArray.length; i++)
        {

            //If teacher exists
            if (stringMap.containsKey(teacherArray[i]))
            {
                //Add classroom

                //Temp holds classrooms in teacher's array of their classrooms
                String temp[] = new String[stringMap.get(teacherArray[i]).length + 1];
                for(int j = 0; j < stringMap.get(teacherArray[i]).length; j++)
                {
                    temp[j] = stringMap.get(teacherArray[i])[j];
                }

                temp[stringMap.get(teacherArray[i]).length] = classroomArray[i];

                stringMap.put(teacherArray[i], temp);
            }
            //Add teacher-classroom pair
            else
            {
                stringMap.put(teacherArray[i], new String[]{classroomArray[i]});
            }




        }

        //Get arrays of actual objects, iterate through map of strings to build map of objects.


    }

    public void printStrings(){

        String[] listOfTeacherNames = stringMap.keySet().toArray(new String[stringMap.size()]);


        for(int i = 0; i < stringMap.size(); i++)
        {
            Log.d("strings", listOfTeacherNames[i]);

            for(int j = 0; j < stringMap.get(listOfTeacherNames[i]).length; j++)
            {
                Log.d("strings", stringMap.get(listOfTeacherNames[i])[j]);
            }
            Log.d("strings", " ");
        }

        /*
        for(int i = 0; i < teacherObjectArray.length; i++)
        {
            Log.d("Strings", teacherObjectArray[i].getName());
        }
        */
    }




}
