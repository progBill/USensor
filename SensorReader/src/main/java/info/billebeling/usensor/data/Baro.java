package info.billebeling.usensor.data;

/**
 * Created by Brett on 4/23.
 * HOPEFULLY takes measurements of Barometer, as well as saves the datapoints.
 */

import com.sensorcon.sensordrone.android.Drone;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Baro {

    private String _name;
    private int _id;
    private Drone _aDrone;
    DataPoint[] _dataPointsF;

    //Needs to have a Drone object as the parameter
    public Baro(String name, int id, Drone aDrone) {

        _name = name;
        _id = id;
        _aDrone =  aDrone;
        Log.d("New Sensor: ", _name);

        turnOnBaro();
    }

    public String getName() {
        return _name;
    }

    public int getID() {
        return _id;
    }

    //Get the Barometric Pressure in Pascals
    public float getBaro() {
        _aDrone.measurePressure();
        Log.d("Reading Pressure", "");
        return _aDrone.pressure_Pascals;

    }

    //Turns off the Pressure Sensor
    public boolean turnOffBaro() {
        boolean baroStatus;
        baroStatus = _aDrone.disablePressure();
        if (baroStatus = true) {
            Log.d("Sensor event:", "Pressure Sensor is now off.");
            return baroStatus;
        } else {
            Log.d("Sensor event error:", "Pressue Sensor is still on.");
            return baroStatus;
        }
    }
    //Turns on the Pressue sensor
    public boolean turnOnBaro() {
        _aDrone.setLeftLED(0,0,255);
        _aDrone.setRightLED(0,0,255);
        sleep(2000);
        if (_aDrone.enablePressure()) {
            Log.d("Sensor event:", "Pressure sensor is on.");
            _aDrone.setLeftLED(0,255, 0);
            _aDrone.setRightLED(0,255, 0);
            return true;

        } else
            Log.d("Sensor event error:", "Pressure sensor is still off");
        _aDrone.setLeftLED(0,255, 0);
        _aDrone.setRightLED(0,255, 0);

        return false;
    }
    //Checks if the Pressure sensor is on or off
    public boolean statusOfBaro() {
        boolean onOrOff;
        onOrOff = _aDrone.statusOfPressure();
        if (onOrOff = true) {
            Log.d("Sensor status:", "Connected");
            return onOrOff;
        } else {
            Log.d("Sensor Status:", "Not connected");
            return onOrOff;
        }
    }
    public DataPoint getMostRecentBaro (int sID, String data, String date) {
        data = String.valueOf(_aDrone.pressure_Pascals);
        Log.d("Datapoint:", "Being created");
        return new DataPoint(this._id, String.valueOf(data), date);
    }
    // Saves a datapoint into the array
    public DataPoint[] saveDataPoint(int sID, String data, String date) {
        int _arraySize = 30;
        int pointer = 0;
        Log.d("Saving:","DataPoints being saved to an array");
        this.pointer(_arraySize, pointer);
        return _dataPointsF;
    }

    public void pointer(int _arraySize, int position) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = new Date().toString();
        for (int i = 1; i <= _arraySize; i++) {
            if (position == _arraySize) {
                for (i = _arraySize; i >= 0; i--) {
                    _dataPointsF[i] = _dataPointsF[i + 1];
                }
            }
            else{
                _dataPointsF[i] = new DataPoint(this._id, String.valueOf(this.getBaro()), date);
                position++;

            }
        } Log.d("Looped:","Looped through array");
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
