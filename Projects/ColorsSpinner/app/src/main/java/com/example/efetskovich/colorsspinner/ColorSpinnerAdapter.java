package com.example.efetskovich.colorsspinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * @author e.fetskovich on 10/19/17.
 */

public class ColorSpinnerAdapter extends ArrayAdapter<Color> implements SpinnerAdapter {
    private List<Color> colors;

    public ColorSpinnerAdapter(Context context, int textViewResourceId, List<Color> colors) {
        super(context, textViewResourceId, colors);
        this.colors = colors;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        super.getDropDownView(position, convertView, parent);
        if (convertView == null) {
            convertView = View.inflate(getContext(), android.R.layout.simple_list_item_1, null);
        }
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setBackgroundColor(colors.get(position).getColor());
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(android.R.layout.simple_list_item_1, null);
        }
        TextView tv = (TextView) view.findViewById(android.R.id.text1);
        tv.setBackgroundColor(colors.get(position).getColor());
        return view;
    }


}
