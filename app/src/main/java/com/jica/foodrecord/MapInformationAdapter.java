package com.jica.foodrecord;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class MapInformationAdapter extends RecyclerView.Adapter<MapInformationAdapter.ViewHolder>{

    ArrayList<FoodItem> items = new ArrayList<FoodItem>();

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull   ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.map_information, viewGroup, false);

        return new ViewHolder(itemView);
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

    public void setItems(ArrayList<FoodItem> items) {
        this.items = items;
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvMapInfo;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            tvMapInfo = itemView.findViewById(R.id.tvMapInfo);

        }

        public void setItem(FoodItem item){
            tvMapInfo.setText(item.getLocation());
        }

    }




}
