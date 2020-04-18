package com.hashbash.sangarodhak.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hashbash.sangarodhak.Modals.FunZoneModal;
import com.hashbash.sangarodhak.R;

import java.util.ArrayList;


public class FunZoneRecyclerAdapter extends RecyclerView.Adapter<FunZoneRecyclerAdapter.AnnouncementViewHolder> {
    int i;
    private Context context;
    private ArrayList<FunZoneModal> allFunZoneItems;

    public FunZoneRecyclerAdapter(Context context, ArrayList<FunZoneModal> allFunZoneItems) {
        this.context = context;
        this.allFunZoneItems = allFunZoneItems;
    }

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_fun_zone_expandable_view, parent, false);
        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AnnouncementViewHolder holder, final int position) {

        FunZoneModal thisItem = allFunZoneItems.get(position);

        holder.itemTitle.setText(thisItem.getTitle());

        holder.arrowIcon.setRotation(thisItem.isExpanded() ? 180 : 0);
        holder.expandableLayout.setVisibility(thisItem.isExpanded() ? View.VISIBLE : View.GONE);

        holder.expandableLayout.removeAllViews();

        for (i = 0; i < thisItem.getSubItems().length; i++) {
            final int temp = i;
            View view = LayoutInflater.from(context).inflate(R.layout.item_fun_zone_expanded_item, null);
            ((TextView) view.findViewById(R.id.object_name)).setText(thisItem.getSubItems()[temp]);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FunZoneModal thisItem = allFunZoneItems.get(holder.getAdapterPosition());
                    try {
                        Class<?> clazz = Class.forName( "com.hashbash.sangarodhak." + thisItem.getActivityToOpen());
                        Intent intent = new Intent(context, clazz);
                        intent.putExtra("extra", thisItem.getActivityExtra()[temp]);
                        context.startActivity(intent);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            holder.expandableLayout.addView(view);
        }
    }

    @Override
    public int getItemCount() {
        return allFunZoneItems.size();
    }

    class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;
        ImageView arrowIcon;

        LinearLayout expandableLayout, titleContainer;

        AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);

            itemTitle = itemView.findViewById(R.id.locale_name);

            titleContainer = itemView.findViewById(R.id.title_container);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            arrowIcon = itemView.findViewById(R.id.arrow_icon);

            titleContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    FunZoneModal thisCountry = allFunZoneItems.get(position);

                    thisCountry.setExpanded(!thisCountry.isExpanded());

                    notifyItemChanged(position);

                }
            });

        }
    }
}
