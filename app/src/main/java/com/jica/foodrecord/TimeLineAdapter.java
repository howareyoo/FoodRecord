package com.jica.foodrecord;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHolder> implements OnTimeLineSummaryClickListener{
    ArrayList<TimeLineSummary> items = new ArrayList<TimeLineSummary>();

    OnTimeLineSummaryClickListener listener;

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.timeline_summary, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull  TimeLineAdapter.ViewHolder viewHolder, int position) {
        TimeLineSummary item = items.get(position);
        viewHolder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTimeLineDate;
        TextView tvShowSummary;

        public ViewHolder(@NonNull View itemView, final OnTimeLineSummaryClickListener listener) {
            super(itemView);

            tvTimeLineDate = itemView.findViewById(R.id.tvTimeLineDate);
            tvShowSummary = itemView.findViewById(R.id.tvShowSummary);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    if(listener !=null){
                        listener.onItemClick(ViewHolder.this, v, pos);

                    }
                }
            });



        }

        public void setItem(TimeLineSummary item){
            tvTimeLineDate.setText(item.getTvTimeLineDate());
            tvShowSummary.setText(item.getTvShowSummary());



        }

    }

    public void addItem(TimeLineSummary item){
        items.add(item);
    }

    public void setItems(ArrayList<TimeLineSummary> items){
        this.items = items;
    }

    public TimeLineSummary getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, TimeLineSummary item){
        items.set(position,item);
    }

}