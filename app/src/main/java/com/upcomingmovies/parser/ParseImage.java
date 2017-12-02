package com.upcomingmovies.parser;

import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shuhab abs-pc-2f-28 on 2/12/17.
 */

public class ParseImage {
    String response;
    public ParseImage(String response) {
        this.response = response;
    }

    public JSONObject getJSONObject() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONArray getJSONArr(JSONObject imageObject) {
        JSONArray jsonArray = null;
        try {
            jsonArray = imageObject.getJSONArray("backdrops");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public JSONObject getArrObject(JSONArray imageArr, int imageCounter) {
        JSONObject jsonObject = null;
        try {
            jsonObject = imageArr.getJSONObject(imageCounter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String getImagePath(JSONObject arrObject) {
        String imagePath = null;
        try {
            imagePath = arrObject.getString("file_path");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imagePath;
    }
}
