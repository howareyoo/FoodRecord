package com.jica.foodrecord;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import static com.jica.foodrecord.FoodDatabase.TAG;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHolder> implements OnTimeLineSummaryClickListener{
    ArrayList<FoodItem> items = new ArrayList<FoodItem>();

    OnTimeLineSummaryClickListener listener;


    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.timeline_summary, viewGroup, false);

        return new ViewHolder(itemView,listener);
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



    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }


    public  void setItemClickListener(OnTimeLineSummaryClickListener listener){
        this.listener = listener;
    }


    public void deleteItem(int position){



        items.remove(position);
        notifyItemRemoved(position);


    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTimeLineDate;
        TextView tvTitle;
        TextView tvLocation;



        public ViewHolder(@NonNull View itemView, OnTimeLineSummaryClickListener listener) {
            super(itemView);

            tvTimeLineDate = itemView.findViewById(R.id.tvTimeLineDate);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvLocation = itemView.findViewById(R.id.tvLocation);



            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(listener !=null){
                        listener.onItemClick(ViewHolder.this, v, position);

                        Log.d(TAG, "clicked");

                    }
                }
            });


        }


        public void setItem(FoodItem item){
            tvTimeLineDate.setText(item.getDate());
            tvTitle.setText(item.getTitle());
            tvLocation.setText(item.getLocation());

        }


    }


}





/*    itemView.findViewById(R.id.ivDeleteData).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    onItemSwipe(position);

                    Toast.makeText(itemView.getContext(), "clicked", Toast.LENGTH_LONG ).show();
                }
            });*/