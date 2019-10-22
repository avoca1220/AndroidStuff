package com.example.caleb.spinnertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.ortiz.touch.TouchImageView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String[] paths = {"Andresen", "McBrien", "Huff"};
    private Spinner spinner;
    private TouchImageView map;
    private final int WIDTH = 3840;
    private final int HEIGHT = 2160;

    private Map<String, String> teacherMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner)findViewById(R.id.teachers_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.teachers_array));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        map = (com.ortiz.touch.TouchImageView)findViewById(R.id.map);
        map.setMaxZoom(20);

        String[] teachersArray = getResources().getStringArray(R.array.teachers_array);
        String[] roomArray = getResources().getStringArray(R.array.room_array);

    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
    {
        Log.d("SpinnerTest", getResources().getStringArray(R.array.teachers_array)[position]);
        TextView roomView = (TextView) findViewById(R.id.roomView);
        roomView.setText(getResources().getStringArray(R.array.room_array)[position]);

        map.setZoom((float)20, (float)(getResources().getIntArray(R.array.x_coords)[position])/WIDTH, (float)(getResources().getIntArray(R.array.y_coords)[position])/HEIGHT);

    }

    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
