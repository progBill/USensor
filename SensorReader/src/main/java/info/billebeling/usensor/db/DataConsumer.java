package info.billebeling.usensor.db;

import info.billebeling.usensor.data.DataPoint;
import info.billebeling.usensor.data.Sensible;
import info.billebeling.usensor.data.Temperature;

public interface DataConsumer {

    public void takeData(DataPoint dp);
    public void takeSensor(Sensible sensor);

}
