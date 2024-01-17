package com.shime.betpowersports;

import org.json.JSONArray;
import org.json.JSONException;

public class Result {
    private String idbetting;
    private String date;
    private String time;
    private String catagory;
    private String league;
    private String club1;
    private String club2;
    private String club1_score;
    private String club2_score;
    private String prediction;
    private String odd;
    private String result;
    private String type;
    public Result(){

    }
    public Result(String idbetting, String date, String time, String catagory, String league, String club1, String club2, String club1_score, String club2_score, String prediction,  String odd, String result, String type) {
        this.idbetting = idbetting;
        this.date = date;
        this.time = time;
        this.catagory = catagory;
        this.league = league;
        this.club1 = club1;
        this.club2 = club2;
        this.club1_score = club1_score;
        this.club2_score = club2_score;
        this.prediction = prediction;
        this.result = result;
        this.type = type;
        this.odd = odd;
    }

    public Result(JSONArray  jsonObject) throws JSONException {

        for (int i=0;i<jsonObject.length();i++){
            this.idbetting = jsonObject.getJSONObject(i).getString("idbetting");
            this.date = jsonObject.getJSONObject(i).getString("date");
            this.time = jsonObject.getJSONObject(i).getString("time");
            this.catagory = jsonObject.getJSONObject(i).getString("catagory");
            this.league = jsonObject.getJSONObject(i).getString("league");
            this.club1 = jsonObject.getJSONObject(i).getString("club1");
            this.club2 = jsonObject.getJSONObject(i).getString("club2");
            this.club1_score = jsonObject.getJSONObject(i).getString("club1_score");
            this.club2_score = jsonObject.getJSONObject(i).getString("club2_score");
            this.prediction = jsonObject.getJSONObject(i).getString("prediction");
            this.odd = jsonObject.getJSONObject(i).getString("odd");
            this.result = jsonObject.getJSONObject(i).getString("result");
            this.type = jsonObject.getJSONObject(i).getString("type");
        }
    }
    public Result(Result  jsonObject) throws JSONException {

//            this.idbetting = jsonObject.getString("idbetting");
//            this.date = jsonObject.getString("date");
//            this.time = jsonObject.getString("time");
//            this.catagory = jsonObject.getString("catagory");
//            this.league = jsonObject.getString("league");
//            this.club1 = jsonObject.getString("club1");
//            this.club2 = jsonObject.getString("club2");
//            this.club1_score = jsonObject.getString("club1_score");
//            this.club2_score = jsonObject.getString("club2_score");
//            this.prediction = jsonObject.getString("prediction");
//            this.result = jsonObject.getString("result");
//            this.type = jsonObject.getString("type");
    }

    public void setIdbetting(String idbetting) {
        this.idbetting = idbetting;
    }

    public String getIdbetting() {
        return idbetting;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getCatagory() {
        return catagory;
    }

    public String getLeague() {
        return league;
    }

    public String getClub1() {
        return club1;
    }

    public String getClub2() {
        return club2;
    }

    public String getClub1_score() {
        return club1_score;
    }

    public String getClub2_score() {
        return club2_score;
    }

    public String getPrediction() {
        return prediction;
    }
    public String getOdd() {
        return odd;
    }

    public String getResult() {
        return result;
    }

    public String getType() {
        return type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public void setClub1(String club1) {
        this.club1 = club1;
    }

    public void setClub2(String club2) {
        this.club2 = club2;
    }

    public void setClub1_score(String club1_score) {
        this.club1_score = club1_score;
    }

    public void setClub2_score(String club2_score) {
        this.club2_score = club2_score;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void setOdd(String odd) {
        this.odd = odd;
    }
}