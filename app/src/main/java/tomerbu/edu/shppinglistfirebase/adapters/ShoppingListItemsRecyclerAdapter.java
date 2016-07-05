package tomerbu.edu.shppinglistfirebase.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import tomerbu.edu.shppinglistfirebase.R;
import tomerbu.edu.shppinglistfirebase.models.ShoppingListItem;

/**
 * Created by tomerbuzaglo on 27/06/2016.
 */
public class ShoppingListItemsRecyclerAdapter extends FirebaseRecyclerAdapter<ShoppingListItem, ShoppingListItemsRecyclerAdapter.ShoppingViewHolder> {

    public ShoppingListItemsRecyclerAdapter(Class<ShoppingListItem> modelClass, int modelLayout, Query ref) {
        super(modelClass, modelLayout, ShoppingViewHolder.class, ref);
    }

    public ShoppingListItemsRecyclerAdapter(Class<ShoppingListItem> modelClass, int modelLayout, DatabaseReference ref) {
        super(modelClass, modelLayout, ShoppingViewHolder.class, ref);
    }

    @Override
    protected void populateViewHolder(ShoppingViewHolder holder, ShoppingListItem model, final int position) {
        holder.use(model);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference ref = getRef(position);
                String key = ref.getKey();
                Toast.makeText(view.getContext(), key, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class ShoppingViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemName;
        private TextView tvItemQuantity;
        private ShoppingListItem model;

        public ShoppingViewHolder(View itemView) {
            super(itemView);
            tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);
            tvItemQuantity = (TextView) itemView.findViewById(R.id.tvItemQuantity);

        }

        private void setItemName(String name) {
            tvItemName.setText(name);
        }

        private void setItemQuantity(String quantity) {
            tvItemQuantity.setText(quantity);
        }

        public void use(ShoppingListItem model) {
            setItemName(model.getTitle());
            setItemQuantity(model.getQuantity());
            this.model = model;
        }
    }
}
