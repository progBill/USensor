package info.billebeling.usensor.data;

public interface DataConsumer {

    public void takeData(DataPoint dp);
    public void takeSensor(SensorObj sensor);

}
