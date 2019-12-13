package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.simpleframework.xml.*;
import org.simpleframework.xml.core.*;

import java.io.File;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Serializer serializer = new Persister();

        Serializable thing = new Serializable("Test text", 123);

        File directory = getFilesDir();
        File result = new File(directory, "example.xml");

        try {
            serializer.write(thing, result);
            Log.d("strings", "worked");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("strings", "failed miserably");

        }

    }
}
