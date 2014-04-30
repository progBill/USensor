package info.billebeling.usensor.data;

import java.io.Serializable;

/**
 * Created by root on 4/28/14.
 */
public interface Sensible extends Serializable {
    /**
     * Created by Brett on 4/24/2014.
     * Interface for the various sensors to get Data and Turn them on and Off
     */
        static final long serialVersionUID = 1L;
        String getName();
        int getID();
        //Uses the measure[sensor] method, and then returns the data
        float takeMeasurement();
        //Turns off the Sensor
        boolean turnOff();
        //Turns on the Sensor
        boolean turnOn();
        //Returns to the Log the status of the sensor (on or off)
        boolean statusOf();
        //Creates a Datapoint of the most recent measuremnt from getData (and returns it)
        DataPoint getLastReading(int sID, String data, String date);
        // Returns an array of Datapoints
        DataPoint[] getDataPoints(int sID, String data, String date);
        //Pointer for the dataPointArray method
        void pointer(int _arraySize, int position);
    }
