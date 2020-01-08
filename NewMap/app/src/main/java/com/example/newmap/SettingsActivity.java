package com.example.newmap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

public class SettingsActivity extends AppCompatActivity {

    private Serializer serializer;
    private File directory;
    private File xml;
    private Loader loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("strings", "Creating settings!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //XML?
        directory = getFilesDir();
        xml = new File(directory, "example.xml");

        serializer = new Persister();

        try {
            loader = serializer.read(Loader.class, xml);
            Log.d("string", "Read successfully!");
            for(int i = 0; i < loader.getClassroomArray().length; i++)
            {
                Log.d("strings", loader.getClassroomArray()[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("strings", "Failed to read");
        }

        loader.addEntry("McBrien", "A6");


        //Write changes
        try {
            serializer.write(loader, xml);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("strings", "Failed to write");
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        recreate();
        //finish();
        return true;
    }
}