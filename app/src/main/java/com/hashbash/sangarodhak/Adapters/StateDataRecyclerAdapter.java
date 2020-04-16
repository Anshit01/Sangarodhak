package com.hashbash.sangarodhak.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hashbash.sangarodhak.Modals.StateCaseDataModal;
import com.hashbash.sangarodhak.R;

import java.util.ArrayList;


public class StateDataRecyclerAdapter extends RecyclerView.Adapter<StateDataRecyclerAdapter.CaseDataViewHolder> {
    private Context context;
    private ArrayList<StateCaseDataModal> allDistricts;

    public StateDataRecyclerAdapter(Context context, ArrayList<StateCaseDataModal> allDistricts) {
        this.context = context;
        this.allDistricts = allDistricts;
    }

    @NonNull
    @Override
    public CaseDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_stats_districts_view, parent, false);

        return new CaseDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CaseDataViewHolder holder, int position) {
        StateCaseDataModal thisDistrict = allDistricts.get(position);

        holder.localeName.setText(thisDistrict.getDistrictName());
        holder.totalCasesText.setText(thisDistrict.getTotalCases());
    }

    @Override
    public int getItemCount() {
        return allDistricts.size();
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
