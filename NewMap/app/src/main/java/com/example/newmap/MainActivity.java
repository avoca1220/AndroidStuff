/**
 * Where I'm at:
 *
 * 1. Dual-classroomed teachers in arrays twice                                         --Resolved
 * 2. McBrien crashes application                                                       --Resolved
 * 3. Changing teacher changes classroom changes teacher to that classroom's default    --Resolved
 * 4. Consecutive teachers with the same classroom mess up order!!!                     --Resolved
 * 5. Finish actual cycling of teachers and classrooms for the cycling buttons          --Resolved
 * 6. Add map                                                                           --Resolved
 * 7. Make map scroll                                                                   --Resolved
 * 8. Make data editable                                                                --Resolved
 * 9. Crashes on AG2                                                                    --Resolved
 * 10.Add actual editing screen                                                         --Resolved
 * 11.Make editing screen edit
 * 12.Make main screen reload after editing
 */

package com.example.newmap;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner teacherSpinner;
    private Spinner classroomSpinner;
    private TouchImageView map;
    private final float WIDTH = 3840;
    private final float HEIGHT = 2160;
    private Teacher[] teacherObjectArray;
    private Classroom[] classroomObjectArray;

    ArrayAdapter<String> teacherAdapter;
    ArrayAdapter<String> classroomAdapter;

    private ImageButton teacherCycleButton;
    private ImageButton classroomCycleButton;

    private String[] roomArray;

    private String[] teacherArray;
    private String[] roomArrayByTeacher;

    private Serializer serializer;
    private File directory;
    private File xml;
    private Loader loader;

    private boolean shouldRestart;

    //Make this less sloppy! Accounts for three "onItemSelected" bits that get run at beginning of
    //program
    //*Never mind on the "making it less sloppy" bit. If it ain't broke, don't fix it.
    private int counter = -3;

    TeacherClassroomMap teacherClassroomMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //XML?
        directory = getFilesDir();
        xml = new File(directory, "example.xml");

        serializer = new Persister();

        if(!xml.exists())
        {
            loader = new Loader(getResources().getStringArray(R.array.teachers_array),
                    getResources().getStringArray(R.array.room_array_by_teacher));
            try {
                serializer.write(loader, xml);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("string", "Failed to write");
            }
        }
        else
        {
            try {
                loader = serializer.read(Loader.class, xml);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("strings", "Failed to read");
            }
        }


        /*
        Why is this here?
        try {
            serializer.write(loader, xml);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("strings", "Failed to write");
        }
        */

        //All teachers, editable
        teacherArray = loader.getTeacherArray();
        //All filled classrooms, in order of teachers
        roomArrayByTeacher = loader.getClassroomArray();

        //All filled classrooms in order of classroom
        roomArray = Resources.getFilledClassrooms(getResources().getStringArray(R.array.room_array), roomArrayByTeacher);
        //roomArray = getResources().getStringArray(R.array.room_array);


        //Set up the teacherClassroomMap map
        teacherClassroomMap = new TeacherClassroomMap(
                teacherArray,
                roomArrayByTeacher,
                getResources().getStringArray(R.array.room_array),
                getResources().getIntArray(R.array.x_coordinates),
                getResources().getIntArray(R.array.y_coordinates));

        //Set up the spinners!
        teacherSpinner = (Spinner) findViewById(R.id.teacherSpinner);
        teacherAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, teacherClassroomMap.getTeacherStrings());

        teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teacherSpinner.setAdapter(teacherAdapter);
        teacherSpinner.setOnItemSelectedListener(this);

        classroomSpinner = (Spinner) findViewById(R.id.classroomSpinner);
        classroomAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, roomArray);

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
                Resources.swapTeachers(teacherClassroomMap, classroomSpinner, teacherSpinner);
            }
        });

        classroomCycleButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Resources.swapClassrooms(teacherClassroomMap, classroomSpinner, teacherSpinner, roomArray);
            }
        });


        //teacherClassroomMap.printStrings();

        //Set up the arrays we'll need (we copy the ones from teacherClassroomMap because otherwise it's a bitch)
        teacherObjectArray = teacherClassroomMap.getTeacherObjectArray();
        classroomObjectArray = teacherClassroomMap.getClassroomObjectArray();

        //Set up the actual map of the school
        map = findViewById(R.id.map);
        map.setMaxZoom(20);

        shouldRestart = false;

    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
    {
        if(counter <= 0) {

            if (parent == teacherSpinner && !Resources.getFreezeTeacher()) {
                Teacher tempTeacher = Resources.getTeacherByName((String) teacherSpinner.getSelectedItem(), teacherObjectArray);
                Classroom[] classrooms = teacherClassroomMap.getClassrooms(tempTeacher);
                int index = Resources.getIndexOfClassroom(classrooms[0], roomArray);

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


            } else if (parent == classroomSpinner && !Resources.getFreezeClassroom()) {
                Classroom tempClassroom = classroomObjectArray[classroomSpinner.getSelectedItemPosition()];
                Teacher tempTeacher;

                /*Sometimes we don't have a teacher for a classroom. I don't feel like doing
                anything neater, so exceptions! Yay!
                */

                try
                {
                    tempTeacher=Resources.getTeacherFromClassroom(tempClassroom, teacherClassroomMap, 0);
                }
                catch(Exception InvalidParameterException )
                {
                    tempTeacher = null;
                    classroomSpinner.setPrompt(null);
                }

                if(tempTeacher != null) {
                    int index = Resources.getIndexOfTeacher(tempTeacher, teacherClassroomMap.getTeacherStrings());

                    counter++;

                    if (((String) teacherSpinner.getSelectedItem()).equals(tempTeacher.getName())) {
                        counter = 0;
                    } else {
                        teacherSpinner.setSelection(index);
                    }
                }
            }


            //See if classroom has multiple teachers or teacher has multiple classrooms and adjust
            //cycler button visibility accordingly
            Classroom tempClassroom = classroomObjectArray[classroomSpinner.getSelectedItemPosition()];
            Teacher tempTeacher = Resources.getTeacherByName((String) teacherSpinner.getSelectedItem(), teacherObjectArray);

            if (Resources.multipleTeachers(teacherClassroomMap, tempClassroom))
            {
                teacherCycleButton.setVisibility(View.VISIBLE);
            }
            else
            {
                teacherCycleButton.setVisibility(View.INVISIBLE);
            }

            if (Resources.multipleClassrooms(teacherClassroomMap, tempTeacher))
            {
                classroomCycleButton.setVisibility(View.VISIBLE);
            }
            else
            {
                classroomCycleButton.setVisibility(View.INVISIBLE);
            }

            //Janky method of keeping position updater & friends from running when we don't want them to
            if(Resources.getFreezeTeacher())
            {
                Resources.unfreezeTeacher();
            }
            if(Resources.getFreezeClassroom())
            {
                Resources.unfreezeClassroom();
            }

            //Move map position to proper classroom
            float x = ((float)tempClassroom.getX() / WIDTH);
            float y = ((float)tempClassroom.getY() / HEIGHT);

            map.setZoom(12, x, y);


        }
        else
        {
            counter = 0;
        }
    }

    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //Hit 'dat settings button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        return true;
    }


    @Override
    protected void onResume()
    {

        super.onResume();
        Log.d("strings", "resuming");

        if(shouldRestart)
        {
            Log.d("strings", "reloading");
            this.recreate();
        }
    }

    @Override
    protected void onPause()
    {
        Log.d("string", "Will restart");
        super.onPause();
        shouldRestart = true;
    }




}
