package com.hashbash.sangarodhak.Adapters;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hashbash.sangarodhak.R;
import com.skydoves.expandablelayout.ExpandableLayout;

import java.util.ArrayList;


public class StateAndDistrictDataRecyclerAdapter extends RecyclerView.Adapter<StateAndDistrictDataRecyclerAdapter.AnnouncementViewHolder> {
    private Context context;
    private ArrayList<String> localeNames, totalCases, totalActive, totalRecovered, totalDeath;

    public StateAndDistrictDataRecyclerAdapter(Context context, ArrayList<String> countryCodes, ArrayList<String> countryNames, ArrayList<String> totalCases, ArrayList<String> totalActive, ArrayList<String> totalRecovered, ArrayList<String> totalDeath) {
        this.context = context;
        this.localeNames = countryNames;
        this.totalCases = totalCases;
        this.totalActive = totalActive;
        this.totalRecovered = totalRecovered;
        this.totalDeath = totalDeath;
    }

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ExpandableLayout expandableLayout = new ExpandableLayout(context);
        expandableLayout.setParentLayoutResource(R.layout.item_stats_expandable_state_n_district_parent);
        expandableLayout.setSecondLayoutResource(R.layout.item_stats_expandable_child);
        expandableLayout.setShowSpinner(true);
        expandableLayout.setSpinnerAnimate(true);
        expandableLayout.setSpinnerMargin(12f);
        expandableLayout.setSpinnerRotation(90);
        expandableLayout.setDuration(200);

        return new AnnouncementViewHolder(expandableLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementViewHolder holder, int position) {
        holder.localeName.setText(localeNames.get(position));

        holder.totalCasesText.setText(totalCases.get(position));
        holder.totalActiveText.setText(totalRecovered.get(position));
        holder.totalRecoveredText.setText(totalActive.get(position));
        holder.totalDeathText.setText(totalDeath.get(position));
    }

    @Override
    public int getItemCount() {
        return localeNames.size();
    }

    static class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        TextView localeName, totalCasesText, totalActiveText, totalRecoveredText, totalDeathText;

        AnnouncementViewHolder(@NonNull ExpandableLayout itemView) {
            super(itemView);
            localeName = itemView.parentLayout.findViewById(R.id.country_name);

            totalCasesText = itemView.secondLayout.findViewById(R.id.country_total_cases);
            totalActiveText = itemView.secondLayout.findViewById(R.id.country_active);
            totalRecoveredText = itemView.secondLayout.findViewById(R.id.country_recovered);
            totalDeathText = itemView.secondLayout.findViewById(R.id.country_death);

        }
    }
}
