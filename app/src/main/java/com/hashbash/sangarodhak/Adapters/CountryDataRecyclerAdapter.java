package com.hashbash.sangarodhak.Adapters;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hashbash.sangarodhak.R;
import com.skydoves.expandablelayout.ExpandableLayout;

import java.util.ArrayList;


public class CountryDataRecyclerAdapter extends RecyclerView.Adapter<CountryDataRecyclerAdapter.AnnouncementViewHolder> {
    private Context context;
    private ArrayList<String> countryCodes, countryNames, totalCases, totalActive, totalRecovered, totalDeath;

    public CountryDataRecyclerAdapter(Context context, ArrayList<String> countryCodes, ArrayList<String> countryNames, ArrayList<String> totalCases, ArrayList<String> totalActive, ArrayList<String> totalRecovered, ArrayList<String> totalDeath) {
        this.context = context;
        this.countryCodes = countryCodes;
        this.countryNames = countryNames;
        this.totalCases = totalCases;
        this.totalActive = totalActive;
        this.totalRecovered = totalRecovered;
        this.totalDeath = totalDeath;
    }

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ExpandableLayout expandableLayout = new ExpandableLayout.Builder(context)
                .setParentLayoutResource(R.layout.item_stats_expandable_country_parent)
                .setSecondLayoutResource(R.layout.item_stats_expandable_child)
                .setShowSpinner(true)
                .setSpinnerAnimate(true)
                .setSpinnerMargin(12f)
                .setSpinnerRotation(90)
                .setDuration(200)
                .build();


        return new AnnouncementViewHolder(expandableLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementViewHolder holder, int position) {
        Glide.with(context).load("https://www.countryflags.io/" + countryCodes.get(position) + "/shiny/64.png").into(holder.countryFlag);
        holder.countryNameText.setText(countryNames.get(position));

        holder.totalCasesText.setText(totalCases.get(position));
        holder.totalActiveText.setText(totalActive.get(position));
        holder.totalRecoveredText.setText(totalRecovered.get(position));
        holder.totalDeathText.setText(totalDeath.get(position));
    }

    @Override
    public int getItemCount() {
        return countryCodes.size();
    }

    static class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        TextView countryNameText, totalCasesText, totalActiveText, totalRecoveredText, totalDeathText;
        ImageView countryFlag;

        AnnouncementViewHolder(@NonNull ExpandableLayout itemView) {
            super(itemView);
            countryFlag = itemView.parentLayout.findViewById(R.id.country_flag);
            countryNameText = itemView.parentLayout.findViewById(R.id.country_name);

            totalCasesText = itemView.secondLayout.findViewById(R.id.country_total_cases);
            totalActiveText = itemView.secondLayout.findViewById(R.id.country_active);
            totalRecoveredText = itemView.secondLayout.findViewById(R.id.country_recovered);
            totalDeathText = itemView.secondLayout.findViewById(R.id.country_death);

        }
    }
}
