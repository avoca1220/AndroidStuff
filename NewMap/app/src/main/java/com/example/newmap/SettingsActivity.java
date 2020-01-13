package com.example.newmap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.core.Persister;

import java.io.File;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Serializer serializer;
    private File directory;
    private File xml;
    private Loader loader;
    private LinearLayout ll;
    private String[] teacherArray;
    private String[] classroomArray;
    private TableLayout tb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("strings", "Creating settings!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        /*getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();

         */
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
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("strings", "Failed to read");
        }

        loader.addEntry("Andresen", "D6");


        //Write changes
        try {
            serializer.write(loader, xml);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("strings", "Failed to write");
        }

        tb = (TableLayout) findViewById(R.id.tb);

        teacherArray = loader.getTeacherArray();
        classroomArray = loader.getClassroomArray();

        TableLayout.LayoutParams lp = new TableLayout.LayoutParams();

        for(int i = 0; i < teacherArray.length; i++)
        {
            TextView tv1 = new TextView(getApplicationContext());
            TextView tv2 = new TextView(getApplicationContext());
            Button bt = new Button(getApplicationContext());
            tv1.setText(teacherArray[i]);
            tv2.setText(classroomArray[i]);
            bt.setId(i);
            bt.setOnClickListener(this);

            TableRow row = new TableRow(getApplicationContext());
            row.addView(tv1);
            row.addView(tv2);
            tb.addView(row, i);
        }




    }

    /*public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //recreate();
        finish();
        return true;
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
    {

    }
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}