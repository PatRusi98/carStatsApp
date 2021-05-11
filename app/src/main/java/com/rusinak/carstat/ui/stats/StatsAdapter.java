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
    List<StatsModel> statistiky;

    public StatsAdapter(Context context, List<StatsModel> statistiky) {
        this.context = context;
        this.statistiky = statistiky;
    }

    @NonNull
    @Override
    public StatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatsAdapter.ViewHolder holder, int position) {
        if (statistiky != null && statistiky.size() > 0) {
            StatsModel stats = statistiky.get(position);
            holder.menoStatistiky.setText(stats.getStatName());
            holder.hodnStatistiky.setText(stats.getStatValue());
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return statistiky.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView menoStatistiky, hodnStatistiky;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            menoStatistiky = itemView.findViewById(R.id.statName);
            hodnStatistiky = itemView.findViewById(R.id.statValue);

        }
    }
}
