package com.atlassian.exercise.network;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.atlassian.exercise.AtlassianApplication;

/**
 * Volley HTTP client singleton to have a common Request Queue
 * across the whole application
 */
public class VolleyManager {
    static VolleyManager sInstance;
    private RequestQueue mRequestQueue;

    public static synchronized VolleyManager getInstance() {
        if (sInstance == null) {
            sInstance = new VolleyManager();
        }

        return sInstance;
    }

    public VolleyManager() {
        mRequestQueue = Volley.newRequestQueue(AtlassianApplication.getInstance());
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
