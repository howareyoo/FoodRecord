package com.jica.foodrecord;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import static com.jica.foodrecord.FoodDatabase.TAG;

public class MapInformationAdapter extends RecyclerView.Adapter<MapInformationAdapter.ViewHolder> implements OnMapInformationClickListener{

    ArrayList<FoodItem> items = new ArrayList<FoodItem>();

    OnMapInformationClickListener listener;


    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull   ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.map_information, viewGroup, false);

        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MapInformationAdapter.ViewHolder viewHolder, int position) {
        FoodItem item = items.get(position);
        viewHolder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }


    public ArrayList<FoodItem> getItems() {
        return items;
    }

    public FoodItem getItem(int position) {
        return items.get(position);
    }

    public void setItems(ArrayList<FoodItem> items) {
        this.items = items;
    }




    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }

    public void setItemClickListener(OnMapInformationClickListener listener){
        this.listener = listener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvMapInfo;


        public ViewHolder(@NonNull  View itemView, OnMapInformationClickListener listener) {
            super(itemView);

            tvMapInfo = itemView.findViewById(R.id.tvMapInfo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int position = getAdapterPosition();





                    if(listener !=null){
                        listener.onItemClick(MapInformationAdapter.ViewHolder.this, v, position);





                        Log.d(TAG, "clicked");

                    }



                }
            });

        }

        public void setItem(FoodItem item){
            tvMapInfo.setText(item.getLocation());
        }

    }




}
