package tomerbu.edu.shppinglistfirebase.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import tomerbu.edu.shppinglistfirebase.R;
import tomerbu.edu.shppinglistfirebase.models.User;

/**
 * Created by tomerbuzaglo on 04/07/2016.
 * Copyright 2016 tomerbuzaglo. All Rights Reserved
 * <p/>
 * Licensed under the Apache License, Version 2.0
 * you may not use this file except
 * in compliance with the License
 */
public class FreindListRecyclerAdapter extends FirebaseRecyclerAdapter<User, FreindListRecyclerAdapter.UserViewHolder> {
    public FreindListRecyclerAdapter(Class<User> modelClass, int modelLayout, Query ref) {
        super(modelClass, modelLayout, UserViewHolder.class, ref);
    }

    public FreindListRecyclerAdapter(Class<User> modelClass, int modelLayout, DatabaseReference ref) {
        super(modelClass, modelLayout, UserViewHolder.class, ref);
    }

    @Override
    protected void populateViewHolder(UserViewHolder viewHolder, User model, int position) {
        viewHolder.populate(model);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFriendEmail;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvFriendEmail = (TextView) itemView.findViewById(R.id.listName);
        }

        public void populate(User model) {
            tvFriendEmail.setText(model.getEmail());
        }
    }
}
