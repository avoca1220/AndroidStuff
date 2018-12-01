package com.example.caleb.spinnertest;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        Log.d("SpinnerTest", String.valueOf(parent.getItemAtPosition(pos)));


    }

    public void onNothingSelected(AdapterView<?> parent)
    {

    }


}
