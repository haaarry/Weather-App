package com.sky.weather;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hac10 on 06/04/2016.
 */
public class Days implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.time);
        dest.writeString(this.windSpeed);
        dest.writeString(this.windDirection);
    }

    protected Days(Parcel in) {
        this.time = in.readString();
        this.windSpeed = in.readString();
        this.windDirection = in.readString();
    }

    public static final Parcelable.Creator<Days> CREATOR = new Parcelable.Creator<Days>() {
        @Override
        public Days createFromParcel(Parcel source) {
            return new Days(source);
        }

        @Override
        public Days[] newArray(int size) {
            return new Days[size];
        }
    };
}
