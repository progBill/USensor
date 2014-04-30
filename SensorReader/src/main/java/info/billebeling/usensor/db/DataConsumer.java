package info.billebeling.usensor.db;

import info.billebeling.usensor.data.DataPoint;
import info.billebeling.usensor.data.SensorObj;

public interface DataConsumer {

    public void takeData(DataPoint dp);
    public void takeSensor(SensorObj sensor);

}
