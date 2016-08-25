package com.github.jonss.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.jonss.sunshine.connection.FetchWeatherTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ForecastFragment extends Fragment {

    //43784c9f5d427de7798f1de398b62ca3
    // http://api.openweathermap.org/data/2.5/find?lat=-23.1857&lon=-46.8978&mode=json&units=celsius&APPID=43784c9f5d427de7798f1de398b62ca3&cnt=7
    //"http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7"
    public ForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        String[] forecast = {"Hoje - Sol 22-29", "Amanhã - Chuva 12-19", "Segunda - chuva 9-22"};

        List<String> list = Arrays.asList(forecast);

        ListView listView = (ListView) view.findViewById(R.id.forecast_listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.forecast_item, list);
        listView.setAdapter(adapter);

        FetchWeatherTask fetchWeatherTask = new FetchWeatherTask();
        AsyncTask<Void, Void, String> execute = fetchWeatherTask.execute();
        try {
            String s = execute.get();
            Log.d("WEATHER", s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return view;
    }
}
