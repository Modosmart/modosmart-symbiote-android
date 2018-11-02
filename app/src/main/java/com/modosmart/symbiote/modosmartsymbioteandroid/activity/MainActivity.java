package com.modosmart.symbiote.modosmartsymbioteandroid.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import com.modosmart.symbiote.modosmartsymbioteandroid.R;
import com.modosmart.symbiote.modosmartsymbioteandroid.fragment.AcSwitchFragment;
import com.modosmart.symbiote.modosmartsymbioteandroid.fragment.RoomSensorFragment;
import com.modosmart.symbiote.modosmartsymbioteandroid.fragment.SettingsFragment;
import com.modosmart.symbiote.modosmartsymbioteandroid.utils.ConstantsUtil;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        BottomNavigationView bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menu_room_sensor) {
                    RoomSensorFragment roomSensorFragment =
                            (RoomSensorFragment) fragmentManager.findFragmentByTag(ConstantsUtil.FRAGMENT_ROOM_SENSOR_TAG);
                    if (roomSensorFragment == null) {
                        // Never clicked before
                        roomSensorFragment = new RoomSensorFragment();
                    }
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.home_page_fragment_container, roomSensorFragment,
                            ConstantsUtil.FRAGMENT_ROOM_SENSOR_TAG);
                    fragmentTransaction.addToBackStack(ConstantsUtil.FRAGMENT_ROOM_SENSOR_TAG);
                    fragmentTransaction.commit();
                    fragmentManager.executePendingTransactions();
                } else if (menuItem.getItemId() == R.id.menu_ac_switch) {
                    AcSwitchFragment acSwitchFragment =
                            (AcSwitchFragment) fragmentManager.findFragmentByTag(ConstantsUtil.FRAGMENT_AC_SWITCH_TAG);
                    if (acSwitchFragment == null) {
                        // Never clicked before
                        acSwitchFragment = new AcSwitchFragment();
                    }
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.home_page_fragment_container, acSwitchFragment,
                            ConstantsUtil.FRAGMENT_AC_SWITCH_TAG);
                    fragmentTransaction.addToBackStack(ConstantsUtil.FRAGMENT_AC_SWITCH_TAG);
                    fragmentTransaction.commit();
                    fragmentManager.executePendingTransactions();
                } else if (menuItem.getItemId() == R.id.menu_settings) {
                    SettingsFragment settingsFragment =
                            (SettingsFragment) fragmentManager.findFragmentByTag(ConstantsUtil.FRAGMENT_SETTINGS_TAG);
                    if (settingsFragment == null) {
                        // Never clicked before
                        settingsFragment = new SettingsFragment();
                    }
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.home_page_fragment_container, settingsFragment,
                            ConstantsUtil.FRAGMENT_SETTINGS_TAG);
                    fragmentTransaction.addToBackStack(ConstantsUtil.FRAGMENT_SETTINGS_TAG);
                    fragmentTransaction.commit();
                    fragmentManager.executePendingTransactions();
                }
                return true;
            }
        });

        RoomSensorFragment roomSensorFragment =
                (RoomSensorFragment) fragmentManager.findFragmentByTag(ConstantsUtil.FRAGMENT_ROOM_SENSOR_TAG);
        if (roomSensorFragment == null) {
            // Never clicked before
            roomSensorFragment = new RoomSensorFragment();
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_page_fragment_container, roomSensorFragment,
                ConstantsUtil.FRAGMENT_ROOM_SENSOR_TAG);
        fragmentTransaction.addToBackStack(ConstantsUtil.FRAGMENT_ROOM_SENSOR_TAG);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }
}
