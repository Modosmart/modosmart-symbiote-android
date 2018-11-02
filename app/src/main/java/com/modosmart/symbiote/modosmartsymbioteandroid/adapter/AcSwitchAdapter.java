package com.modosmart.symbiote.modosmartsymbioteandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.modosmart.symbiote.modosmartsymbioteandroid.R;
import com.modosmart.symbiote.modosmartsymbioteandroid.model.AcSwitchResourceModel;

import java.util.ArrayList;
import java.util.Collection;

public class AcSwitchAdapter extends RecyclerView.Adapter {
    private ArrayList<AcSwitchResourceModel> resources = new ArrayList<>();
    private OnSwitchStatusChange onSwitchStatusChangeListener;

    public AcSwitchAdapter(ArrayList<AcSwitchResourceModel> resources) {
        this.resources = resources;
        notifyDataSetChanged();
    }

    public boolean add(AcSwitchResourceModel item) {
        boolean isAdded = resources.add(item);
        if (isAdded) {
            notifyItemInserted(resources.size());
        }
        return isAdded;
    }

    public void append(AcSwitchResourceModel item) {
        resources.add(0, item);
        notifyItemInserted(resources.size());
    }

    public boolean addAll(Collection<AcSwitchResourceModel> items) {
        boolean isAdded = resources.addAll(items);
        if (isAdded) {
            notifyDataSetChanged();
        }
        return isAdded;
    }

    public void appendAll(Collection<AcSwitchResourceModel> items) {
        resources.addAll(0, items);
        notifyDataSetChanged();
    }

    public void clear() {
        resources.clear();
        notifyDataSetChanged();
    }

    public ArrayList<AcSwitchResourceModel> getResources() {
        return resources;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.ac_switch_card, parent, false);

        return new ResourceViewHolder(v);
    }

    public void remove(int index) {
        resources.remove(index);
    }

    public void setOnSwitchStatusChange(OnSwitchStatusChange onSwitchStatusChangeListener) {
        this.onSwitchStatusChangeListener = onSwitchStatusChangeListener;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final AcSwitchResourceModel resourceModel = resources.get(position);
        ((ResourceViewHolder) holder).mac_address.setText(resourceModel.getMacAddress());
        ((ResourceViewHolder) holder).symbiote_id.setText(resourceModel.getSymbioteId());

        ((ResourceViewHolder) holder).ac_status.setChecked(resourceModel.getStatus());
        ((ResourceViewHolder) holder).ac_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                onSwitchStatusChangeListener.OnSwitchChange(b, resourceModel.getSymbioteId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return resources.size();
    }

    private class ResourceViewHolder extends RecyclerView.ViewHolder {
        private TextView mac_address;
        private TextView symbiote_id;
        private Switch ac_status;

        ResourceViewHolder(View itemView) {
            super(itemView);
            mac_address = itemView.findViewById(R.id.ac_mac_address_value);
            symbiote_id = itemView.findViewById(R.id.ac_symbiote_id_value);
            ac_status = itemView.findViewById(R.id.ac_switch);
        }
    }
}
