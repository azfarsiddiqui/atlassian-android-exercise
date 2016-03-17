package com.atlassian.exercise.network;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Base class for all the network requests must originate here
 * to include any common headers, params etc to aid in authorization
 * or whatever
 */
public abstract class ApplicationHttpRequest extends StringRequest {

    public ApplicationHttpRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }
}
