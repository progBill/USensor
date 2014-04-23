package info.billebeling.usensor.sensorreader;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.sensorcon.sensordrone.android.Drone;
import java.sql.SQLException;
import java.util.Date;

import info.billebeling.usensor.data.DataPoint;
import info.billebeling.usensor.data.SensorObj;
import info.billebeling.usensor.db.SensorBaseQueries;


public class SensorWrangler extends Service{
    private SensorObj[] _sensorArray;
    private static final String TAG = "BroadcastService";
    public static final String BROADCAST_ACTION = "info.billebeling.usensor.displayevent";
    private final Handler handler = new Handler();
    private String _name;
    private float _data;
    private Intent _intent;
    private SensorBaseQueries _db;

    @Override
    public void onCreate(){
        super.onCreate();

        Toast.makeText(this, "SW Created", Toast.LENGTH_LONG).show();
        _intent = new Intent(BROADCAST_ACTION);
        Drone aDrone = connect();
        _db = new SensorBaseQueries(getBaseContext());
        try {
            _db.open();
        } catch (SQLException e) {
            Log.d("DB ERROR", e.toString());
        }

        //TODO: move this sensor creation code to database oncreate
        SensorObj s;
        s = new SensorObj("TempF", 0 , aDrone);
        _db.takeSensor(s);

        _sensorArray = _db.getSensors(aDrone);

        Log.d("SW sensorArray has this many: ", String.valueOf(_sensorArray.length));

    }

    public int onStartCommand(Intent intent, int flags, int startId){
        Toast.makeText(this, "SW Started", Toast.LENGTH_LONG).show();
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 1000); // 1 second
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent i) {
        return null;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "SW Destroyed", Toast.LENGTH_LONG).show();
        _db.close();
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

    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            DisplayLoggingInfo();
            handler.postDelayed(this, 1000); // 1c seconds
        }
    };

    public void pollSensor(){

        int sID = 0;
        for(SensorObj s : _sensorArray){
            _name = s.getName();
            _data = s.getData();
            sID = s.getID();
        }

        String date = new Date().toString();
        _db.takeData(new DataPoint(sID, String.valueOf(_data), date));

        Log.d(String.format("SW: pollS: %s --", _name), String.valueOf(_data));

    }

    private void DisplayLoggingInfo() {
        Log.d(TAG, "entered DisplayLoggingInfo");

        pollSensor();
        _intent.putExtra("name", _name);
        _intent.putExtra("data", String.valueOf(_data));
        sendBroadcast(_intent);
    }

}