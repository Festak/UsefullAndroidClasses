package com.example.efetskovich.colorsspinner;

import java.util.LinkedList;
import java.util.List;

/**
 * @author e.fetskovich on 10/19/17.
 */

public class ColorGenerator {

    private ColorGenerator(){
        // do nothing
    }

    public static List<Color> generateColors(){
        List<Color> list = new LinkedList<>();
        list.add(new Color(android.graphics.Color.rgb(0, 0, 0)));
        list.add(new Color(android.graphics.Color.rgb(255, 255, 255)));
        list.add(new Color(android.graphics.Color.rgb(255, 0, 0)));
        list.add(new Color(android.graphics.Color.rgb(0, 255, 0)));
        list.add(new Color(android.graphics.Color.rgb(0, 0, 255)));
        list.add(new Color(android.graphics.Color.rgb(100, 4, 200)));
        return list;
    }

}
