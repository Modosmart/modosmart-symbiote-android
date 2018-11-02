package com.modosmart.symbiote.modosmartsymbioteandroid.network;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.modosmart.symbiote.modosmartsymbioteandroid.activity.MyApplication;

public class VolleySingleton {
    private static  VolleySingleton sInstance = null;
    private RequestQueue mRequestQueue;

    private VolleySingleton() {
        mRequestQueue = Volley.newRequestQueue(MyApplication.getsAppContext());
    }

    public static VolleySingleton getInstance() {
        if (sInstance == null){
            sInstance = new VolleySingleton();
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
