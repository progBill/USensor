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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    Intent _intent;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity: ", "Starting Stuff");
        setContentView(R.layout.activity_main);
        startService();
        _intent = new Intent(this, SensorWrangler.class);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void startService(){
        startService(new Intent(getBaseContext(), SensorWrangler.class));
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

    public void updateUI(Intent i){

        String name = i.getStringExtra("name");
        String out = String.format("%s", name);
        Button b = (Button) findViewById(R.id.details);

        b.setText(out);

    }

    public void toDetailScreen(View v){
        Intent i = new Intent(this, DetailsActivity.class);
        startActivity(i);
    }

    private Button makeButton(String title){
        Button b = new Button(this);
        b.setText(title);
        return b;
    }
}
