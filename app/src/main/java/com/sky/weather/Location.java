package com.sky.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hac10 on 06/04/2016.
 */
public class Location implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeList(this.days);
    }

    protected Location(Parcel in) {
        this.name = in.readString();
        this.days = new ArrayList<Days>();
        in.readList(this.days, Days.class.getClassLoader());
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
