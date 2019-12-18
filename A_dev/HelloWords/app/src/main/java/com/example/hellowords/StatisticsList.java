package com.example.hellowords;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StatisticsList {

    private StatisticsAdapter statisticsAdapter = null;
    public StatisticsList(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        statisticsAdapter = new StatisticsAdapter();
        rv.setAdapter(statisticsAdapter);
    }

    public void setStatistics(List<StatisticsElement> list) {
        statisticsAdapter.setData(list);
    }

    static class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.Vh> {
        private List<StatisticsElement> data;
        void setData(List<StatisticsElement> data) {
            this.data = data;
            notifyDataSetChanged();
        }
        @NonNull
        @Override
        public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Vh(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull Vh holder, int position) {
            holder.bind(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class Vh extends RecyclerView.ViewHolder {

            private final TextView name;
            private final TextView value;
            public Vh(ViewGroup parent) {
                super(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.statistics_item, parent, false));
                name = itemView.findViewById(R.id.counter_name);
                value = itemView.findViewById(R.id.counter_value);
            }

            void bind(StatisticsElement statisticsElement) {
                name.setText(statisticsElement.getName());
                value.setText(String.valueOf(statisticsElement.getNumber()));
            }
        }
    }
}
