package tomerbu.edu.shppinglistfirebase.controllers.activities;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tomerbu.edu.shppinglistfirebase.R;
import tomerbu.edu.shppinglistfirebase.adapters.AllUsersRecyclerAdapter;
import tomerbu.edu.shppinglistfirebase.models.User;

public class AllUsersActivity extends BaseActivity {

    private RecyclerView rvAllUsers;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        rvAllUsers = (RecyclerView) findViewById(R.id.allUsersRecycler);
        ref = FirebaseDatabase.getInstance().getReference("users");
        AllUsersRecyclerAdapter adapter = new AllUsersRecyclerAdapter(User.class, R.layout.lists_two, ref);

        rvAllUsers.setAdapter(adapter);
    }
}
