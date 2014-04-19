package info.billebeling.usensor.data;


import android.util.Log;

public class DataPoint {

    private int _sID;
    private String _date;
    private String _data;

    public DataPoint(int sID, String data, String date){

        _sID = sID;
        _date = date;
        _data = data;
        Log.d("New DataPoint", String.format("%d: %s",_sID, _data));
    }

    public DataPoint (int sID, String data){
        _sID= sID;
        _data = data;
    }

    public int get_sID() {
        return _sID;
    }
    public String getDate() {
        return _date;
    }
    public String getData() {
        return _data;
    }
    public void setDate(String incDate){ _date = incDate;}


}
