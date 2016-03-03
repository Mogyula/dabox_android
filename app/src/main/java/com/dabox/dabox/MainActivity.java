package com.dabox.dabox;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private TriggerContainer triggerContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //ListView listView = (ListView) findViewById(R.id.listView);
        triggerContainer = (TriggerContainer) findViewById(R.id.triggerContainer);

        /*
        String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Uranus", "Neptune"};

        ArrayList<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList(planets));

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, layout.simple_list_item_1, list);

        listView.setAdapter(listAdapter);
        */
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

    public void openDialog(View view){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("New trigger");
        dialog.show();
    }

    public void addTrigger(View view){
        String name = ((EditText)((View)view.getParent()).findViewById(R.id.editName)).getText().toString();
        String description = ((EditText)((View)view.getParent()).findViewById(R.id.editDescription)).getText().toString();
        Integer channel = Integer.parseInt(((EditText) ((View) view.getParent()).findViewById(R.id.editChannel)).getText().toString());
        this.triggerContainer.addTrigger(channel,name,description);
    }
}


