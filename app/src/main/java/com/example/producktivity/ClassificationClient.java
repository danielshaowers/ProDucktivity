package com.example.producktivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ClassificationClient {
    private String URL = "https://api.appmonsta.com/v1/stores/android/details/";
    private String UserName = "23bd817660d5852948f01392ffd4457dd549afa4";
    private String PassWord = "X";

    public ClassificationClient() {}

    public String requestAppCategory(String appId) {
        String url = URL + appId + ".json?country=US";
        final String[] category = {""};
        final String[] appType = {""};

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            category[0] = response.getString("genre_id");
                            appType[0] = response.getString("app_type");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Username", UserName);
                headers.put("Password", PassWord);
                return headers;
            }
        };
        if (appType[0].equalsIgnoreCase("GAME"))
            return "GAME";
        else
            return category[0];
    }
}