package com.github.jonss.sunshine;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.github.jonss.sunshine.model.Temperature;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joao on 30/08/16.
 */
public class WeatherDataParser {

    private Context context;

    public WeatherDataParser(Context context) {
        this.context = context;
    }

    /**
     * Given a string of the form returned by the api call:
     * http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7
     * retrieve the maximum temperature for the day indicated by dayIndex
     * (Note: 0-indexed, so 0 would refer to the first day).
     */

    public List<Temperature> parse(String weatherJsonStr)
            throws JSONException {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        PreferenceManager.setDefaultValues(context, R.xml.pref_general, false);
        String unitType = preferences.getString(context.getString(R.string.pref_units_key), context.getString(R.string.pref_units_metric));

        List<Temperature> temperatures = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(weatherJsonStr);
        JSONArray list = jsonObject.getJSONArray("list");

        for (int i = 0; i < list.length(); i++) {
            JSONObject object = list.getJSONObject(i);
            JSONObject temp = object.getJSONObject("temp");
            double max = temp.getDouble("max");
            double min = temp.getDouble("min");

            if (unitType.equals(context.getString(R.string.pref_units_imperial))) {
                max = (max * 1.8) + 32;
                min = (max * 1.8) + 32;
            } else if (!unitType.equals(context.getString(R.string.pref_units_imperial))) {
                Log.d("WeatherDataParser", "Unit type not found");
            }

            JSONArray weather = object.getJSONArray("weather");
            JSONObject weatherObj = weather.getJSONObject(0);
            String main = weatherObj.getString("main");

            temperatures.add(new Temperature(min, max, main));
        }

        return temperatures;
    }

}
