package team.hashbash.sangarodhak.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import team.hashbash.sangarodhak.Fragments.FragmentSupplies;
import team.hashbash.sangarodhak.Modals.SupplyItemsDataModal;
import team.hashbash.sangarodhak.R;

import java.util.ArrayList;


public class SuppliesRecyclerAdapter extends RecyclerView.Adapter<SuppliesRecyclerAdapter.AnnouncementViewHolder> {
    private Context context;

    private ArrayList<SupplyItemsDataModal> allItems;

    public SuppliesRecyclerAdapter(Context context, ArrayList<SupplyItemsDataModal> allItems) {
        this.context = context;
        this.allItems = allItems;
    }

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_supplies_items, parent, false);
        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AnnouncementViewHolder holder, final int position) {

        final SupplyItemsDataModal thisItem = allItems.get(position);

        holder.itemName.setText(thisItem.getItemName());
        holder.itemRate.setText(String.format("₹ %d per %s", thisItem.getItemRate(), thisItem.getQuantityType()));

        holder.quantityBuy.setText(String.format("%d %s", holder.quantity, thisItem.getQuantityType()));
        holder.quantityPrice.setText(String.format("₹ %d", holder.quantity * thisItem.getItemRate()));

        holder.itemView.findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.quantity < thisItem.getQuantityAvailable())
                    holder.quantity++;
                holder.quantityBuy.setText(String.format("%d %s", holder.quantity, thisItem.getQuantityType()));
                holder.quantityPrice.setText(String.format("₹ %d", holder.quantity * thisItem.getItemRate()));
            }
        });
        holder.itemView.findViewById(R.id.button_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.quantity > 1)
                    holder.quantity--;
                holder.quantityBuy.setText(String.format("%d %s", holder.quantity, thisItem.getQuantityType()));
                holder.quantityPrice.setText(String.format("₹ %d", holder.quantity * thisItem.getItemRate()));
            }
        });
        holder.itemView.findViewById(R.id.button_tick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentSupplies.allItemsAddedInCart.add(new SupplyItemsDataModal( thisItem.getItemName(), thisItem.getQuantityType(), thisItem.getQuantityAvailable(), thisItem.getItemRate(), holder.quantity));
                FragmentSupplies.updateCartDetails();
                FragmentSupplies.cartRecyclerView.setAdapter(new CartRecyclerAdapter(context, FragmentSupplies.allItemsAddedInCart));
            }
        });
    }

    @Override
    public int getItemCount() {
        return allItems.size();
    }

    static class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemRate, quantityBuy, quantityPrice;
        int quantity = 1;

        AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.item_name);
            itemRate = itemView.findViewById((R.id.item_rate));
            quantityBuy = itemView.findViewById(R.id.item_quantity);
            quantityPrice = itemView.findViewById(R.id.item_quantity_price);

        }
    }
}
