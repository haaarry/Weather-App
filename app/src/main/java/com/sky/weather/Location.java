package com.sky.weather;

import java.util.List;

/**
 * Created by hac10 on 06/04/2016.
 */
public class Location {
    private String name;
    private List <Days> days;

    public Location(String name, List<Days> days){
        this.name = name;
        this.days = days;
    }

    public String getName(){
        return name;
    }

    public List<Days> getDays(){
        return days;
    }

}
