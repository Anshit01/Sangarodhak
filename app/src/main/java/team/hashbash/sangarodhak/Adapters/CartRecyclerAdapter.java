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

import java.text.MessageFormat;
import java.util.ArrayList;


public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.AnnouncementViewHolder> {
    private Context context;

    private ArrayList<SupplyItemsDataModal> allItems;

    public CartRecyclerAdapter(Context context, ArrayList<SupplyItemsDataModal> allItems) {
        this.context = context;
        this.allItems = allItems;
    }

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_cart_items, parent, false);
        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AnnouncementViewHolder holder, final int position) {

        final SupplyItemsDataModal thisItem = allItems.get(position);

        holder.itemName.setText(thisItem.getItemName());
        holder.itemRate.setText(String.format("₹ %d per %s", thisItem.getItemRate(), thisItem.getQuantityType()));

        holder.quantityBuy.setText(MessageFormat.format("{0}{1}", thisItem.getQuantityBought(), thisItem.getQuantityType()));
        holder.quantityPrice.setText(String.format("₹ %d", thisItem.getQuantityBought() * thisItem.getItemRate()));

        holder.itemView.findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thisItem.getQuantityBought() < thisItem.getQuantityAvailable())
                    thisItem.setQuantityBought(thisItem.getQuantityBought() + 1);
                holder.quantityBuy.setText(String.format("%d %s", thisItem.getQuantityBought(), thisItem.getQuantityType()));
                holder.quantityPrice.setText(String.format("₹ %d", thisItem.getQuantityBought() * thisItem.getItemRate()));
                FragmentSupplies.updateCartDetails();
            }
        });
        holder.itemView.findViewById(R.id.button_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thisItem.getQuantityBought() > 1)
                    thisItem.setQuantityBought(thisItem.getQuantityBought() - 1);
                holder.quantityBuy.setText(String.format("%d %s", thisItem.getQuantityBought(), thisItem.getQuantityType()));
                holder.quantityPrice.setText(String.format("₹ %d", thisItem.getQuantityBought() * thisItem.getItemRate()));
                FragmentSupplies.updateCartDetails();
            }
        });
        holder.itemView.findViewById(R.id.button_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allItems.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                FragmentSupplies.updateCartDetails();
            }
        });

    }

    @Override
    public int getItemCount() {
        return allItems.size();
    }

    static class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemRate, quantityBuy, quantityPrice;

        AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.item_name);
            itemRate = itemView.findViewById((R.id.item_rate));
            quantityBuy = itemView.findViewById(R.id.item_quantity);
            quantityPrice = itemView.findViewById(R.id.item_quantity_price);

        }
    }
}
