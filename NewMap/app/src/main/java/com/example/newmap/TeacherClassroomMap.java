package com.example.newmap;

import android.util.Log;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class TeacherClassroomMap {


    private String[] teacherArray;
    private String[] classroomArrayByTeacher;

    private int[] xCoords;
    private int[] yCoords;

    private HashMap<String, String[]> stringMap = new LinkedHashMap<String, String[]>();
    private HashMap<Teacher, Classroom[]> objectMap = new LinkedHashMap<Teacher ,Classroom[]>();

    private Teacher[] teacherObjectArray;
    private Classroom[] classroomObjectArray;

    /**
     * Create a map of Teacher objects and their corresponding Classroom objects
     * @param teacherArray Strings of teacher names
     * @param classroomArrayByTeacher Strings of classroom names in order of teachers
     * @param classroomArray Strings of classrooms names in alphabetical order
     * @param xCoords Strings of classroom x coordinates
     * @param yCoords Strings of classroom y coordinates
     */
    public TeacherClassroomMap(String[] teacherArray, String[] classroomArrayByTeacher, String[] classroomArray, int[] xCoords, int[] yCoords)
    {
        this.teacherArray = teacherArray;
        this.classroomArrayByTeacher = classroomArrayByTeacher;

        teacherObjectArray = Resources.getTeacherObjectArray(teacherArray);
        classroomObjectArray = Resources.getClassroomObjectArray(classroomArray, xCoords, yCoords);


        //Make sure the arrays are valid (of equal length)

        if (teacherArray.length != classroomArrayByTeacher.length)
        {
            throw new InvalidParameterException();
        }

        //Start with String array for mapping out relationships. Add the necessary key-value pairs,
        //and add the classrooms to teachers that already exist (if they do indeed exist).
        //Also all of the classroom values are stored in arrays so that we can have multiple.
        for (int i = 0; i < teacherArray.length; i++)
        {

            //If teacher already exists, add classroom
            if (stringMap.containsKey(teacherArray[i]))
            {
                //Temp holds classrooms from teacher's array of their classrooms
                String temp[] = new String[stringMap.get(teacherArray[i]).length + 1];
                for(int j = 0; j < stringMap.get(teacherArray[i]).length; j++)
                {
                    temp[j] = stringMap.get(teacherArray[i])[j];
                }

                temp[stringMap.get(teacherArray[i]).length] = classroomArrayByTeacher[i];

                stringMap.put(teacherArray[i], temp);
            }
            //Add teacher-classroom pair
            else
            {
                stringMap.put(teacherArray[i], new String[]{classroomArrayByTeacher[i]});
            }
        }

        //Iterate through map of strings to build map of objects.
        for(int i = 0; i < teacherArray.length; i++) {
            Classroom[] temp = new Classroom[stringMap.get(teacherArray[i]).length];

            for (int j = 0; j < temp.length; j++) {
                temp[j] = Resources.getClassroomByName(stringMap.get(teacherArray[i])[j], classroomObjectArray);
            }

            objectMap.put(Resources.getTeacherByName(teacherArray[i], teacherObjectArray), temp);
        }

    }

    public void printStrings(){
        //Display all data within object map
        Teacher[] listOfTeacherObjects = objectMap.keySet().toArray(new Teacher[objectMap.size()]);
        String[] listOfTeacherObjectNames = new String[listOfTeacherObjects.length];

        for(int i = 0; i < listOfTeacherObjects.length; i++)
        {
            listOfTeacherObjectNames[i] = listOfTeacherObjects[i].getName();
        }

        for(int i = 0; i < objectMap.size(); i++)
        {
            Log.d("strings", listOfTeacherObjectNames[i]);

            for(int j = 0; j < objectMap.get(listOfTeacherObjects[i]).length; j++)
            {
                Log.d("strings", objectMap.get(listOfTeacherObjects[i])[j].name);
            }
            
            Log.d("strings", " ");
        }


    }




}
