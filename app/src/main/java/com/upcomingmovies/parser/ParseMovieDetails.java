package com.upcomingmovies.parser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shuhab abs-pc-2f-28 on 2/12/17.
 */

public class ParseMovieDetails {
    private String response;
    public ParseMovieDetails(String response) {
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

    public String getOverview(JSONObject jsonObject) {
        String overview = null;
        try {
            overview = jsonObject.getString("overview");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return overview;
    }

    public String getTitle(JSONObject jsonObject) {
        String title = null;
        try {
            title = jsonObject.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return title;
    }

    public String getPopularity(JSONObject jsonObject) {
        String popularity = null;
        try {
            popularity = jsonObject.getString("popularity");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return popularity;
    }
}
