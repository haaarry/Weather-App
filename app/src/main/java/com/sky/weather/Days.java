package com.sky.weather;

/**
 * Created by hac10 on 06/04/2016.
 */
public class Days {
    private String time;
    private String windSpeed;
    private String windDirection;

    public Days (String time, String windSpeed, String windDirection){
        this.time = time;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
    }

    public String getTime() {
        return time;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }
}
