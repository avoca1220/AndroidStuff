package com.caleb.mvhs;

import android.util.Log;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class TeacherClassroomMap {


    private String[] teacherArray;
    private String[] classroomArrayByTeacher;
    private String[] classroomArray;

    private int[] xCoords;
    private int[] yCoords;

    private LinkedHashMap<String, String[]> stringMap = new LinkedHashMap<String, String[]>();
    private LinkedHashMap<Teacher, Classroom[]> objectMap = new LinkedHashMap<Teacher ,Classroom[]>();

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
        this.classroomArray = classroomArray;

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

    public Classroom[] getClassrooms(Teacher teacher)
    {
        return objectMap.get(teacher);
    }

    /**
     * We gon' use the arrays from this object in some other ones.
     */
    public Teacher[] getTeacherObjectArray()
    {
        return this.teacherObjectArray;
    }

    public Classroom[] getClassroomObjectArray()
    {
        ArrayList<Classroom> output = new ArrayList();



        for(int i = 0; i < classroomObjectArray.length; i++)
        {
            for(int j = 0; j < classroomArrayByTeacher.length; j++)
            {
                if (classroomObjectArray[i].getName().equals(classroomArrayByTeacher[j]))
                {
                    output.add(classroomObjectArray[i]);
                    break;
                }
            }
        }
        return output.toArray(new Classroom[0]);
    }

    public String[] getTeacherStrings()
    {
        //Stupid way to convert Object[] to String[]
        return Arrays.copyOf(stringMap.keySet().toArray(), stringMap.keySet().toArray().length, String[].class);
    }

    public Teacher[] getNoDuplicatesTeacherObjectArray()
    {
        return Arrays.copyOf(objectMap.keySet().toArray(), objectMap.keySet().toArray().length, Teacher[].class);
    }

    public LinkedHashMap<Teacher, Classroom[]> getWholeFuckingMap()
    {
        return this.objectMap;
    }

}
