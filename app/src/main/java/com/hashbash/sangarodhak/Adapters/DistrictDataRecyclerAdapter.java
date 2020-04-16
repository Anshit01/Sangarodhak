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


public class DistrictDataRecyclerAdapter extends RecyclerView.Adapter<DistrictDataRecyclerAdapter.CaseDataViewHolder> {
    private Context context;
    private ArrayList<String> localeNames, totalCases;

    public DistrictDataRecyclerAdapter(Context context, ArrayList<String> countryNames, ArrayList<String> totalCases) {
        this.context = context;
        this.localeNames = countryNames;
        this.totalCases = totalCases;
    }

    @NonNull
    @Override
    public CaseDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_stats_districts_view, parent, false);

        return new CaseDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CaseDataViewHolder holder, int position) {
        holder.localeName.setText(localeNames.get(position));

        holder.totalCasesText.setText(totalCases.get(position));
    }

    @Override
    public int getItemCount() {
        return localeNames.size();
    }

    static class CaseDataViewHolder extends RecyclerView.ViewHolder {
        TextView localeName, totalCasesText;

        CaseDataViewHolder(@NonNull View itemView) {
            super(itemView);
            localeName = itemView.findViewById(R.id.locale_name);
            totalCasesText = itemView.findViewById(R.id.locale_total_cases);

        }
    }
}
