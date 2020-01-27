package com.caleb.mvhs;

import android.widget.Spinner;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Stores all of the bullshit I need (Organization! Put the bad stuff in one place so we don't have
 * to look at it!)
 */
public abstract class Resources {

    private static boolean freezeTeacher = false;
    private static boolean freezeClassroom = false;

    /**
     * Take an array of teacher names and return an array of Teacher objects
     *
     * @param teachers
     * @return
     */
    public static Teacher[] getTeacherObjectArray(String[] teachers) {
        Teacher[] teacherArray = new Teacher[teachers.length];

        for (int i = 0; i < teachers.length; i++) {
            teacherArray[i] = new Teacher(teachers[i]);
        }

        return teacherArray;
    }

    /**
     * Take arrays of classroom names, x-coordinates, and y-coordinates, and return an array of Classroom values
     *
     * @param classrooms
     * @param xCoord
     * @param yCoord
     * @return
     */
    public static Classroom[] getClassroomObjectArray(String[] classrooms, int[] xCoord, int[] yCoord) {

        //Make sure the arrays are valid
        if (classrooms.length != xCoord.length || xCoord.length != yCoord.length) {
            throw new InvalidParameterException();
        } else {
            //Create the aforementioned array of Classroom objects
            Classroom[] classroomArray = new Classroom[classrooms.length];

            for (int i = 0; i < classrooms.length; i++) {
                classroomArray[i] = new Classroom(classrooms[i], xCoord[i], yCoord[i]);
            }

            return classroomArray;
        }
    }

    public static Teacher getTeacherByName(String name, Teacher[] teachers) {
        for (int i = 0; i < teachers.length; i++) {
            if (teachers[i].getName().equals(name)) {
                return teachers[i];
            }
        }

        return null;
    }

    public static Classroom getClassroomByName(String name, Classroom[] classrooms) {
        for (int i = 0; i < classrooms.length; i++) {
            if (classrooms[i].getName().equals(name)) {
                return classrooms[i];
            }

        }

        return null;
    }

    public static int getIndexOfClassroom(Classroom classroom, String[] listOfClassrooms) {
        for (int i = 0; i < listOfClassrooms.length; i++) {
            if (listOfClassrooms[i].equals(classroom.getName())) {
                return i;
            }
        }
        throw new InvalidParameterException();
    }

    public static int getIndexOfTeacher(Teacher teacher, String[] listOfTeachers) {
        for (int i = 0; i < listOfTeachers.length; i++) {
            if (listOfTeachers[i].equals(teacher.getName())) {
                return i;
            }
        }
        throw new InvalidParameterException();
    }

    /**
     * Gets teacher with classroom, with index (zero based) dictating which teacher
     * (in order of appearance)
     * @param classroom
     * @param map
     * @param index
     * @return
     */
    public static Teacher getTeacherFromClassroom(Classroom classroom, TeacherClassroomMap map, int index) {
        Teacher[] teachers = map.getNoDuplicatesTeacherObjectArray();

        //Return the teacher with the order of appearance from index. Use iterator to do this.
        int iterator = -1;

        for (int i = 0; i < teachers.length; i++) {

            for (int j = 0; j < map.getClassrooms(teachers[i]).length; j++) {

                if (map.getClassrooms(teachers[i])[j].getName().equals(classroom.getName())) {
                    iterator++;
                    if (iterator == index)
                    {
                        return teachers[i];
                    }
                }
            }
        }
        throw new InvalidParameterException();
    }

