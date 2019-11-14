/**
 * Where I'm at:
 *
 * 1. Dual-classroomed teachers in arrays twice                                         --Resolved
 * 2. McBrien crashes application                                                       --Resolved
 * 3. Changing teacher changes classroom changes teacher to that classroom's default    --Resolved
 * 4. Consecutive teachers with the same classroom mess up order!!!                     --Resolved
 * 5. Finish cycling buttons
 */

package com.example.newmap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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

    private ImageButton teacherCycleButton;
    private ImageButton classroomCycleButton;

    //Make this less sloppy! Accounts for three "onItemSelected" bits that get run at beginning of
    //program
    private int counter = -3;

    TeacherClassroomMap teacherClassroomMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Set up the map
        teacherClassroomMap = new TeacherClassroomMap(
                getResources().getStringArray(R.array.teachers_array),
                getResources().getStringArray(R.array.room_array_by_teacher),
                getResources().getStringArray(R.array.room_array),
                getResources().getIntArray(R.array.x_coordinates),
                getResources().getIntArray(R.array.y_coordinates));

        //Set up the spinners!
        teacherSpinner = (Spinner) findViewById(R.id.teacherSpinner);
        ArrayAdapter<String> teacherAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, teacherClassroomMap.getTeacherStrings());

        teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teacherSpinner.setAdapter(teacherAdapter);
        teacherSpinner.setOnItemSelectedListener(this);

        classroomSpinner = (Spinner) findViewById(R.id.classroomSpinner);
        ArrayAdapter<String> classroomAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.room_array));

        classroomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classroomSpinner.setAdapter(classroomAdapter);
        classroomSpinner.setOnItemSelectedListener(this);

        //Set up the buttons!
        teacherCycleButton = findViewById(R.id.teacherCycleButton);
        classroomCycleButton = findViewById(R.id.classroomCycleButton);

        teacherCycleButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

            }
        });

        classroomCycleButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

            }
        });



        teacherClassroomMap.printStrings();

        //Set up the arrays we'll need (we copy the ones from teacherClassroomMap because otherwise it's a bitch)
        teacherObjectArray = teacherClassroomMap.getTeacherObjectArray();

        classroomObjectArray = teacherClassroomMap.getClassroomObjectArray();
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
    {
        if(counter <= 0) {

            if (parent == teacherSpinner) {
                Teacher tempTeacher = Resources.getTeacherByName((String) teacherSpinner.getSelectedItem(), teacherObjectArray);
                Classroom[] classrooms = teacherClassroomMap.getClassrooms(tempTeacher);
                int index = Resources.getIndexOfClassroom(classrooms[0], getResources().getStringArray(R.array.room_array));

                counter++;

                //If the spinner already has a classroom selected with the same name as the one we
                // want to change it to (because onItemSelected is only run if that value changes,
                // which throws off our counter
                if(((String) classroomSpinner.getSelectedItem()).equals(classrooms[0].getName()))
                {
                    counter = 0;
                }
                else
                {
                    classroomSpinner.setSelection(index);
                }


            } else if (parent == classroomSpinner) {
                Classroom tempClassroom = classroomObjectArray[classroomSpinner.getSelectedItemPosition()];
                Teacher tempTeacher = Resources.getTeacherFromClassroom(tempClassroom, teacherClassroomMap, 0);
                //Log.d("strings", "Found teacher of " + tempTeacher.getName());
                int index = Resources.getIndexOfTeacher(tempTeacher, teacherClassroomMap.getTeacherStrings());
                //Log.d("strings", "Teachers index is: " + Integer.toString(index));

                counter++;

                if(((String) teacherSpinner.getSelectedItem()).equals(tempTeacher.getName()))
                {
                    counter = 0;
                }
                else
                {
                    teacherSpinner.setSelection(index);
                }

                Log.d("strings", "Got here.");
                if(Resources.multipleTeachers(teacherClassroomMap, tempClassroom))
                {
                    teacherCycleButton.setVisibility(View.VISIBLE);
                }
                else
                {
                    teacherCycleButton.setVisibility(View.INVISIBLE);
                }

            }

            //Counter incrementer was initially here


            Log.d("strings", "Times run:  " + Integer.toString(counter));
    }
        else
    {
            Log.d("strings", "Reset counter");
            counter = 0;
        }
    }

    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
