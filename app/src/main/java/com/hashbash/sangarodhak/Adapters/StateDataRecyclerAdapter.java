package com.hashbash.sangarodhak.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hashbash.sangarodhak.R;

import java.util.ArrayList;


public class StateDataRecyclerAdapter extends RecyclerView.Adapter<StateDataRecyclerAdapter.CaseDataViewHolder> {
    private Context context;
    private ArrayList<String> localeNames, totalCases, totalActive, totalRecovered, totalDeath;

    public StateDataRecyclerAdapter(Context context, ArrayList<String> countryNames, ArrayList<String> totalCases, ArrayList<String> totalActive, ArrayList<String> totalRecovered, ArrayList<String> totalDeath) {
        this.context = context;
        this.localeNames = countryNames;
        this.totalCases = totalCases;
        this.totalActive = totalActive;
        this.totalRecovered = totalRecovered;
        this.totalDeath = totalDeath;
    }

    @NonNull
    @Override
    public CaseDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_stats_country_expandable_view, parent, false);

        return new CaseDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CaseDataViewHolder holder, int position) {
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

    static class CaseDataViewHolder extends RecyclerView.ViewHolder {
        TextView localeName, totalCasesText, totalActiveText, totalRecoveredText, totalDeathText;
        LinearLayout expandableLayout;
        ImageView arrowIcon;
        boolean isExpanded = false;

        CaseDataViewHolder(@NonNull View itemView) {
            super(itemView);

            localeName = itemView.findViewById(R.id.locale_name);

            totalCasesText = itemView.findViewById(R.id.locale_total_cases);
            totalActiveText = itemView.findViewById(R.id.locale_active);
            totalRecoveredText = itemView.findViewById(R.id.locale_recovered);
            totalDeathText = itemView.findViewById(R.id.locale_death);

            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            arrowIcon = itemView.findViewById(R.id.arrow_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isExpanded = !isExpanded;

                    arrowIcon.setRotation(isExpanded ? 180 : 0);

                    expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });

        }
    }
}
