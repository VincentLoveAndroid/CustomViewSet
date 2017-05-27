package com.example.mingren.customviewset.Ob;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by sunzhiyong on 17/3/14.
 */

public class CityBean implements Serializable {
    private String cityCode;
    private String cityName;

    public CityBean(String cityCode, String cityName) {
        this.cityCode = cityCode;
        this.cityName = cityName;
    }

    public CityBean(){

    }

    public CityBean(JSONObject json){
        this.cityCode = json.optString("item_code");
        this.cityName = json.optString("item_name");
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return cityName;
    }
}
