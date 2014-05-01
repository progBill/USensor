package info.billebeling.usensor.sensorreader;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import info.billebeling.usensor.data.Sensible;

public class MainActivity extends Activity {
    Intent _intent;
    boolean _gotSensors;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity: ", "Starting Stuff");
        setContentView(R.layout.activity_main);
        startService();
        _intent = new Intent(this, SensorWrangler.class);
        _gotSensors = false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void startService() {
        startService(new Intent(getBaseContext(), SensorWrangler.class));
    }

    public void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(SensorWrangler.BROADCAST_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    public void updateUI(Intent i) {

        Bundle b = i.getExtras();

        if(!_gotSensors) {
            HashMap sensorNames = (HashMap) b.getSerializable("names");
            Set sNames = sensorNames.entrySet();
            Iterator iter = sNames.iterator();

            LinearLayout lo = (LinearLayout) findViewById(R.id.layout);
            while (iter.hasNext()) {
                Map.Entry me = (Map.Entry) iter.next();
                String name = (String) me.getValue();
                String id = (String) me.getKey();
                iter.remove();

                Button newButton = this.makeButton(name,id);
                lo.addView(newButton);
                Log.d("new sensor received!", id + ":" + name);
            }

            Log.d("sensorNames length", String.valueOf(sensorNames.size()));
            _gotSensors = true;
        }
    }

    public void toDetailScreen(View v, String id) {
        Intent i = new Intent(this, DetailsActivity.class);

        Log.d("MA id to Deets:", id);

        i.putExtra("id", id);
        startActivity(i);
    }

    private Button makeButton(String title, final String id) {
        final Button b = new Button(this);
        b.setText(title);
        b.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                toDetailScreen(b, id);
            }
        });
        return b;
    }

}
