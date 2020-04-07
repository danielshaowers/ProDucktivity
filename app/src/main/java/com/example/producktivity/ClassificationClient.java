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

public class ClassificationClient{
    private String URL = "https://api.appmonsta.com/v1/stores/android/details/";
    private String UserName = "";
    private String PassWord = "X";
    public ClassificationClient(){

    }

    public String requestAppCategory(String appId) {
        String url = URL + appId + ".json?country=US";
        final String[] category = {""};

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            category[0] = parseJsonForCategory(response);
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
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("User", UserName);
                        params.put("Pass", PassWord);
                        return params;
                    }
                };
        return category[0];
    }

    private String parseJsonForCategory(JSONObject Json) throws JSONException {
        return Json.getString("genre_id");
    }
}