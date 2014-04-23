package info.billebeling.usensor.ui;


import android.content.Context;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;


public class sensorUI extends AdapterView{


    public sensorUI(Context context) {
        super(context);
    }

    @Override
    public Adapter getAdapter() {
        return null;
    }

    @Override
    public void setAdapter(Adapter adapter) {

    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setSelection(int i) {

    }
}
