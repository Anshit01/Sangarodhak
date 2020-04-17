package com.hashbash.sangarodhak.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.hashbash.sangarodhak.Adapters.CartRecyclerAdapter;
import com.hashbash.sangarodhak.Adapters.SuppliesRecyclerAdapter;
import com.hashbash.sangarodhak.Modals.SupplyItemsDataModal;
import com.hashbash.sangarodhak.R;

import java.text.MessageFormat;
import java.util.ArrayList;

public class FragmentSupplies extends Fragment {

    public static RecyclerView recyclerView, cartRecyclerView;
    private LinearLayout cartLayout, viewCartView;
    private BottomSheetBehavior bottomSheetBehavior;
    public static ArrayList<SupplyItemsDataModal> allItemsAvailable = new ArrayList<>(), allItemsAddedInCart = new ArrayList<>();

    private static TextView checkoutAmount, noOfItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_supplies, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        cartRecyclerView = view.findViewById(R.id.cart_recycler_view);
        viewCartView = view.findViewById(R.id.view_cart_view);
        cartLayout = view.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(cartLayout);

        checkoutAmount = view.findViewById(R.id.total_amount_to_pay);
        noOfItems = view.findViewById(R.id.total_items_in_cart);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewCartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                else
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        allItemsAvailable.add(new SupplyItemsDataModal("Potato", "Kg", 50, 30));
        allItemsAvailable.add(new SupplyItemsDataModal("Onion", "Kg", 30, 50));
        allItemsAvailable.add(new SupplyItemsDataModal("Sugar", "Kg", 50, 35));
        allItemsAvailable.add(new SupplyItemsDataModal("Dhaniya", "g", 3000, 10));

        recyclerView.setAdapter(new SuppliesRecyclerAdapter(getContext(), allItemsAvailable));

        allItemsAddedInCart.add(new SupplyItemsDataModal("Potato", "Kg", 50, 30, 1));

        cartRecyclerView.setAdapter(new CartRecyclerAdapter(getContext(), allItemsAddedInCart));

        updateCartDetails();

        return view;
    }

    public static void updateCartDetails() {

        SupplyItemsDataModal lastItem = allItemsAddedInCart.get(allItemsAddedInCart.size()-1);
        for(int i = 0; i < allItemsAddedInCart.size()-1; i++){
            if(allItemsAddedInCart.get(i).getItemName().equals(lastItem.getItemName())) {
                allItemsAddedInCart.get(i).setQuantityBought(lastItem.getQuantityBought());
                allItemsAddedInCart.remove(lastItem);
                break;
            }
        }

        noOfItems.setText(MessageFormat.format("{0}", allItemsAddedInCart.size()));

        int priceSum = 0;

        for (int i = 0; i < allItemsAddedInCart.size(); i++)
            priceSum += allItemsAddedInCart.get(i).getItemRate() * allItemsAddedInCart.get(i).getQuantityBought();

        checkoutAmount.setText(MessageFormat.format("₹ {0}", priceSum));

    }
}
