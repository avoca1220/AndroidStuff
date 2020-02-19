package com.caleb.mvhs;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;


import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

@TargetApi(16)
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private Serializer serializer;
    private File directory;
    private File xml;
    private Loader loader;
    private LinearLayout ll;
    private String[] teacherArray;
    private String[] classroomArray;
    private TableLayout tb;
    private boolean shouldRestart = false;

    //public ScrollView newView = findViewById(R.id.newView);


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //XML?
        directory = getFilesDir();
        xml = new File(directory, "data.xml");

        serializer = new Persister();

        try {
            loader = serializer.read(Loader.class, xml);

        } catch (Exception e) {
            e.printStackTrace();

        }




        //Write changes
        try {
            serializer.write(loader, xml);
        } catch (Exception e) {
            e.printStackTrace();

        }

        tb = (TableLayout) findViewById(R.id.tb);

        teacherArray = loader.getTeacherArray();
        classroomArray = loader.getClassroomArray();


        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT, 1);
        lp.setMargins(5, 30, 5, 30);
        lp.height=50;

        TableRow.LayoutParams bp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0);
        bp.setMargins(5, 30, 5, 30);



        for(int i = 0; i < teacherArray.length; i++)
        {
            TextView tv1 = new TextView(getApplicationContext());
            TextView tv2 = new TextView(getApplicationContext());
            ImageButton bt = new ImageButton(getApplicationContext());
            tv1.setText(teacherArray[i]);
            tv1.setTextColor(Color.parseColor("#8c8c8c"));
            tv1.setLayoutParams(lp);
            tv1.setTextSize(16);
            tv2.setText(classroomArray[i]);
            tv2.setTextColor(Color.parseColor("#8c8c8c"));
            tv2.setLayoutParams(lp);
            tv2.setTextSize(16);
            bt.setId(i);
            bt.setLayoutParams(bp);
            //bt.setMaxHeight(5);
            //bt.setMaxWidth(5);
            bt.setOnClickListener(this);
            //bt.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.delete, null));
            TableRow row = new TableRow(getApplicationContext());
            row.setLayoutParams(lp);
            row.addView(tv1);
            row.addView(tv2);
            row.addView(bt);
            row.setId(i);
            tb.addView(row, i);


            bt.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_cancel_black_24dp, null));
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
        //startActivity(new Intent(SettingsActivity.this, ShowPopUp.class));
        if (getResources().getResourceEntryName(item.getItemId()).equals("action_menu"))
        {
            startActivity(new Intent(SettingsActivity.this, Entry.class));
        }
        else{
            finish();
        }
        return true;
    }

    @Override
    public void onClick(View v)
    {


        //Parcelable state = newView.onSaveInstanceState();
        for(int i = 0; i < tb.getChildCount(); i++)
        {
            if(tb.getChildAt(i).getId() == v.getId())
            {
                tb.removeView(tb.getChildAt(i));
                loader.removeEntry(i);
                break;
            }

        }

        try {
            serializer.write(loader, xml);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume()
    {

        super.onResume();

        if(shouldRestart)
        {
            this.recreate();
        }
    }


    @Override
    protected void onPause()
    {
        super.onPause();
        shouldRestart = true;
    }

}