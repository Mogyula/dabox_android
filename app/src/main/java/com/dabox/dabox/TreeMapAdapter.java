package com.dabox.dabox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.TreeMap;

public class TreeMapAdapter extends BaseAdapter {
    private final TreeMap<Integer, Trigger> map;

    public TreeMapAdapter(TreeMap<Integer, Trigger> map){
        super();
        this.map=map;
    }

    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public Trigger getItem(int position) {
        return (Trigger)map.values().toArray()[position];
    }

    @Override
    public long getItemId(int position) {
        //i don't understand the purpose of this method...
        return 0;//map.get(position).name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        } else {
            result = convertView;
        }

        ((TextView)result.findViewById(R.id.channelField)).setText(Integer.toString((Integer)(map.keySet().toArray()[position])));
        ((TextView)result.findViewById(R.id.nameField)).setText(getItem(position).name);
        ((TextView)result.findViewById(R.id.descriptionField)).setText(getItem(position).description);

        return result;
    }
}
