package info.billebeling.usensor.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import info.billebeling.usensor.db.SensorBaseContract.SensorBase;

public class SensorBaseHelper extends SQLiteOpenHelper{
    private static final String SQL_DELETE_BASE =
            "DROP TABLE IF EXISTS " + SensorBase.TABLE_NAME;
    private static final String SQL_DELETE_DATA =
            "DROP TABLE IF EXISTS " + SensorBase.DATA_TABLE_NAME;

    public SensorBaseHelper(Context context){
        super(context, SensorBase.DB_NAME, null, SensorBase.DATABASE_VERSION);
        Log.d("SBH Starting: ", SensorBase.DB_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Execing: ", SensorBase.SQL_CREATE_SENSORBASE);
        Log.d("Execing: ", SensorBase.DATA_SQL_CREATE_SENSORDATA);
        db.execSQL(SensorBase.SQL_CREATE_SENSORBASE);
        db.execSQL(SensorBase.DATA_SQL_CREATE_SENSORDATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL(SQL_DELETE_BASE);
        db.execSQL(SQL_DELETE_DATA);
        onCreate(db);
    }
}
