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

    private LayoutInflater inflater;
    List<Days> data = Collections.emptyList();
    Context context;
    public RecyclerAdapter(Context context, List<Days> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.forecast_view, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Days current = data.get(position);
        holder.dayTextView.setText(current.getTime());
        holder.windSpeedTextView.setText(current.getWindSpeed());

            final RotateAnimation rotateAnimation = new RotateAnimation(0.0f, Float.parseFloat(current.getWindDirection()),
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);

            rotateAnimation.setDuration(1000);
            //animSet.addAnimation(rotateAnimation);

            rotateAnimation.setRepeatCount(0);
            rotateAnimation.setFillAfter(true);
            holder.minCompassPointer.startAnimation(rotateAnimation);


    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        TextView dayTextView;
        TextView windSpeedTextView;
        ImageView minCompassPointer;

        public MyViewHolder(View itemView) {
            super(itemView);
            dayTextView = (TextView) itemView.findViewById(R.id.forecastTimeTextView);
            windSpeedTextView =(TextView) itemView.findViewById(R.id.forecastWindSpeedTextView);
            minCompassPointer = (ImageView) itemView.findViewById(R.id.minCompassPointerImageView);
        }

//        @Override
//        public void onClick(View v) {
//            Toast.makeText(context, ""+getPosition(), Toast.LENGTH_SHORT).show();
//        }

        @Override
        public boolean onLongClick(View v) {


            return false;
        }
    }
}