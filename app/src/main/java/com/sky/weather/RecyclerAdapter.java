package com.sky.weather;

/**
 * Created by hac10 on 07/04/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by hac10 on 27/09/15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    public interface ItemClickListener {
        void onLongClickItem(String cityTitle, int position);
        void onClickItem(String cityTitle);
    }

    private LayoutInflater inflater;
    List<Days> data = Collections.emptyList();
    Context context;
    int layout;
    List<String> cityTitle;
    private ItemClickListener clickListener = null;

    public RecyclerAdapter(Context context, List<Days> data, int layout, List<String> cityTitle){
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
        this.layout = layout;
        this.cityTitle = cityTitle;
    }

    public void setClickListener(ItemClickListener listener){
        this.clickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Days current = data.get(position);
        holder.dayTextView.setText(current.getTime());
        holder.windSpeedTextView.setText(current.getWindSpeed());

        if(cityTitle !=null){
            holder.forecastCityTitleTextView.setText(cityTitle.get(position));
        }

            final RotateAnimation rotateAnimation = new RotateAnimation(0.0f, Float.parseFloat(current.getWindDirection()),
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);

            rotateAnimation.setDuration(1000);
            //animSet.addAnimation(rotateAnimation);

            rotateAnimation.setRepeatCount(0);
            rotateAnimation.setFillAfter(true);
            holder.minCompassPointer.startAnimation(rotateAnimation);

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null){
                    clickListener.onClickItem(cityTitle.get(position));
                }
            }
        });

        holder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(clickListener != null){
                    clickListener.onLongClickItem(cityTitle.get(position), position);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        View rootView;
        TextView dayTextView;
        TextView windSpeedTextView;
        ImageView minCompassPointer;
        TextView forecastCityTitleTextView;


        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            dayTextView = (TextView) itemView.findViewById(R.id.forecastTimeTextView);
            windSpeedTextView =(TextView) itemView.findViewById(R.id.forecastWindSpeedTextView);
            minCompassPointer = (ImageView) itemView.findViewById(R.id.minCompassPointerImageView);
            if(cityTitle !=null){
                forecastCityTitleTextView = (TextView) itemView.findViewById(R.id.favouritesCityTitleTextView);
            }
        }
    }
}