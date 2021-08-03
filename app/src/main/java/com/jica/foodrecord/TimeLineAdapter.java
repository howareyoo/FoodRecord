package com.jica.foodrecord;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHolder> implements OnTimeLineSummaryClickListener{
    ArrayList<FoodItem> items = new ArrayList<FoodItem>();

    OnTimeLineSummaryClickListener listener;

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.timeline_summary, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull  TimeLineAdapter.ViewHolder holder, int position) {
        FoodItem item = items.get(position);
        holder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();

    }


    public void addItem(FoodItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<FoodItem> items) {
        this.items = items;
    }

    public FoodItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, FoodItem item) {
        items.set(position, item);
    }



    public void OnTimeLineSummaryClickListener (OnTimeLineSummaryClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTimeLineDate;
        TextView tvTitle;
        TextView tvContents;
        TextView tvLocation;

        public ViewHolder(@NonNull View itemView, final OnTimeLineSummaryClickListener listener) {
            super(itemView);

            tvTimeLineDate = itemView.findViewById(R.id.tvTimeLineDate);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContents = itemView.findViewById(R.id.tvContents);
            tvLocation = itemView.findViewById(R.id.tvLocation);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(listener !=null){
                        listener.onItemClick(ViewHolder.this, v, position);

                    }
                }
            });

        }

        public void setItem(FoodItem item){
            tvTimeLineDate.setText(item.getDate());
            tvTitle.setText(item.getTitle());
            tvContents.setText(item.getContents());
            tvLocation.setText(item.getLocation());

        }


    }


}