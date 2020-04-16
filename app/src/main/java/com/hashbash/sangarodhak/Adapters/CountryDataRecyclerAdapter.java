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

import com.bumptech.glide.Glide;
import com.hashbash.sangarodhak.R;
import com.skydoves.expandablelayout.ExpandableLayout;

import java.util.ArrayList;


public class CountryDataRecyclerAdapter extends RecyclerView.Adapter<CountryDataRecyclerAdapter.AnnouncementViewHolder> {
    private Context context;
    private ArrayList<String> countryCodes, countryNames, totalCases, totalActive, totalRecovered, totalDeath;
    private ArrayList<Boolean> isExpanded;

    public CountryDataRecyclerAdapter(Context context, ArrayList<String> countryCodes, ArrayList<String> countryNames, ArrayList<String> totalCases, ArrayList<String> totalActive, ArrayList<String> totalRecovered, ArrayList<String> totalDeath) {
        this.context = context;
        this.countryCodes = countryCodes;
        this.countryNames = countryNames;
        this.totalCases = totalCases;
        this.totalActive = totalActive;
        this.totalRecovered = totalRecovered;
        this.totalDeath = totalDeath;
        isExpanded = new ArrayList<>();
        for (int i = 0; i < countryCodes.size(); i++)
            isExpanded.add(false);
    }

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_stats_country_expandable_view, parent, false);

        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AnnouncementViewHolder holder, final int position) {
        Glide.with(context).load("https://www.countryflags.io/" + countryCodes.get(position) + "/shiny/64.png").into(holder.countryFlag);
        holder.countryNameText.setText(countryNames.get(position));

        holder.totalCasesText.setText(totalCases.get(position));
        holder.totalActiveText.setText(totalActive.get(position));
        holder.totalRecoveredText.setText(totalRecovered.get(position));
        holder.totalDeathText.setText(totalDeath.get(position));
        holder.titleTotalCases.setText(totalCases.get(position));

        holder.titleContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpanded.set(position, !isExpanded.get(position));

                holder.arrowIcon.setRotation(isExpanded.get(position) ? 180 : 0);
                holder.expandableLayout.setVisibility(isExpanded.get(position) ? View.VISIBLE : View.GONE);
                holder.titleTotalCases.setVisibility(isExpanded.get(position) ? View.GONE : View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return countryCodes.size();
    }

    static class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        TextView countryNameText, totalCasesText, totalActiveText, totalRecoveredText, totalDeathText, titleTotalCases;
        ImageView countryFlag, arrowIcon;

        LinearLayout expandableLayout, titleContainer;

        AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);

            countryFlag = itemView.findViewById(R.id.country_flag);
            countryNameText = itemView.findViewById(R.id.locale_name);
            titleTotalCases = itemView.findViewById(R.id.title_total_cases);

            totalCasesText = itemView.findViewById(R.id.locale_total_cases);
            totalActiveText = itemView.findViewById(R.id.locale_active);
            totalRecoveredText = itemView.findViewById(R.id.locale_recovered);
            totalDeathText = itemView.findViewById(R.id.locale_death);

            titleContainer = itemView.findViewById(R.id.title_container);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            arrowIcon = itemView.findViewById(R.id.arrow_icon);

        }
    }
}
