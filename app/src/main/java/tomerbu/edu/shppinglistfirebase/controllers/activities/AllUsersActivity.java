package tomerbu.edu.shppinglistfirebase.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tomerbu.edu.shppinglistfirebase.R;
import tomerbu.edu.shppinglistfirebase.adapters.AllUsersRecyclerAdapter;
import tomerbu.edu.shppinglistfirebase.models.ShoppingList;
import tomerbu.edu.shppinglistfirebase.models.User;

public class AllUsersActivity extends BaseActivity {

    private RecyclerView rvAllUsers;
    private DatabaseReference ref;
    private ShoppingList shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        rvAllUsers = (RecyclerView) findViewById(R.id.allUsersRecycler);
        ref = FirebaseDatabase.getInstance().getReference("users");
        AllUsersRecyclerAdapter adapter = new AllUsersRecyclerAdapter(User.class, R.layout.lists_two, ref);
        shoppingList = getIntent().getParcelableExtra(ShoppingListItemsActivity.EXTRA_SHOPPING_LIST);

        adapter.setOnUserAddedAsFriendListener(new AllUsersRecyclerAdapter.OnUserAddedAsFriendListener() {
            @Override
            public void onUserAddedAsFreind(User user, String key) {

                DatabaseReference userFriendsRef =
                        FirebaseDatabase.getInstance().getReference().
                                child("userFriends").
                                child(getCurrentUser().getUid());

                //HashMap<String, Object> shoppingListMap = shoppingList.toMap();


                //  Query rvQuery = ref.child("userLists").
                //child(currentUser.getUid()).
                  //      orderByChild("listName");
                //TODO: Friend request instead of just adding the list to the friend
                FirebaseDatabase.getInstance().getReference().child("userLists").child(user.getUID()).child(shoppingList.getUID()).setValue(shoppingList);

                userFriendsRef.child(key).setValue(user);

                Intent intent = new Intent(AllUsersActivity.this, FreindListActivity.class);
                startActivity(intent);
            }
        });
        rvAllUsers.setAdapter(adapter);
    }
}
