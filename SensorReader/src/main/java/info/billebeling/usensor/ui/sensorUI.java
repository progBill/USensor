package info.billebeling.usensor.ui;

/**
 * Created by me on 4/16/14.
 */
import info.billebeling.usensor.sensorreader.R;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;

import info.billebeling.usensor.sensorreader.MainActivity;

public class sensorUI extends ActionBarActivity {

    private MainActivity _main;
//    private Button button = (Button) findViewById(R.id.button);

    public sensorUI (MainActivity ma)
    {

        _main = ma;

        ((Activity) ma).setContentView(R.layout.activity_main);
/*
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.button)
                {
                    MessageBox("Reading current temperature...");
                }
            }
        });  */
    }

    public void setTemp(String temp){
        TextView t = (TextView) ((Activity) _main).findViewById(R.id.hello_world);

        t.setText(temp);
        t.invalidate();
    }

//-----this is cleaner code(my opinion), would like to figure out------//
//    public void  clickEvent(View v)
//    {
//        if(v.getId() == R.id.button)
//        {
//            MessageBox("The current temp is...");
//        }
//    }
    public void MessageBox(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
