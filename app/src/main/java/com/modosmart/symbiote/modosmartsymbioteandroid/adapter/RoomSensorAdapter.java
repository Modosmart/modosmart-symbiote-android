package com.modosmart.symbiote.modosmartsymbioteandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.modosmart.symbiote.modosmartsymbioteandroid.R;
import com.modosmart.symbiote.modosmartsymbioteandroid.model.RoomSensorResourceModel;

import java.util.ArrayList;
import java.util.Collection;

public class RoomSensorAdapter extends RecyclerView.Adapter {
    private ArrayList<RoomSensorResourceModel> resources = new ArrayList<>();

    public RoomSensorAdapter(ArrayList<RoomSensorResourceModel> resources) {
        this.resources = resources;
        notifyDataSetChanged();
    }

    public boolean add(RoomSensorResourceModel item) {
        boolean isAdded = resources.add(item);
        if (isAdded) {
            notifyItemInserted(resources.size());
        }
        return isAdded;
    }

    public void append(RoomSensorResourceModel item) {
        resources.add(0, item);
        notifyItemInserted(resources.size());
    }

    public boolean addAll(Collection<RoomSensorResourceModel> items) {
        boolean isAdded = resources.addAll(items);
        if (isAdded) {
            notifyDataSetChanged();
        }
        return isAdded;
    }

    public void appendAll(Collection<RoomSensorResourceModel> items) {
        resources.addAll(0, items);
        notifyDataSetChanged();
    }

    public void clear() {
        resources.clear();
        notifyDataSetChanged();
    }

    public ArrayList<RoomSensorResourceModel> getResources() {
        return resources;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.room_sensor_card, parent, false);

        return new ResourceViewHolder(v);
    }

    public void remove(int index) {
        resources.remove(index);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final RoomSensorResourceModel resourceModel = resources.get(position);
        ((ResourceViewHolder) holder).mac_address.setText(resourceModel.getMacAddress());
        ((ResourceViewHolder) holder).symbiote_id.setText(resourceModel.getSymbioteId());
        ((ResourceViewHolder) holder).temperature.setText(resourceModel.getTemperature() + (char) 0x00B0);
        ((ResourceViewHolder) holder).humidity.setText(resourceModel.getHumidity() + "%");
        ((ResourceViewHolder) holder).presence.setText(resourceModel.getPresence() + "%");
        ((ResourceViewHolder) holder).battery.setText(resourceModel.getBattery() + "%");
        ((ResourceViewHolder) holder).firmware.setText(resourceModel.getFirmware());
    }

    @Override
    public int getItemCount() {
        return resources.size();
    }

    private class ResourceViewHolder extends RecyclerView.ViewHolder {
        private TextView mac_address;
        private TextView symbiote_id;
        private TextView temperature;
        private TextView humidity;
        private TextView presence;
        private TextView battery;
        private TextView firmware;

        ResourceViewHolder(View itemView) {
            super(itemView);
            mac_address = itemView.findViewById(R.id.room_mac_address_value);
            symbiote_id = itemView.findViewById(R.id.room_symbiote_id_value);
            temperature = itemView.findViewById(R.id.room_temperature_value);
            humidity = itemView.findViewById(R.id.room_humidity_value);
            presence = itemView.findViewById(R.id.room_presence_value);
            battery = itemView.findViewById(R.id.room_battery_value);
            firmware = itemView.findViewById(R.id.room_firmware_value);
        }
    }
}
