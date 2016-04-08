package com.sky.weather;

/**
 * Created by hac10 on 07/04/2016.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        ///String quant = "" +current.quantity;
        holder.dayTextView.setText(current.getTime());
        holder.windSpeedTextView.setText(current.getWindSpeed());
        holder.windDirectionTextView.setText(current.getWindDirection());


        //holder.windSpeedTextView.setImageResource(current.iconId)
        //holder.quantityTV.setText(quant);//Set to quantity//THIS COULD BREAK IT
       // holder.quantityTV.setText(current.quantity.toString());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        TextView dayTextView;
        TextView windSpeedTextView;
        TextView windDirectionTextView;
        //ImageView imageV;
        //TextView quantityTV;

        public MyViewHolder(View itemView) {
            super(itemView);
            dayTextView = (TextView) itemView.findViewById(R.id.forecastTimeTextView);
            windSpeedTextView =(TextView) itemView.findViewById(R.id.forecastWindSpeedTextView);
            windDirectionTextView =(TextView) itemView.findViewById(R.id.forecastWindDirectionTextView);

            //windSpeedTextView = (ImageView) itemView.findViewById(R.id.listIcon);
            //imageV.setOnLongClickListener(this);
           // quantityTV = (TextView) itemView.findViewById(R.id.itemQuantTextView);
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