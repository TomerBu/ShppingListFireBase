package tomerbu.edu.shppinglistfirebase.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import tomerbu.edu.shppinglistfirebase.R;
import tomerbu.edu.shppinglistfirebase.controllers.activities.FreindListActivity;
import tomerbu.edu.shppinglistfirebase.models.User;

/**
 * Created by tomerbuzaglo on 04/07/2016.
 * Copyright 2016 tomerbuzaglo. All Rights Reserved
 * <p/>
 * Licensed under the Apache License, Version 2.0
 * you may not use this file except
 * in compliance with the License
 */
public class AllUsersRecyclerAdapter extends FirebaseRecyclerAdapter<User, AllUsersRecyclerAdapter.AllUsersViewHolder> {




    public AllUsersRecyclerAdapter(Class<User> modelClass, int modelLayout, Query ref) {
        super(modelClass, modelLayout, AllUsersViewHolder.class, ref);
    }

    public AllUsersRecyclerAdapter(Class<User> modelClass, int modelLayout, DatabaseReference ref) {
        super(modelClass, modelLayout, AllUsersViewHolder.class, ref);
    }

    @Override
    protected void populateViewHolder(AllUsersViewHolder holder, final User model, final int position) {
        holder.populate(model);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = getRef(position).getKey();

                DatabaseReference userFriendsRef = FirebaseDatabase.getInstance().getReference().child("userFriends").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                userFriendsRef.child(key).setValue(model);
                Intent intent = new Intent(view.getContext(), FreindListActivity.class);
                view.getContext().startActivity(intent);

            }
        });
    }

    public static class AllUsersViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;

        public AllUsersViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.listName);
        }

        public void populate(User model) {
            tvTitle.setText(model.getEmail());
        }
    }
}
