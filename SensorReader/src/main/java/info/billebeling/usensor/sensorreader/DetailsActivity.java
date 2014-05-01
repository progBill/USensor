package info.billebeling.usensor.sensorreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DetailsActivity extends ActionBarActivity {

    private String _sID;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent callingIntent = this.getIntent();
        _sID = callingIntent.getStringExtra("id");
    }

    public void onResume(){
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(SensorWrangler.BROADCAST_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
    public void updateUI(Intent i){
        Bundle b = i.getExtras();
        Log.d("sID received from calling intent", String.valueOf(!(_sID==null)));

        HashMap sensorData = (HashMap) b.getSerializable("datas");
        HashMap sensorName = (HashMap) b.getSerializable("names");
        Set sData = sensorData.entrySet();
        Set sNames = sensorName.entrySet();
        Iterator diter = sData.iterator();
        Iterator niter = sNames.iterator();

        while (diter.hasNext()) {
            Map.Entry dataMap = (Map.Entry) diter.next();
            Map.Entry nameMap = (Map.Entry) niter.next();

            Log.d(String.valueOf(dataMap.getKey()), String.valueOf(dataMap.getValue()));

            if(_sID.equals(dataMap.getKey())) {

                TextView label = (TextView) findViewById(R.id.sensorLabel);
                label.setText(String.valueOf(nameMap.getValue()));

                TextView reading = (TextView) findViewById(R.id.sensorReading);
                reading.setText(String.valueOf(dataMap.getValue()));

            }
            diter.remove();
            niter.remove();
        }

    }
}
