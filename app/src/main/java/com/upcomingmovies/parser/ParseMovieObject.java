package com.upcomingmovies.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shuhab abs-pc-2f-28 on 1/12/17.
 */

public class ParseMovieObject {
    private String response;
    public ParseMovieObject(String response) {
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

    public JSONArray getResultArr(JSONObject jsonObject) {
        JSONArray resultArr = null;
        try {
            resultArr = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultArr;
    }

    public JSONObject getArrObj(JSONArray resultArr, int upcomingMovieCount) {
        JSONObject arrObj = null;
        try {
            arrObj = resultArr.getJSONObject(upcomingMovieCount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrObj;
    }

    public String getTitle(JSONObject arrObject) {
        String title = null;
        try {
            title = arrObject.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return title;
    }

    public String getPosterImage(JSONObject arrObject) {
        String posterImage = null;
        try {
            posterImage = arrObject.getString("poster_path");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return posterImage;
    }

    public String getReleaseDate(JSONObject arrObject) {
        String releaseDate = null;
        try {
            releaseDate = arrObject.getString("release_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return releaseDate;
    }


    public Boolean getIsAdult(JSONObject arrObject) {
        Boolean isAdult = null;
        try {
            isAdult = arrObject.getBoolean("adult");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isAdult;
    }

    public String getMovieId(JSONObject arrObject) {
        String id = null;
        try {
            id = arrObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }
}