    /**
     * See if there are multiple teachers with the one classroom
     * @param map
     * @param classroom
     * @return
     */
    public static boolean multipleTeachers(TeacherClassroomMap map, Classroom classroom)
    {
        int counter = 0;
        Teacher[] teachers = map.getNoDuplicatesTeacherObjectArray();

        for(int i = 0; i < teachers.length; i++)
        {
            for(int j = 0; j < map.getClassrooms(teachers[i]).length; j++)
            {
                if (map.getClassrooms(teachers[i])[j] == classroom)
                {
                    counter++;
                }
            }
        }

        if(counter > 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    public static boolean multipleClassrooms(TeacherClassroomMap map, Teacher teacher)
    {
        LinkedHashMap<Teacher, Classroom[]> objectMap = map.getWholeFuckingMap();

        if(objectMap.get(teacher).length > 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Swap teachers when teacher cycle button is pressed
     * @param map
     * @param classroomSpinner
     * @param teacherSpinner
     */
    public static void swapTeachers(TeacherClassroomMap map, Spinner classroomSpinner, Spinner teacherSpinner)
    {
        Classroom tempClassroom = map.getClassroomObjectArray()[classroomSpinner.getSelectedItemPosition()];
        Teacher[] teachers = map.getNoDuplicatesTeacherObjectArray();
        Teacher tempTeacher = Resources.getTeacherByName((String) teacherSpinner.getSelectedItem(), teachers);

        if (multipleTeachers(map, tempClassroom))
        {

            int numTeachers = 0;
            int currentAppearance = 0;
            int targetAppearance;
            String[] teacherStrings = map.getTeacherStrings();

            //How many teachers share this classroom? (one-based because zero-based would be stupid)
            //While we're at it, what's current teacher's order of appearance? (one-based)
            for(int i = 0; i < teachers.length; i++)
            {
                for(int j = 0; j < map.getClassrooms(teachers[i]).length; j++)
                {
                    if(map.getClassrooms(teachers[i])[j].getName().equals(tempClassroom.getName()))
                    {
                        numTeachers++;

                        if(teachers[i].getName().equals(tempTeacher.getName()))
                        {
                            currentAppearance = numTeachers;
                        }
                    }
                }
            }


            if(numTeachers == currentAppearance)
            {
                targetAppearance = 1;
            }
            else
            {
                targetAppearance = currentAppearance + 1;
            }

            //Now set teacherSpinner to our target.
            int newCounter = 0;
            for(int i = 0; i < teachers.length; i++)
            {
                for(int j = 0; j < map.getClassrooms(teachers[i]).length; j++)
                {
                    if(map.getClassrooms(teachers[i])[j].getName().equals(tempClassroom.getName()))
                    {
                        newCounter++;
                        if(newCounter == targetAppearance)
                        {
                            freezeTeacher();
                            teacherSpinner.setSelection(getIndexOfTeacher(teachers[i], teacherStrings));
                        }
                    }
                }
            }

        }
    }

    public static void swapClassrooms(TeacherClassroomMap map, Spinner classroomSpinner, Spinner teacherSpinner, String[] classroomStrings)
    {
        Classroom tempClassroom = map.getClassroomObjectArray()[classroomSpinner.getSelectedItemPosition()];
        Teacher[] teachers = map.getNoDuplicatesTeacherObjectArray();
        Teacher tempTeacher = Resources.getTeacherByName((String) teacherSpinner.getSelectedItem(), teachers);

        if(multipleClassrooms(map, tempTeacher))
        {
            int numClassrooms = map.getClassrooms(tempTeacher).length;
            int currentAppearance = 0;
            int targetAppearance;

            //What's the order of appearance of our current classroom?
            for(int i = 0; i < map.getClassrooms(tempTeacher).length; i++)
            {
                if(map.getClassrooms(tempTeacher)[i].getName().equals(tempClassroom.getName()))
                {
                    currentAppearance = i + 1;
                }
            }

            if(numClassrooms == currentAppearance)
            {
                targetAppearance = 1;
            }
            else
            {
                targetAppearance = currentAppearance + 1;
            }

            int targetIndex = getIndexOfClassroom(map.getClassrooms(tempTeacher)[targetAppearance-1], classroomStrings);
            freezeClassroom();
            classroomSpinner.setSelection(targetIndex);
        }
    }

    public static String[] getFilledClassrooms(String[] orderedByClassroom, String[] orderedByTeacher)
    {
        ArrayList<String> output = new ArrayList();

        for(int i = 0; i < orderedByClassroom.length; i ++)
        {
            for(int j = 0; j < orderedByTeacher.length; j++)
            {
                if(orderedByTeacher[j].equals(orderedByClassroom[i]))
                {
                    output.add(orderedByClassroom[i]);
                    break;
                }
            }
        }
        String[] array = output.toArray(new String[0]);
        return array;
    }



































































    //God I fucking hate myself for this
    public static boolean getFreezeTeacher()
    {
        return freezeTeacher;
    }
    public static boolean getFreezeClassroom()
    {
        return freezeClassroom;
    }
    public static void freezeTeacher()
    {
        freezeTeacher = true;
    }
    public static void freezeClassroom()
    {
        freezeClassroom = true;
    }
    public static void unfreezeTeacher()
    {
        freezeTeacher = false;
    }
    public static void unfreezeClassroom()
    {
        freezeClassroom = false;
    }



}