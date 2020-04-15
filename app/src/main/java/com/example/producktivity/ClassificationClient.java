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
        System.out.println(url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("got a response");
                            category[0] = response.getString("genre_id");
                            appType[0] = response.getString("app_type");
                            System.out.print(category[0]);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        error.printStackTrace();

                    }
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                String credentials = UserName + ":" + PassWord;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
                        /*Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Username", UserName);
                        headers.put("Password", PassWord);
                        return headers;*/
            }
        };
        queue.add(jsonObjectRequest);
        if (appType[0].equalsIgnoreCase("GAME"))
            return "GAME";
        else
            return category[0];
    }
}