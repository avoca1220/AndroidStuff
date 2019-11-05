/**
 * Where I'm at:
 *
 * Have modified TeacherClassroomMap to work with objects instead of just strings. Yay.
 *
 * TeacherClassroomMap is not workin very well, most likely because I'm trying to use it with a set
 * of arrays from this class (which, though they have identical data, are different apparently).
 *
 * So fix that.
 */

package com.example.newmap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner teacherSpinner;
    private Spinner classroomSpinner;
    private TouchImageView map;
    private final int WIDTH = 3840;
    private final int HEIGHT = 2160;
    private Teacher[] teacherObjectArray;
    private Classroom[] classroomObjectArray;

    TeacherClassroomMap teacherClassroomMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up the spinners!
        teacherSpinner = (Spinner) findViewById(R.id.teacherSpinner);
        ArrayAdapter<String> teacherAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.teachers_array));

        teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teacherSpinner.setAdapter(teacherAdapter);
        teacherSpinner.setOnItemSelectedListener(this);

        classroomSpinner = (Spinner) findViewById(R.id.classroomSpinner);
        ArrayAdapter<String> classroomAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.room_array));

        classroomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classroomSpinner.setAdapter(classroomAdapter);
        classroomSpinner.setOnItemSelectedListener(this);



        //Set up the map
        teacherClassroomMap = new TeacherClassroomMap(
                getResources().getStringArray(R.array.teachers_array),
                getResources().getStringArray(R.array.room_array_by_teacher),
                getResources().getStringArray(R.array.room_array),
                getResources().getIntArray(R.array.x_coordinates),
                getResources().getIntArray(R.array.y_coordinates));

        teacherClassroomMap.printStrings();

        //Set up the arrays we'll need
        teacherObjectArray = Resources.getTeacherObjectArray(getResources().getStringArray(R.array.teachers_array));

        classroomObjectArray = Resources.getClassroomObjectArray(
                getResources().getStringArray(R.array.room_array),
                getResources().getIntArray(R.array.x_coordinates),
                getResources().getIntArray(R.array.y_coordinates));
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
    {
        if (parent == teacherSpinner)
        {
            Teacher tempTeacher = Resources.getTeacherByName((String)teacherSpinner.getSelectedItem(), teacherObjectArray);
            Classroom[] classrooms = teacherClassroomMap.getClassrooms(tempTeacher);
            //Log.d("strings", "Length is " + Integer.toString(teacherClassroomMap.getClassrooms(tempTeacher).length));
            //Log.d("strings", Integer.toString(Resources.getIndexOfClassroom(
            //        teacherClassroomMap.getClassrooms(tempTeacher)[0],
            //        getResources().getStringArray(R.array.room_array))));
        }
    }

    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
