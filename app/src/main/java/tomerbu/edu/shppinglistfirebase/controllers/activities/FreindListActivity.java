package tomerbu.edu.shppinglistfirebase.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tomerbu.edu.shppinglistfirebase.R;
import tomerbu.edu.shppinglistfirebase.adapters.FreindListRecyclerAdapter;
import tomerbu.edu.shppinglistfirebase.models.ShoppingList;
import tomerbu.edu.shppinglistfirebase.models.User;

public class FreindListActivity extends BaseActivity {
    //public final static String EXREA_ADD_USER_TO_LIST = "addUserToList";
    private RecyclerView rvFreinds;
    private ShoppingList shoppingList;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freind_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ref = FirebaseDatabase.getInstance().getReference().child("userFriends").child(getCurrentUser().getUid());
        rvFreinds = (RecyclerView) findViewById(R.id.userFriendsRecycler);
        rvFreinds.setAdapter(new FreindListRecyclerAdapter(User.class, R.layout.lists_two, ref));


        shoppingList = getIntent().getParcelableExtra(ShoppingListItemsActivity.EXTRA_SHOPPING_LIST);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AllUsersActivity.class);
                intent.putExtra(ShoppingListItemsActivity.EXTRA_SHOPPING_LIST, shoppingList);
                User user = new User(getCurrentUser().getEmail(), getCurrentUser().getUid(), true);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
    }

}
