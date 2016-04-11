package com.sky.weather;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity implements WeatherApiResponse {

    private TextView windSpeedTextView;
    private TextView windDirectionTextView;
    private TextView currentDateTextView;
    private TextView cityNameTextView;
    private RecyclerView forecastRecyclerView;
    private  RecyclerAdapter adapter;
    private ImageView windSpeedImageView;
    private ImageView compassBgImageView;
    private ImageView compassPointImageView;


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


        setTitle("");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        cityNameTextView = (TextView) findViewById(R.id.cityNameTextView);
        windSpeedTextView = (TextView) findViewById(R.id.windSpeedTextView);
        windDirectionTextView = (TextView) findViewById(R.id.windDirectionTextView);
        currentDateTextView = (TextView) findViewById(R.id.currentDateTextView);
        forecastRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        //windSpeedImageView = (ImageView) findViewById(R.id.imageView);
        compassBgImageView = (ImageView) findViewById(R.id.compassBackground);
        compassPointImageView = (ImageView) findViewById(R.id.compass_pointer);
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

       // String temp [] = new String [] {"temp 1", "temp 2", "temp 3", "temp 4", "temp 5"};

        windSpeedTextView.setText(forecast.get(0).getWindSpeed());
        windDirectionTextView.setText(forecast.get(0).getWindDirection());

        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, temp);



        adapter = new RecyclerAdapter(this, forecast);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        forecastRecyclerView.setAdapter(adapter);
        forecastRecyclerView.setLayoutManager(linearLayoutManager);
        currentDateTextView.setText(getString(R.string.last_updated) + forecast.get(0).getTime());
        cityNameTextView.setText(cityTitle);

        setCompass(Float.parseFloat(forecast.get(0).getWindDirection()));
        //forecastRecyclerView.setAdapter();

        //forecastRecyclerView.setAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.detail_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return false;
        }
    }

    public void setCompass(float direction){
        AnimationSet animSet = new AnimationSet(true);
        //animSet.setInterpolator(new DecelerateInterpolator());

        final RotateAnimation rotateAnimation = new RotateAnimation(0.0f, direction,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setDuration(1000);
        //animSet.addAnimation(rotateAnimation);

        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setFillAfter(true);
        compassPointImageView.startAnimation(rotateAnimation);
    }
}
