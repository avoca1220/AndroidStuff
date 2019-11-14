package com.example.newmap;

import android.util.Log;

import java.security.InvalidParameterException;

public abstract class Resources {

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

    public static Teacher getTeacherFromClassroom(Classroom classroom, TeacherClassroomMap map, int index) {
        Teacher[] teachers = map.getNoDuplicatesTeacherObjectArray();
        //Log.d("strings", " ");
        //Log.d("strings", "Target: " + classroom.getName());

        //Return the teacher with the order of appearance from index. Use iterator to do this.
        int iterator = -1;

        for (int i = 0; i < teachers.length; i++) {
            //Log.d("strings", " ");
            //Log.d("strings", "Teacher: " + teachers[i].getName());
            for (int j = 0; j < map.getClassrooms(teachers[i]).length; j++) {
                //Log.d("strings", "Classroom: " + map.getClassrooms(teachers[i])[j].getName());
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
        Log.d("strings", "Number of teachers: " + Integer.toString(counter));
        if(counter > 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}