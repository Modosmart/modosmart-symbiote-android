package com.modosmart.symbiote.modosmartsymbioteandroid.fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.modosmart.symbiote.modosmartsymbioteandroid.R;
import com.modosmart.symbiote.modosmartsymbioteandroid.adapter.AcSwitchAdapter;
import com.modosmart.symbiote.modosmartsymbioteandroid.adapter.OnSwitchStatusChange;
import com.modosmart.symbiote.modosmartsymbioteandroid.model.AcSwitchResourceModel;
import com.modosmart.symbiote.modosmartsymbioteandroid.service.INetworkService;
import com.modosmart.symbiote.modosmartsymbioteandroid.service.SymbioteService;
import com.modosmart.symbiote.modosmartsymbioteandroid.utils.ConstantsUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AcSwitchFragment extends Fragment implements INetworkService {
    private SymbioteService mSymbioteService;
    private RecyclerView mRecyclerView;
    private TextView tvEmptyView;
    private AcSwitchAdapter mAcSwitchAdapter;
    private final int REQUEST_ALL_RESOURCES = 0;
    private final int REQUEST_READ_AC_SWITCH = 1;
    private final int REQUEST_WRITE_AC_SWITCH = 2;

    private ArrayList<AcSwitchResourceModel> allResources = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ac_switch, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);
        tvEmptyView = view.findViewById(R.id.empty_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAcSwitchAdapter = new AcSwitchAdapter(new ArrayList<AcSwitchResourceModel>());
        mRecyclerView.setAdapter(mAcSwitchAdapter);

        mAcSwitchAdapter.setOnSwitchStatusChange(new OnSwitchStatusChange() {
            @Override
            public void OnSwitchChange(boolean status, String symbiote_id) {
                Log.d("DEBUG", "Status = " + status);
                Log.d("DEBUG", "Symbiote ID = " + symbiote_id);

                mSymbioteService.controlSwitchStatus(REQUEST_WRITE_AC_SWITCH, true,status, symbiote_id,
                        ConstantsUtil.SYMBIOTE_TOKEN, 0);
            }
        });

        mSymbioteService = new SymbioteService();
        mSymbioteService.addListener(this);

        mSymbioteService.getAllResources(REQUEST_ALL_RESOURCES);

        return view;
    }

    private void getAllReadings() {
        for (int i = 0; i < allResources.size(); i++) {
            mSymbioteService.controlSwitchStatus(REQUEST_READ_AC_SWITCH, false,false, allResources.get(i).getSymbioteId(),
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
        mAcSwitchAdapter.clear();
    }

    @Override
    public void responseSuccess(JSONObject response, int requestNumber) {
        if (requestNumber == REQUEST_READ_AC_SWITCH) {
            try {
                int position = response.getInt("position");
                String status = response.getString("status");
                AcSwitchResourceModel resource = allResources.get(position);
                if (status.equals("OFF")) {
                    resource.setStatus(false);
                } else {
                    resource.setStatus(true);
                }
                mAcSwitchAdapter.add(resource);
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
                    if (type.equals(".Actuator")) {
                        String symbiote_id = resource.getString("id");
                        String mac_address = resource.getString("name");
                        mac_address = mac_address.replace("AC_SWITCH_","");

                        AcSwitchResourceModel mResourceModel = new AcSwitchResourceModel(type, symbiote_id, mac_address, false);
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
        mAcSwitchAdapter.clear();
        mRecyclerView.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void responseFailure(int requestNumber) {
        mAcSwitchAdapter.clear();
        mRecyclerView.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void responseTimeoutError(int requestNumber) {
        mAcSwitchAdapter.clear();
        mRecyclerView.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void responseNetworkError(int requestNumber) {
        mAcSwitchAdapter.clear();
        mRecyclerView.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void responseServerError(int requestNumber) {
        mAcSwitchAdapter.clear();
        mRecyclerView.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void responseParseError(int requestNumber) {
        mAcSwitchAdapter.clear();
        mRecyclerView.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void AuthFailureError(int requestNumber) {
        mAcSwitchAdapter.clear();
        mRecyclerView.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);
    }
}
