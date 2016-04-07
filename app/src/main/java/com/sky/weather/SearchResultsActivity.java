package com.sky.weather;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity implements WeatherApiResponse {

    private TextView windSpeedTextView;
    private TextView windDirectionTextView;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        handleIntent(getIntent());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        windSpeedTextView = (TextView) findViewById(R.id.windSpeedTextView);
        windDirectionTextView = (TextView) findViewById(R.id.windDirectionTextView);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            WeatherApiAsync weatherApiAsync = new WeatherApiAsync(this);
            weatherApiAsync.execute(query);
        }
    }

    @Override
    public void returnedData(Location output) {
       // Toast.makeText(getApplicationContext(), output.getDays().get(2).getTime(), Toast.LENGTH_SHORT).show();



        String cityTitle = output.getName();

        List<Days> forecast = output.getDays();

        setTitle(cityTitle);
        windSpeedTextView.setText(forecast.get(0).getWindSpeed().toString());
        windDirectionTextView.setText(forecast.get(0).getWindDirection().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.detail_menu, menu);

        return true;
    }
}
