package team.hashbash.sangarodhak.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import team.hashbash.sangarodhak.Modals.CountryCaseDataModal;
import team.hashbash.sangarodhak.R;

import java.util.ArrayList;


public class CountryDataRecyclerAdapter extends RecyclerView.Adapter<CountryDataRecyclerAdapter.CaseDataViewHolder> {

    private Context context;
    private ArrayList<CountryCaseDataModal> allstates;

    public CountryDataRecyclerAdapter(Context context, ArrayList<CountryCaseDataModal> allstates) {
        this.context = context;
        this.allstates = allstates;
    }

    @NonNull
    @Override
    public CaseDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_stats_country_expandable_view, parent, false);

        return new CaseDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CaseDataViewHolder holder, final int position) {

        CountryCaseDataModal thisState = allstates.get(position);

        holder.localeName.setText(thisState.getStateName());

        holder.totalCasesText.setText(thisState.getTotalCases());
        holder.totalActiveText.setText(thisState.getTotalActiveCases());
        holder.totalRecoveredText.setText(thisState.getTotalRecovered());
        holder.totalDeathText.setText(thisState.getTotalDeaths());
        holder.titleTotalCases.setText(thisState.getTotalCases());

        holder.arrowIcon.setRotation(thisState.isExpanded() ? 180 : 0);
        holder.expandableLayout.setVisibility(thisState.isExpanded() ? View.VISIBLE : View.GONE);
        holder.titleTotalCases.setVisibility(thisState.isExpanded() ? View.GONE : View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return allstates.size();
    }

    class CaseDataViewHolder extends RecyclerView.ViewHolder {
        TextView localeName, totalCasesText, totalActiveText, totalRecoveredText, totalDeathText, titleTotalCases;
        LinearLayout expandableLayout, titleContainer;
        ImageView arrowIcon;

        CaseDataViewHolder(@NonNull View itemView) {
            super(itemView);

            localeName = itemView.findViewById(R.id.locale_name);

            totalCasesText = itemView.findViewById(R.id.locale_total_cases);
            totalActiveText = itemView.findViewById(R.id.locale_active);
            totalRecoveredText = itemView.findViewById(R.id.locale_recovered);
            totalDeathText = itemView.findViewById(R.id.locale_death);
            titleTotalCases = itemView.findViewById(R.id.title_total_cases);

            titleContainer = itemView.findViewById(R.id.title_container);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            arrowIcon = itemView.findViewById(R.id.arrow_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    CountryCaseDataModal thisState = allstates.get(position);

                    thisState.setExpanded(!thisState.isExpanded());

                    notifyItemChanged(position);
//
//                    arrowIcon.setRotation(isExpanded.get(getAdapterPosition()) ? 180 : 0);
//
//                    expandableLayout.setVisibility(isExpanded.get(getAdapterPosition()) ? View.VISIBLE : View.GONE);
//                    titleTotalCases.setVisibility(isExpanded.get(getAdapterPosition()) ? View.GONE : View.VISIBLE);
                }
            });

        }
    }
}
