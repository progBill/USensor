package info.billebeling.usensor.sensorreader;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String TAG = "BroadcastTest";
    Intent _intent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity: ", "Starting Stuff");
        setContentView(R.layout.activity_main);
        startService();
        _intent = new Intent(this, SensorWrangler.class);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void startService(){
        startService(new Intent(getBaseContext(), SensorWrangler.class));
    }

    public void stopService(View v){
        stopService(new Intent(getBaseContext(), SensorWrangler.class));
    }

    public void onResume(){
        super.onResume();
        startService(_intent);
        registerReceiver(broadcastReceiver, new IntentFilter(SensorWrangler.BROADCAST_ACTION));
    }
    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        stopService(_intent);
    }

    public void updateUI(Intent i){

        String name = i.getStringExtra("name");
        String data = i.getStringExtra("data");
        String out = String.format("%s\t%s", name, data);
        Log.d("MA updateUI", out);
        TextView t = (TextView) findViewById(R.id.text_out);

        if (!(name==null)) {
            Log.d(TAG, name);
            Log.d(TAG, String.valueOf(data));
        }
        t.setText(out);
    }
}
