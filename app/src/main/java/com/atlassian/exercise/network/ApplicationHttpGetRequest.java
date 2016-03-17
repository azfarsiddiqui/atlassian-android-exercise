package com.atlassian.exercise.network;

import com.android.volley.Response;

public class ApplicationHttpGetRequest extends ApplicationHttpRequest {

    public ApplicationHttpGetRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, listener, errorListener);
    }
}
