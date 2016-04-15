package com.sky.weather;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavouritesActivity extends AppCompatActivity implements ParseJsonResponse, RecyclerAdapter.ItemClickListener {

    List<String> favourtieCityTitles = new ArrayList<String>();

    List<Location> favouriteLocations = new ArrayList<Location>();
    List<Days> favouritesCurrentDays = new ArrayList<Days>();
    RecyclerView favouritesRecycler;
    RecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        setLists();

        setView();

    }

    public void setView(){
        favouritesRecycler = (RecyclerView) findViewById(R.id.favouritesRecycler);
        adapter = new RecyclerAdapter(this, favouritesCurrentDays, R.layout.favourite_view, favourtieCityTitles);
        adapter.setClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        favouritesRecycler.setAdapter(adapter);
        favouritesRecycler.setLayoutManager(linearLayoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.options_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public void parsedJson(Location output) {
        favouriteLocations.add(output);
        Days currentDay = output.getDays().get(0);
        favouritesCurrentDays.add(currentDay);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateLists();
    }

    @Override
    public void onLongClickItem(String cityTitle, int position) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(cityTitle);
        editor.commit();

        favouritesCurrentDays.remove(position);
        favourtieCityTitles.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onClickItem(String cityTitle) {
        Intent intent = new Intent(this, SearchResultsActivity.class);

        intent.putExtra("cityTitle", cityTitle);
        startActivity(intent);
    }

    public void setLists(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Map<String, ?> allEntries = sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()){
                favourtieCityTitles.add(entry.getKey());
                ParseJsonAsync parseJsonAsync = new ParseJsonAsync(this);
                parseJsonAsync.execute(entry.getValue().toString());
        }
    }

    public void updateLists(){
        favouriteLocations.clear();
        favourtieCityTitles.clear();
        favouritesCurrentDays.clear();
        setLists();
    }

}
