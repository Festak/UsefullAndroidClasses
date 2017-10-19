package com.example.efetskovich.colorsspinner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        textView = (TextView) findViewById(R.id.textColor);

        ColorSpinnerAdapter colorSpinner = new ColorSpinnerAdapter(MainActivity.this, android.R.layout.simple_list_item_1, ColorGenerator.generateColors());
        spinner.setAdapter(colorSpinner);

        spinner.setOnItemSelectedListener(getColorSelectedListener());

    }

    private AdapterView.OnItemSelectedListener getColorSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Color color = (Color) adapterView.getSelectedItem();
                if (color != null) {
                    textView.setTextColor(color.getColor());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        };
    }

}
