package com.rusinak.carstat.ui.statistiky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rusinak.carstat.R;

import java.util.List;

public class StatistikyAdapter extends RecyclerView.Adapter<StatistikyAdapter.ViewHolder> {

    Context context;
    List<StatistikyModel> statistiky;

    public StatistikyAdapter(Context context, List<StatistikyModel> statistiky) {
        this.context = context;
        this.statistiky = statistiky;
    }

    @NonNull
    @Override
    public StatistikyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatistikyAdapter.ViewHolder holder, int position) {
        if (statistiky != null && statistiky.size() > 0) {
            StatistikyModel stats = statistiky.get(position);
            holder.menoStatistiky.setText(stats.getMenoStat());
            holder.hodnStatistiky.setText(stats.getHodnStat());
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

            menoStatistiky = itemView.findViewById(R.id.menoStatistiky);
            hodnStatistiky = itemView.findViewById(R.id.hodnStatistiky);

        }
    }
}
