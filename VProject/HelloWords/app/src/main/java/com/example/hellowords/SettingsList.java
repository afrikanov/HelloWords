package com.example.hellowords;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SettingsList {

    private SettingsAdapter statisticsAdapter = null;
    public SettingsList(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        statisticsAdapter = new SettingsAdapter();
        rv.setAdapter(statisticsAdapter);
    }

    public void setStatistics(List<SettingsElement> list) {
        statisticsAdapter.setData(list);
    }

    static class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.Vh> {
        private List<SettingsElement> data;
        void setData(List<SettingsElement> data) {
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
                        .inflate(R.layout.settings_element, parent, false));
                name = itemView.findViewById(R.id.counter_name);
                value = itemView.findViewById(R.id.counter_value);
            }

            void bind(SettingsElement settingsElement) {
                name.setText(settingsElement.getName());
                value.setText(String.valueOf(settingsElement.getNumber()));
            }
        }
    }
}
