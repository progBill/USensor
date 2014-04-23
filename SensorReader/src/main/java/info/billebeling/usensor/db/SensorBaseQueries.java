package info.billebeling.usensor.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sensorcon.sensordrone.android.Drone;

import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import info.billebeling.usensor.data.SensorObj;
import info.billebeling.usensor.db.SensorBaseContract.SensorBase;
import info.billebeling.usensor.data.DataConsumer;
import info.billebeling.usensor.data.DataPoint;

public class SensorBaseQueries implements DataConsumer{
    private SensorBaseHelper _sbHelper;
    private SQLiteDatabase _db;
    private Context _cxt;


    public SensorBaseQueries(Context context){
        Log.d("INIT: ", "SensorBaseQueries");
        _sbHelper = new SensorBaseHelper(context);
        _cxt = context;
    }

    public void open() throws SQLException {
        _db = _sbHelper.getWritableDatabase();
    }

   public void close() {
        _db.close();
    }

    //////////////////
    //CREATE METHODS//
    //////////////////
    public Queue<DataPoint> getDP(int sID){

        Queue<DataPoint> dp = new LinkedList<DataPoint>();
        String[] datum = this.getSensorData(sID);

        Log.d("datum 0,1:",String.valueOf(datum[0]) + " " + String.valueOf(datum[1]));

        if (datum.length == 0){
            return null;
        }
        else{
        for (int i=0;i<datum.length;i++){
            Log.d("datum:", String.format("%s", datum[i]));
        }

        dp.add( new DataPoint(sID, datum[0], datum[1]));
        Log.d("SBQ New DP", "");

        return dp;}
    }

    //////////////////////
    //SensorBase Queries//
    //////////////////////

    /**
     * Saves a sensor title into the sensorBase DB
     *
     * @param s
     */
    public void saveSensor(String s){
        Log.d("SensorBase: ","Saving");
        ContentValues values = new ContentValues();
        values.put(SensorBase.COLUMN_NAME_TITLE, s);
        values.put(SensorBase.COLUMN_NAME_TITLE, s);
        long newRowID = _db.insert(SensorBase.TABLE_NAME, null, values);
        Log.d("SensorBase new Row id: ", String.valueOf(newRowID));
    }

    /**
     * Returns an array of sensor's names
     * @return
     */
    public SensorObj[] getSensors(Drone d){
        Log.d("SensorBase: ", "getting Sensors");

        String[] projection = {
                SensorBase.COLUMN_NAME_ID,
                SensorBase.COLUMN_NAME_TITLE
        };

        String sortOrder =  SensorBase.COLUMN_NAME_ID;
        Cursor c = _db.query(
               SensorBase.TABLE_NAME,
                projection,     //return columns
                null, null,     //WHERE cols, vals
                null, null,     //GROUP BY cols, vals
                sortOrder       //ORDER BY
        );

        SensorObj[] sensors;
        sensors = new SensorObj[c.getCount()];

        Log.d("SBQ has this many sensors: ", String.valueOf(c.getCount()));

        c.moveToFirst();
        int i = 0;
        while(!c.isAfterLast()){
            sensors[i] = new SensorObj(c.getString(1), Integer.parseInt(c.getString(0)), d);
            c.moveToNext();
            i++;
        }

        return sensors;
    }
    ////////////////////
    //saveData Queries//
    ////////////////////

    /**
     * Returns an array of data for a particular sensor ordered from most to least recent
     * @return
     */
    public String[] getSensorData(int sid){
        Log.d("SensorData: ", "getting Sensor Data");

        String[] projection = {
                SensorBase.DATA_COLUMN_NAME_DATA,
                SensorBase.DATA_COLUMN_NAME_DATE
        };

        String whereCol = SensorBase.DATA_COLUMN_NAME_ID;
        String[] whereVals = {String.valueOf(sid)};
        String sortOrder =  SensorBase.DATA_COLUMN_NAME_DATE;
        Cursor c = _db.query(
                SensorBase.DATA_TABLE_NAME,
                projection,             //return columns
                null,null,
                //whereCol, whereVals,    //WHERE cols, vals
                null, null,             //GROUP BY cols, vals
                sortOrder               //ORDER BY
        );

        String[] data = new String[c.getCount() + 1];

        Log.d("SD has this much data: ", String.valueOf(c.getCount()));

        c.moveToFirst();
        int i = 0;
        while(!c.isAfterLast()){
            data[i] = c.getString(0);
            Log.d(String.format("data %d", i), data[i]);
            c.moveToNext();
            i++;
        }

        return data;
    }

