package com.example.newmap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

public class Entry extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button cancelButton;
    private Button addButton;
    private Spinner classroomSpinner;
    private TextView teacherName;

    private ArrayAdapter<String> classroomAdapter;;

    private Serializer serializer;
    private File directory;
    private File xml;
    private Loader loader;

    private String[] roomArray;
    private String[] roomArrayByTeacher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        //XML!
        directory = getFilesDir();
        xml = new File(directory, "example.xml");

        serializer = new Persister();

        try {
            loader = serializer.read(Loader.class, xml);
            Log.d("string", "Read successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("strings", "Failed to read");
        }


        //Spinner!
        classroomSpinner = (Spinner) findViewById(R.id.classroomName);
        classroomAdapter = new ArrayAdapter<String>(Entry.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.room_array));

        classroomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classroomSpinner.setAdapter(classroomAdapter);
        classroomSpinner.setOnItemSelectedListener(this);

        //Text!
        teacherName = findViewById(R.id.teacherName);

        //Buttons!
        cancelButton = findViewById(R.id.cancelButton);
        addButton = findViewById(R.id.addButton);
        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String tempString = teacherName.getText().toString();
                String tempString2 = (String) classroomSpinner.getSelectedItem();
                loader.addEntry(tempString, tempString2);
                try {
                    serializer.write(loader, xml);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("string", "Failed to write");
                }
                finish();
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
    {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
