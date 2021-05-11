package com.rusinak.carstat.ui.stats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rusinak.carstat.R;

import java.util.List;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> {

    Context context;
    List<StatsModel> listStats;

    public StatsAdapter(Context context, List<StatsModel> listStats) {
        this.context = context;
        this.listStats = listStats;
    }

    @NonNull
    @Override
    public StatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatsAdapter.ViewHolder holder, int position) {
        if (listStats != null && listStats.size() > 0) {
            StatsModel stats = listStats.get(position);
            holder.nameStats.setText(stats.getStatName());
            holder.valueStats.setText(stats.getStatValue());
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return listStats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameStats, valueStats;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameStats = itemView.findViewById(R.id.statName);
            valueStats = itemView.findViewById(R.id.statValue);

        }
    }
}
