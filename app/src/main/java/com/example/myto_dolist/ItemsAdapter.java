package com.example.myto_dolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Responsible for displaying data from the model into a row in the recycler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{

    public interface  OnClickListener{
        void onItemClicked(int position);
    }

    public interface OnLongClickListener{
        // the class needs to know the position to so it can notify the adapter that that is the
            // position where the item is to be deleted
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;
    OnClickListener clickListener;

    // the constructor; Get the data that we need
    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener,
                        OnClickListener onClickListener) {
        //Set member variable to the variable passed in through the constructor
        this.items = items;
        this.longClickListener = longClickListener;
        this.clickListener = onClickListener;
    }

    @NonNull
    @Override
    // Creates each view and wraps it inside of a viewholder
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use layout inflator to inflate a view
        // pass in xml file you're using inside of "inflate()"
            // Used the basic "simple_list_item_1" that is a built-in android resource
        View toDoView =
                LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

        //wrap it inside view, and return it
        return new ViewHolder(toDoView);
    }

    @Override
    //Takes data a certain position and binds it to a certain a view holder
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Grab item at the position
        String item = items.get(position);

        // Bind the item into the specified view holder
        holder.bind(item); // method ".bind" writes inside the ViewHolder class
    }

    @Override
    // The number of items available in the list
    public int getItemCount() {
        return items.size();
    }

    //Container to provide easy access to views that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        // Update the view inside fo the the view holder with this data
            // by getting a reference to the view which we can access inside of bind
        public void bind(String item) {
            tvItem.setText(item);

            // What to do if the user quick taps on the item
            tvItem.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    clickListener.onItemClicked(getAdapterPosition());
                }
            });


            // What to do if the user holds down on an item
            tvItem.setOnLongClickListener(new View.OnLongClickListener(){

                @Override
                public boolean onLongClick(View v) {
                    // Notify the listener which position was long pressed
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true; // the callback is consuming the long click
                }
            });
        }
    }
}
