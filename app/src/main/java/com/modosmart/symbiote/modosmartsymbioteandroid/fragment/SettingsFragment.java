package com.modosmart.symbiote.modosmartsymbioteandroid.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.modosmart.symbiote.modosmartsymbioteandroid.R;

public class SettingsFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
    }
}
