package info.billebeling.usensor.db;
import android.provider.BaseColumns;
public final class SensorBaseContract {
    //empty constr to avoid instantiation
    public SensorBaseContract() {}
    /* Inner class that defines the DB schemas */
    public static abstract class SensorBase implements BaseColumns {
        //DB info
        public static final int DATABASE_VERSION = 1;
        public static final String DB_NAME = "sensorProject.db";
        public static final String TEXT_TYPE = " TEXT";
        public static final String COMMA_SEP = ",";
        //sensorBase schema
        public static final String TABLE_NAME = "sensorBase";
        public static final String COLUMN_NAME_TITLE = "sensor";
        public static final String COLUMN_NAME_ID = "sensor_id";
        //sensorData schema
        public static final String DATA_TABLE_NAME = "sensorData";
        public static final String DATA_COLUMN_NAME_DATE = "created_at";
        public static final String DATA_COLUMN_NAME_ID = "sensor_id";
        public static final String DATA_COLUMN_NAME_DATA = "sensor_data";
        //SQL statements
        public static final String SQL_CREATE_SENSORBASE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        SensorBase.COLUMN_NAME_ID + " INTEGER PRIMARY KEY" + SensorBase.COMMA_SEP +
                        SensorBase.COLUMN_NAME_TITLE + SensorBase.TEXT_TYPE + " )";

        //no PK as it would really be a composite of all cols
        public static final String DATA_SQL_CREATE_SENSORDATA =
                "CREATE TABLE " + DATA_TABLE_NAME + " (" +
                        DATA_COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
                        DATA_COLUMN_NAME_DATA + TEXT_TYPE + COMMA_SEP +
                        DATA_COLUMN_NAME_DATE + TEXT_TYPE + " )";

    }

}