    /////////////////////
    //Testing functions//
    /////////////////////

    public void CheckSBDB(){
    File database=_cxt.getDatabasePath(SensorBase.DB_NAME);

        if (!database.exists()) {
            // Database does not exist so copy it from assets here
            Log.i("SensorBase", "Not Found");
        } else {
            Log.i("SensorBase", "Found");
        }
    }//end CheckSBDB

    public void checkSDDB(){
        File database=_cxt.getDatabasePath(SensorBase.DB_NAME);

        if (!database.exists()) {
            // Database does not exist so copy it from assets here
            Log.i("SensorData", "Not Found");
        } else {
            Log.i("SensorData", "Found");
        }
    }//end CheckSBDB

    //////////////////
    //save DataPoint//
    //////////////////
    @Override
    public void takeData(DataPoint dp) {
        if(!this.dataPointExists(dp)){
            this.saveData(dp);
        }
    }

    private boolean dataPointExists(DataPoint dp){
        String[] projection = {SensorBase.DATA_COLUMN_NAME_DATA};

        String whereCol =
                SensorBase.DATA_COLUMN_NAME_ID + "=? AND " +
                SensorBase.DATA_COLUMN_NAME_DATA + "=? AND " +
                SensorBase.DATA_COLUMN_NAME_DATE + "=? ";

        String[] whereVals = {String.valueOf(dp.get_sID()), dp.getData(), dp.getDate()};
        String sortOrder =  SensorBase.DATA_COLUMN_NAME_DATE;

        Cursor c = _db.query(
                SensorBase.DATA_TABLE_NAME,
                projection,             //return columns
                whereCol, whereVals,    //WHERE cols, vals
                null, null,             //GROUP BY cols, vals
                sortOrder               //ORDER BY
        );

        Log.d("SBQ data exists row count:", String.valueOf(c.getCount()));

        return c.getCount() > 0;
    }



    private void saveData(DataPoint dp){
        Log.d("SensorDataQueries: ","Saving");
        ContentValues values = new ContentValues();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = new Date().toString();
        values.put(SensorBase.DATA_COLUMN_NAME_ID, dp.get_sID());
        values.put(SensorBase.DATA_COLUMN_NAME_DATA, dp.getData());
        values.put(SensorBase.DATA_COLUMN_NAME_DATE, date);
        long newRowID = _db.insert(SensorBase.DATA_TABLE_NAME, null, values);
        Log.d("SensorData new Row id: ", String.valueOf(newRowID));
    }

    ////////////////
    //Save Sensors//
    ////////////////
    @Override
    public void takeSensor(SensorObj sensor) {
        if(!this.sensorExists(sensor)){
            this.saveSensor(sensor);
        }
    }

    private boolean sensorExists(SensorObj snsr){
        String[] projection = {SensorBase.COLUMN_NAME_TITLE};

        String whereCol =
                SensorBase.COLUMN_NAME_ID + "=? AND " +
                        SensorBase.COLUMN_NAME_TITLE + "=? ";

        String[] whereVals = {String.valueOf(snsr.getID()), snsr.getName()};

        Cursor c = _db.query(
                SensorBase.TABLE_NAME,
                projection,             //return columns
                whereCol, whereVals,    //WHERE cols, vals
                null, null,             //GROUP BY cols, vals
                null                    //ORDER BY
        );

        Log.d("SBQ  exists row count:", String.valueOf(c.getCount()));

        return c.getCount() > 0;
    }

    public void saveSensor(SensorObj sensor){
        Log.d("SensorBase: ","Saving");
        ContentValues values = new ContentValues();
        values.put(SensorBase.COLUMN_NAME_TITLE, sensor.getName());
        values.put(SensorBase.COLUMN_NAME_ID, sensor.getID());
        long newRowID = _db.insert(SensorBase.TABLE_NAME, null, values);
        Log.d("SensorBase new Row id: ", String.valueOf(newRowID));
    }


}