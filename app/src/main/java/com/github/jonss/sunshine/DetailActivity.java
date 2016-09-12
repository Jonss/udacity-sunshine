package com.github.jonss.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private String weatherStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        weatherStr = intent.getStringExtra(Intent.EXTRA_TEXT);

        TextView tempTextView = (TextView) findViewById(R.id.detail_text);
        tempTextView.setText(weatherStr);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.action_map:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                String cep = preferences.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));

                Uri parse = Uri.parse("geo:0,0?q=" + cep.substring(0, 5));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(parse);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
            case R.id.action_share:
                ShareActionProvider provider = new ShareActionProvider(this);
                provider.setShareIntent(createShareForecastIntent());

                MenuItemCompat.setActionProvider(item, provider);
        }

        return super.onOptionsItemSelected(item);
    }


    private Intent createShareForecastIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, weatherStr + " #Sunshine");
        return intent;
    }

}
