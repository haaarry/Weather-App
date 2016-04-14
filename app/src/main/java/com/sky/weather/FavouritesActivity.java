package com.sky.weather;

import android.app.SearchManager;
import android.content.Context;
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

public class FavouritesActivity extends AppCompatActivity implements ParseJsonResponse, FavouritesLongClickListener{

    List<String> faves = new ArrayList<String>();

    List<Location> favouriteLocations = new ArrayList<Location>();
    List<Days> favouritesCurrentDays = new ArrayList<Days>();
    RecyclerView favouritesRecycler;
    RecyclerAdapter adapter;


    //ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        //listView = (ListView) findViewById(R.id.favouritesListView);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Map<String, ?> allEntries = sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()){
            faves.add(entry.getKey());
            ParseJsonAsync parseJsonAsync = new ParseJsonAsync(this);
            parseJsonAsync.execute(entry.getValue().toString());
        }

        setView();
    }

    public void setView(){
        favouritesRecycler = (RecyclerView) findViewById(R.id.favouritesRecycler);
        adapter = new RecyclerAdapter(this, favouritesCurrentDays, R.layout.favourite_view, faves);

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

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onLongClickItem(String cityTitle, int position) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(cityTitle);
        editor.commit();

        favouritesCurrentDays.remove(position);
        faves.remove(position);
        adapter.notifyItemRemoved(position);

        //adapter.notifyDataSetChanged();
    }
}
