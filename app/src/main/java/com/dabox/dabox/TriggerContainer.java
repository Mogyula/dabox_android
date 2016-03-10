package com.dabox.dabox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeMap;

public class TriggerContainer extends ListView {
    private TreeMap<Integer, Trigger> triggers;
    private Context context;

    public TriggerContainer(Context context, AttributeSet attrs){
        super(context, attrs);
        this.triggers = new TreeMap<Integer, Trigger>();
        this.context = context;
        loadFromPhone();
    }

    public void addTrigger(Integer channel, String name, String description){
        this.triggers.put(channel, new Trigger(name, description));
        this.saveToPhone();
        this.updateListView();
    }

    public void removeTrigger(Integer channel){
        this.triggers.remove(channel);
        this.saveToPhone();
        this.updateListView();
    }

    public Boolean exists(Integer channel){
        if(this.triggers.get(channel)!=null)
            return true;
        else
            return false;
    }

    private void saveToPhone(){
        try {
            FileOutputStream fos = this.context.openFileOutput(getResources().getText(R.string.triggers_file).toString(), Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.triggers);
            oos.close();
            fos.close();
        }catch(Exception e){
            Log.e("saveToPhone()", e.toString());
            showError(getResources().getText(R.string.save_error).toString());
        }
    }

    public void loadFromPhone(){
        try {
            FileInputStream fis = this.context.openFileInput(getResources().getText(R.string.triggers_file).toString());
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.triggers = (TreeMap<Integer, Trigger>)ois.readObject();
            ois.close();
            fis.close();
            updateListView();
        }catch (Exception e){
            Log.e("loadFromPhone()", e.toString());
            showError(getResources().getText(R.string.load_error).toString());
        }
    }

    private void updateListView(){
        TreeMapAdapter adapter = new TreeMapAdapter(this.triggers);
        this.setAdapter(adapter);
    }

    private void showError(String message){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.context);
        dialogBuilder.setTitle(R.string.error);
        dialogBuilder.setMessage(message);
        dialogBuilder.setNeutralButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialogBuilder.create().show();
    }
}