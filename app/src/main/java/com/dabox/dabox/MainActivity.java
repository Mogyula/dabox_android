package com.dabox.dabox;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static Activity activity;
    private static TriggerContainer triggerContainer;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //ListView listView = (ListView) findViewById(R.id.listView);
        triggerContainer = (TriggerContainer) findViewById(R.id.triggerContainer);

        startService(new Intent(getBaseContext(), ListenerService.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static TriggerContainer getTriggerContainer(){
        return MainActivity.triggerContainer;
    }

    public static Activity getMainActivity(){
        return MainActivity.activity;
    }

    public void openDialog(View view){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("New trigger");
        dialog.show();
    }

    public void addTrigger(View view){
        String name = ((EditText)((View)view.getParent()).findViewById(R.id.editName)).getText().toString();
        String description = ((EditText)((View)view.getParent()).findViewById(R.id.editDescription)).getText().toString();
        String channel = ((EditText) ((View) view.getParent()).findViewById(R.id.editChannel)).getText().toString();

        if(!name.matches("") && !description.matches("") && !channel.matches("")) {
            this.triggerContainer.addTrigger(Integer.parseInt(channel), name, description);
            dialog.dismiss();
        }
    }

    public void sendSocket(View view){
        Integer channel = Integer.parseInt(((TextView)((View)view.getParent()).findViewById(R.id.channelField)).getText().toString());
        try {
            daBoxConnection conn = new daBoxConnection(
                    this.getResources().getString(R.string.dabox_address),
                    this.getResources().getInteger(R.integer.inbound_port),
                    channel,
                    getApplicationContext());
            conn.start();
        }catch (Exception e){
            Log.e("sendSocket()", e.toString());
        }
    }

    public void removeTrigger(View view){
        triggerContainer.removeTrigger(
                Integer.parseInt(((TextView) (((LinearLayout) view.getParent()).findViewById(R.id.channelField))).getText().toString())
        );
    }
}


