package com.modosmart.symbiote.modosmartsymbioteandroid.fragment;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.modosmart.symbiote.modosmartsymbioteandroid.R;
import com.modosmart.symbiote.modosmartsymbioteandroid.adapter.RoomSensorAdapter;
import com.modosmart.symbiote.modosmartsymbioteandroid.model.RoomSensorResourceModel;
import com.modosmart.symbiote.modosmartsymbioteandroid.service.INetworkService;
import com.modosmart.symbiote.modosmartsymbioteandroid.service.SymbioteService;
import com.modosmart.symbiote.modosmartsymbioteandroid.utils.ConstantsUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RoomSensorFragment extends Fragment implements INetworkService {
    private SymbioteService mSymbioteService;
    private RecyclerView mRecyclerView;
    private TextView tvEmptyView;
    private RoomSensorAdapter mRoomSensorAdapter;
    private final int REQUEST_ALL_RESOURCES = 0;
    private final int REQUEST_READ_ROOM_SENSOR = 1;

    private ArrayList<RoomSensorResourceModel> allResources = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_room_sensor, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);
        tvEmptyView = view.findViewById(R.id.empty_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRoomSensorAdapter = new RoomSensorAdapter(new ArrayList<RoomSensorResourceModel>());
        mRecyclerView.setAdapter(mRoomSensorAdapter);

        mSymbioteService = new SymbioteService();
        mSymbioteService.addListener(this);

        mSymbioteService.getAllResources(REQUEST_ALL_RESOURCES);
        return view;
    }

    private void getAllReadings() {
        for (int i = 0; i < allResources.size(); i++) {
            mSymbioteService.getRoomSensorReadings(REQUEST_READ_ROOM_SENSOR, allResources.get(i).getSymbioteId(),
                    ConstantsUtil.SYMBIOTE_TOKEN, i);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mSymbioteService != null ) {
            mSymbioteService.removeListener(this);
            mSymbioteService.cancelAllRequests();
        }

        allResources.clear();
        mRoomSensorAdapter.clear();
    }

    @Override
    public void responseSuccess(JSONObject response, int requestNumber) {
        if (requestNumber == REQUEST_READ_ROOM_SENSOR) {
            try {
                int position = response.getInt("position");
                String temperature = response.getString("temperature");
                String humidity = response.getString("humidity");
                String presence = response.getString("presence");
                String battery = response.getString("battery");
                String firmware = response.getString("firmware");

                RoomSensorResourceModel resource = allResources.get(position);
                resource.setTemperature(temperature);
                resource.setHumidity(humidity);
                resource.setBattery(battery);
                resource.setPresence(presence);
                resource.setFirmware(firmware);

                mRoomSensorAdapter.add(resource);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void responseSuccess(JSONArray response, int requestNumber) {
        if (requestNumber == REQUEST_ALL_RESOURCES) {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    JSONObject resource = jsonObject.getJSONObject("resource");
                    String type = resource.getString("@c");
                    if (type.equals(".StationarySensor")) {
                        String symbiote_id = resource.getString("id");
                        String mac_address = resource.getString("name");
                        mac_address = mac_address.replace("SM006_","");

                        RoomSensorResourceModel mResourceModel = new RoomSensorResourceModel(type, symbiote_id,
                                mac_address, "", "", "", "", "");
                        allResources.add(mResourceModel);
                    }
                } catch (JSONException e) {
                }
            }

            getAllReadings();

            if (response.length() == 0) {
                mRecyclerView.setVisibility(View.GONE);
                tvEmptyView.setVisibility(View.VISIBLE);
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                tvEmptyView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void responseFailure(JSONObject failure, int requestNumber) {
        mRoomSensorAdapter.clear();
        mRecyclerView.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void responseFailure(int requestNumber) {
        mRoomSensorAdapter.clear();
        mRecyclerView.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void responseTimeoutError(int requestNumber) {
        mRoomSensorAdapter.clear();
        mRecyclerView.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void responseNetworkError(int requestNumber) {
        mRoomSensorAdapter.clear();
        mRecyclerView.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void responseServerError(int requestNumber) {
        mRoomSensorAdapter.clear();
        mRecyclerView.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void responseParseError(int requestNumber) {
        mRoomSensorAdapter.clear();
        mRecyclerView.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void AuthFailureError(int requestNumber) {
        mRoomSensorAdapter.clear();
        mRecyclerView.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);
    }
}
