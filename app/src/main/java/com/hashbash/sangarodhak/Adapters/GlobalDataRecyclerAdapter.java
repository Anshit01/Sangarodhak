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
import com.hashbash.sangarodhak.Modals.GlobalCaseDataModal;
import com.hashbash.sangarodhak.R;

import java.util.ArrayList;


public class GlobalDataRecyclerAdapter extends RecyclerView.Adapter<GlobalDataRecyclerAdapter.AnnouncementViewHolder> {
    private Context context;

    private ArrayList<GlobalCaseDataModal> allCountryData;

    public GlobalDataRecyclerAdapter(Context context, ArrayList<GlobalCaseDataModal> allCountryData) {
        this.context = context;
        this.allCountryData = allCountryData;
    }

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_stats_country_expandable_view, parent, false);

        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AnnouncementViewHolder holder, final int position) {

        final GlobalCaseDataModal thisCountry = allCountryData.get(position);

        Glide.with(context).load("https://www.countryflags.io/" + thisCountry.getCountryCode() + "/shiny/64.png").into(holder.countryFlag);
        holder.countryNameText.setText(thisCountry.getCountryName());

        holder.totalCasesText.setText(thisCountry.getTotalActiveCases());
        holder.totalActiveText.setText(thisCountry.getTotalActiveCases());
        holder.totalRecoveredText.setText(thisCountry.getTotalRecovered());
        holder.totalDeathText.setText(thisCountry.getTotalDeaths());
        holder.titleTotalCases.setText(thisCountry.getTotalCases());

        holder.arrowIcon.setRotation(thisCountry.isExpanded() ? 180 : 0);
        holder.expandableLayout.setVisibility(thisCountry.isExpanded() ? View.VISIBLE : View.GONE);
        holder.titleTotalCases.setVisibility(thisCountry.isExpanded() ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return allCountryData.size();
    }

    class AnnouncementViewHolder extends RecyclerView.ViewHolder {
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    GlobalCaseDataModal thisCountry = allCountryData.get(position);

                    thisCountry.setExpanded(!thisCountry.isExpanded());

                    notifyItemChanged(position);
                }
            });

        }
    }
}
