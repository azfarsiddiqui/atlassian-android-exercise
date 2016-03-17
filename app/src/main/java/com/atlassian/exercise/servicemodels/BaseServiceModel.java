package com.atlassian.exercise.servicemodels;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.android.volley.toolbox.RequestFuture;
import com.atlassian.exercise.AtlassianApplication;
import com.atlassian.exercise.exceptions.NetworkNotFoundException;
import com.atlassian.exercise.network.ApplicationHttpGetRequest;
import com.atlassian.exercise.network.VolleyManager;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * This Base service model provides methods to conveniently execute
 * HTTP requests. All service models should extend from this class
 */
public abstract class BaseServiceModel {

    public Observable<String> doHttpGet(String url) {
        RequestFuture<String> future = RequestFuture.newFuture();
        ApplicationHttpGetRequest request = new ApplicationHttpGetRequest(url, future, future);

        if (!isNetworkAvailable(AtlassianApplication.getInstance())) {
            return Observable.error(new NetworkNotFoundException());
        }

        VolleyManager.getInstance().getRequestQueue().add(request);
        return Observable.from(future).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            Network[] networks = connectivity.getAllNetworks();

            for (Network network : networks) {
                NetworkInfo networkInfo = connectivity.getNetworkInfo(network);
                if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }

        return false;
    }
}
