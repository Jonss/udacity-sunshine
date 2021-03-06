package com.github.jonss.sunshine.model;

/**
 * Created by neuromancer on 30/08/16.
 */
public class Temperature {

    private final Double mMin;
    private final Double mMax;
    private final String mMain;

    public Temperature(double min, double max, String main) {
        mMin = min;
        mMax = max;
        mMain = main;
    }

    @Override
    public String toString() {
        return mMain + " - " + mMax.intValue() + "/" + mMin.intValue();
    }

}
