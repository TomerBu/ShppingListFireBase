package tomerbu.edu.shppinglistfirebase.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tomerbu.edu.shppinglistfirebase.R;
import tomerbu.edu.shppinglistfirebase.adapters.ShoppingListItemsRecyclerAdapter;
import tomerbu.edu.shppinglistfirebase.controllers.fragments.AddListDialog;
import tomerbu.edu.shppinglistfirebase.models.ShoppingList;
import tomerbu.edu.shppinglistfirebase.models.ShoppingListItem;

public class ShoppingListItemsActivity extends BaseActivity {
    public static final String EXTRA_SHOPPING_LIST = "edu.tomerbu.shoppingList";

    private RecyclerView recycler;
    private DatabaseReference ref;
    private ActionBar actionBar;
    String uid;
    private ShoppingList shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        shoppingList = getIntent().getParcelableExtra(EXTRA_SHOPPING_LIST);
        uid = shoppingList.getUID();

        assert shoppingList != null && actionBar != null;


        recycler = (RecyclerView) findViewById(R.id.shoppingListItemsRecycler);

        ref = FirebaseDatabase.getInstance().getReference("shoppingListItems/").child(uid).child("items");

        actionBar.setTitle("");
        setListTitle();


        recycler.setAdapter(new ShoppingListItemsRecyclerAdapter(ShoppingListItem.class, R.layout.shopping_list_item, ref));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddItemToShoppingList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddListDialog().setDelegate(new AddListDialog.OnSaveClicked() {
                    @Override
                    public void onSaveClicked(String title) {
                        ref.push().setValue(new ShoppingListItem(title, "1", getCurrentUser().getEmail()));
                    }
                }).show(getSupportFragmentManager(), "Add");
            }
        });
    }

    private void setListTitle() {
        if (actionBar != null && ref != null) {
            actionBar.setTitle(shoppingList.getListName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shopping_list_items_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_edit_list_name:
                return true;
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                return true;
            case R.id.action_share:
                shareList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareList() {
        Intent intent = new Intent(this, FreindListActivity.class);
        intent.putExtra(EXTRA_SHOPPING_LIST, shoppingList);

        startActivity(intent);
    }
}
