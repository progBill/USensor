package info.billebeling.usensor.sensorreader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;


import com.sensorcon.sensordrone.android.Drone;

public class MainActivity extends Activity {
    SensorWrangler _sWrangler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity: ", "Starting Stuff");

        Drone d = connect();

        _sWrangler = new SensorWrangler(this, d); // passing both context and drone to Wrangler.
        setContentView(R.layout.activity_main);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public Drone connect() {
        Drone d = new Drone();

        boolean isConn = false;

        while (!isConn) {

            isConn = d.btConnect("00:17:E9:50:EB:F6");
            d.enableTemperature();
            d.measureTemperature();
            Log.d("Temp F is:", String.valueOf(d.temperature_Fahrenheit));

            if (isConn) {
                Log.d("sensorDrone", "Connected");
            } else {
                Log.d("SensorDrone", "Not Connected");
            }
        }
        return d;

    }


    public void setText(String str){
        TextView t = (TextView) findViewById(R.id.hello_world);
        t.setText(str);

    }

    public static void sleep(int amt) // In milliseconds
    {
        long a = System.currentTimeMillis();
        long b = System.currentTimeMillis();
        while ((b - a) <= amt)
        {
            b = System.currentTimeMillis();
        }
    }
}