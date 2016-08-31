package com.github.jonss.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.jonss.sunshine.connection.FetchWeatherTask;
import com.github.jonss.sunshine.model.Temperature;

import org.json.JSONException;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ForecastFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<Temperature> adapter;

    //api.openweathermap.org/data/2.5/forecast/daily?lat=-23.1857&lon=-46.8978&cnt=7&APPID=43784c9f5d427de7798f1de398b62ca3

    public ForecastFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        listView = (ListView) view.findViewById(R.id.forecast_listview);
        loadWeather();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Clicado " + adapter.getItem(position), Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecast_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                loadWeather();
                Toast.makeText(getActivity(), "Refresh clicado!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadWeather() {
        FetchWeatherTask fetchWeatherTask = new FetchWeatherTask();
        AsyncTask<Void, Void, String> execute = fetchWeatherTask.execute();
        try {
            String s = execute.get();
            Log.d("WEATHER", s);

            WeatherDataParser parser = new WeatherDataParser();
            List<Temperature> temperatures = parser.parse(s);
            adapter = new ArrayAdapter<>(getActivity(), R.layout.forecast_item, temperatures);
            listView.setAdapter(adapter);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
