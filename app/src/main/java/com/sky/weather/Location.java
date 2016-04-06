package com.sky.weather;

/**
 * Created by hac10 on 06/04/2016.
 */
public class Location {
    private String name;
    private String days;

    public Location(String name, String days){
        this.name = name;
        this.days = days;
    }

    public String getName(){
        return name;
    }

    public String getDays(){
        return days;
    }

}
