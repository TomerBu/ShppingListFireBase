package tomerbu.edu.shppinglistfirebase.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import tomerbu.edu.shppinglistfirebase.R;
import tomerbu.edu.shppinglistfirebase.models.ShoppingList;

/**
 * Created by tomerbuzaglo on 30/06/2016.
 * Copyright 2016 tomerbuzaglo. All Rights Reserved
 * <p/>
 * Licensed under the Apache License, Version 2.0
 * you may not use this file except
 * in compliance with the License
 */
public class ShoppingListsAdapter extends FirebaseRecyclerAdapter<ShoppingList, ShoppingListsAdapter.ShopListsViewHolder> {
    private OnItemClickListener listener;

    public ShoppingListsAdapter(Class<ShoppingList> modelClass, int modelLayout, Query ref) {
        super(modelClass, modelLayout, ShopListsViewHolder.class, ref);
        if (getItemCount() == 0) {
        }//no lists yet
    }

    @Override
    protected void populateViewHolder(ShopListsViewHolder holder, final ShoppingList model,
                                      final int position) {
        holder.use(model);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference ref = getRef(position);
                String key = ref.getKey();
                if (listener!=null){
                    listener.onItemClicked(model, key);
                }
            }
        });
    }

    public static class ShopListsViewHolder extends RecyclerView.ViewHolder {
        private TextView tvListName;

        public ShopListsViewHolder(View itemView) {
            super(itemView);
            tvListName = (TextView) itemView.findViewById(R.id.listName);
        }


        public void use(ShoppingList model) {
            tvListName.setText(model.getListName());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public interface OnItemClickListener {
        public void onItemClicked(ShoppingList model, String key);

    }
}
