package com.example.producktivity;

import android.content.Context;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.example.producktivity.dbs.blacklist.BlacklistEntry;
import com.example.producktivity.dbs.blacklist.Category;
import com.example.producktivity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ClassificationClient {
    private String URL = "https://api.appmonsta.com/v1/stores/android/details/";
    private String UserName = "23bd817660d5852948f01392ffd4457dd549afa4";
    private String PassWord = "X";

    RequestQueue queue;

    public ClassificationClient(Context t) {
        queue = Volley.newRequestQueue(t);
    }


    public String requestAppCategory(String appId) {
        String url = URL + appId + ".json?country=US";
        final String[] category = {""};
        final String[] appType = {""};
        if (appId == null)
            return null;
        System.out.println(url);
        if(appId.equalsIgnoreCase("com.example.producktivity")){
            return "PRODUCKTIVITY";
        }
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, future, future){
                    @Override
                    public Map<String, String> getHeaders(){
                        Map<String,String> headers = new HashMap<>();
                        // add headers <key,value>
                        String credentials = UserName+":"+PassWord;
                        String auth = "Basic "
                                + Base64.encodeToString(credentials.getBytes(StandardCharsets.UTF_8),
                                Base64.DEFAULT);
                        headers.put("Authorization", auth);
                        return headers;
                    }
                };
        queue.add(jsonObjectRequest);
        try {
            JSONObject response = future.get();
            category[0] = response.getString("genre_id");
            appType[0] = response.getString("app_type");
            if(appType[0].equalsIgnoreCase("GAME"))
                return "GAME";
            else
                return category[0];
        } catch (InterruptedException | ExecutionException | JSONException e) {
            // handle the error
            e.printStackTrace();
            return category[0];
        }
       // System.out.println("added to queue");
       // return "SYSTEM";
    }
}