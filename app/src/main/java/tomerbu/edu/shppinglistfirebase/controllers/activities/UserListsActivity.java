package tomerbu.edu.shppinglistfirebase.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

import tomerbu.edu.shppinglistfirebase.R;
import tomerbu.edu.shppinglistfirebase.adapters.ShoppingListsAdapter;
import tomerbu.edu.shppinglistfirebase.controllers.fragments.AddListDialog;
import tomerbu.edu.shppinglistfirebase.models.ShoppingList;
import tomerbu.edu.shppinglistfirebase.models.User;

public class UserListsActivity extends BaseActivity {
    private RecyclerView rvUserLists;
    /**
     * firebase.getInstance()...
     */
    private DatabaseReference ref;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_lists);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setTitle("My Shopping Lists");

        updateUserStats();

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    // FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue()
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    currentUser = firebaseAuth.getCurrentUser();

                    ref = FirebaseDatabase.getInstance().getReference();
                    Toast.makeText(getApplicationContext(), "Hello, " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
                    initRecycler();
                }
            }
        });


        handleFab();


    }

    private void updateUserStats() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().
                getCurrentUser();
        if (currentUser == null || currentUser.getEmail() == null) return;

        String email = currentUser.getEmail();

        /* Create HashMaps of a connected/disconnected user */
        HashMap<String, Object> updateMapLogin = new HashMap<String, Object>();
        HashMap<String, Object> updateMapLogout = new HashMap<String, Object>();

        HashMap<String, Object> timeStamp = new HashMap<>();
        timeStamp.put("lastLogin", ServerValue.TIMESTAMP);

        HashMap<String, Object> logOutMap =
                new User( email, currentUser.getUid(), false).toMap();

        HashMap<String, Object> loginMap =
                new User(email, currentUser.getUid(), true).toMap();

        /* Add the user and UID to the update map */
        updateMapLogin.put("/users/" + currentUser.getUid(),
                loginMap);

        FirebaseDatabase.getInstance().getReference().
                updateChildren(updateMapLogin);

           /* Add the user and UID to the update map */
        updateMapLogout.put("/users/" + currentUser.getUid(),
                logOutMap);
        FirebaseDatabase.getInstance().getReference().
                onDisconnect().updateChildren(updateMapLogout);
    }

    private void handleFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddListDialog addListDialog = new AddListDialog();
                addListDialog.setCancelable(false);


                //ADD A List to userLists: uid->ListPid->listName, owner, timeCreated
                //ADD The list to lists: listPID simultaneously


                addListDialog.setDelegate(new AddListDialog.OnSaveClicked() {
                    @Override
                    public void onSaveClicked(String title) {
                        addListToFirebase(title);

                    }
                });
                addListDialog.show(getSupportFragmentManager(), "Tag");
            }
        });
    }

    private void addListToFirebase(String title) {
        String key = ref.child("userLists").child(currentUser.getUid()).push().getKey();

        HashMap<String, Object> newListMap = new ShoppingList(currentUser.getEmail(), title, key).toMap();
        Log.e(TAG, newListMap.toString()
        );

        Map<String, Object> childUpdateMap = new HashMap<String, Object>();

        childUpdateMap.put("userLists/" + currentUser.getUid() + "/" + key, newListMap);
        childUpdateMap.put("shoppingListItems/" + key + "/", newListMap);
        childUpdateMap.put("shoppingLists/" + key + "/", newListMap);


        ref.updateChildren(childUpdateMap);
    }

    private void initRecycler() {
        rvUserLists = (RecyclerView) findViewById(R.id.recyclerUserLists);

        Query rvQuery = ref.child("userLists").
                child(currentUser.getUid()).
                orderByChild("listName");

        ShoppingListsAdapter adapter = new ShoppingListsAdapter(
                ShoppingList.class, R.layout.lists_one, rvQuery
        );

        adapter.setOnItemClickListener(new ShoppingListsAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(ShoppingList model, String key) {
                Intent intent = new Intent(UserListsActivity.this, ShoppingListItemsActivity.class);
                intent.putExtra(ShoppingListItemsActivity.EXTRA_SHOPPING_LIST, model);
                startActivity(intent);
            }
        });
        rvUserLists.setAdapter(adapter);
    }
}
