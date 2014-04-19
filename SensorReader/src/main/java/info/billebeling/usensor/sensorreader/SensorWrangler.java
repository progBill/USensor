package info.billebeling.usensor.sensorreader;

import android.util.Log;

import com.sensorcon.sensordrone.android.Drone;

import java.sql.SQLException;

import info.billebeling.usensor.data.SensorObj;
import info.billebeling.usensor.db.SensorBaseQueries;
import info.billebeling.usensor.ui.sensorUI;


/**
 * Created by Torres James E on 4/14/2014.
 * SensorWrangler is the Controller class.
 *
 *
 */
public class SensorWrangler {
    private SensorObj[] _sensorArray;
    private SensorBaseQueries _db;
    private sensorUI _ui;

    public SensorWrangler(MainActivity aContext, Drone aDrone){
        _db = new SensorBaseQueries(aContext);
        _ui = new sensorUI(aContext);

        try {
            _db.open();
        } catch (SQLException e) {
            Log.d("DB ERROR", "Couldn't open a DB");
        }
        SensorObj s = new SensorObj("TempF", 0 , aDrone);
        _db.takeSensor(s);
        _sensorArray = _db.getSensors(aDrone);

        Log.d("SW sensorArray has this many: ", String.valueOf(_sensorArray.length));

        this.PollSensor();
    }


    public void PollSensor(){
        //DataPoint aDataPoint;
        String SensorName = "";
        float SensorValue = 0;

        for(SensorObj aSensor : _sensorArray){

           Log.d("SensorWrangler:", String.valueOf(_sensorArray));
           SensorName = aSensor.getName();
           SensorValue = aSensor.getData();
           Log.d("SensorWrangler:", "Done polling.");

           // aDataPoint = aSensor.takeMeasurement(); // takeMeasurement not yet created but will return data point.
           // _db.saveData(aDataPoint.get_sID(), aDataPoint.getData());
        }

        String tTxt;


        while (true) {
            sleep(5000);
            tTxt = String.format("%f", _sensorArray[0].getData());
            Log.d("SW, Temp F is:", tTxt);
            //_ui.setTemp(tTxt);
        }
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