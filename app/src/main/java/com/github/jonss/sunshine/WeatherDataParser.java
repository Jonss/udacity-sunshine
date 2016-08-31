package com.github.jonss.sunshine;

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

    /**
     * Given a string of the form returned by the api call:
     * http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7
     * retrieve the maximum temperature for the day indicated by dayIndex
     * (Note: 0-indexed, so 0 would refer to the first day).
     */

    public List<Temperature> parse(String weatherJsonStr)
            throws JSONException {

        List<Temperature> temperatures = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(weatherJsonStr);
        JSONArray list = jsonObject.getJSONArray("list");

        for (int i = 0; i < list.length(); i++) {
            JSONObject object = list.getJSONObject(i);
            JSONObject temp = object.getJSONObject("temp");
            double max = temp.getDouble("max");
            double min = temp.getDouble("min");

            JSONArray weather = object.getJSONArray("weather");
            JSONObject weatherObj = weather.getJSONObject(0);
            String main = weatherObj.getString("main");

            temperatures.add(new Temperature(min, max, main));
        }

        return temperatures;
    }

}
