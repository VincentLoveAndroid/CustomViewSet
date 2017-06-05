package com.example.mingren.customviewset.adapter;

import android.os.AsyncTask;

import com.example.mingren.customviewset.MyApplication;
import com.example.mingren.customviewset.Ob.CityBean;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CityParserTask extends AsyncTask<Void, Void, CityParserTask.CitysEntity> {
    private CityParserCallBack cityParserCallBack;

    @Override
    protected CitysEntity doInBackground(Void... voids) {

        CitysEntity citysEntity = new CitysEntity();

        //省市区数据解析
        JSONArray jsonArray = null;
        InputStream inputStream = null;
        try {
            inputStream = MyApplication.context.getResources().getAssets().open("city.json");
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);

            jsonArray = new JSONArray(new String(bytes, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if(jsonArray != null){
            JSONArray provinceArray = jsonArray.optJSONArray(0);
            JSONArray cityArray = jsonArray.optJSONArray(1);
            JSONArray areaArray = jsonArray.optJSONArray(2);

            Map<String, Integer> proKeys = new HashMap<>();
            Map<String, Integer> cityKeys = new HashMap<>();
            for(int i = 0; i < provinceArray.length(); i++){
                CityBean bean = new CityBean(provinceArray.optJSONObject(i));
                citysEntity.getProvinces().add(bean);
                citysEntity.getCitys().add(new ArrayList<CityBean>());
                citysEntity.getAreas().add(new ArrayList<ArrayList<CityBean>>());

                proKeys.put(bean.getCityCode().substring(0,2), i);
            }

            int index = 0;
            int index1 = 0;
            ArrayList<ArrayList<CityBean>> cityList;

            for(int i = 0; i < cityArray.length(); i++){
                CityBean bean = new CityBean(cityArray.optJSONObject(i));
                index = proKeys.get(bean.getCityCode().substring(0,2));
                citysEntity.getCitys().get(index).add(bean);

                cityList = citysEntity.getAreas().get(index);
                cityList.add(new ArrayList<CityBean>());

                cityKeys.put(bean.getCityCode().substring(0, 4), cityList.size() - 1);
            }

            String key = "";
            for(int i = 0; i < areaArray.length(); i++){
                CityBean bean = new CityBean(areaArray.optJSONObject(i));

                if(bean.getCityCode().startsWith("110")
                        || bean.getCityCode().startsWith("120")
                        || bean.getCityCode().startsWith("310")
                        || bean.getCityCode().startsWith("500")){
                    key = bean.getCityCode().substring(0, 3) + "1";

                } else if(bean.getCityCode().equals("441900")){
                    key = "4419";
                } else if(bean.getCityCode().equals("442000")){
                    key = "4420";
                } else if(bean.getCityCode().equals("429004")){
                    key = "4296";
                } else if(bean.getCityCode().equals("429005")){
                    key = "4297";
                } else if(bean.getCityCode().equals("429006")){
                    key = "4298";
                } else if(bean.getCityCode().equals("429021")){
                    key = "4299";
                } else if(bean.getCityCode().equals("469001")){
                    key = "4681";
                } else if(bean.getCityCode().equals("469002")){
                    key = "4682";
                } else if(bean.getCityCode().equals("469003")){
                    key = "4683";
                } else if(bean.getCityCode().equals("469005")){
                    key = "4684";
                } else if(bean.getCityCode().equals("469006")){
                    key = "4685";
                } else if(bean.getCityCode().equals("469007")){
                    key = "4686";
                } else if(bean.getCityCode().equals("469021")){
                    key = "4687";
                } else if(bean.getCityCode().equals("469022")){
                    key = "4688";
                } else if(bean.getCityCode().equals("469023")){
                    key = "4689";
                } else if(bean.getCityCode().equals("469024")){
                    key = "4679";
                } else if(bean.getCityCode().equals("469025")){
                    key = "4691";
                } else if(bean.getCityCode().equals("469026")){
                    key = "4692";
                } else if(bean.getCityCode().equals("469027")){
                    key = "4693";
                } else if(bean.getCityCode().equals("469028")){
                    key = "4694";
                } else if(bean.getCityCode().equals("469029")){
                    key = "4695";
                } else if(bean.getCityCode().equals("469030")){
                    key = "4696";
                } else if(bean.getCityCode().equals("469031")){
                    key = "4697";
                } else if(bean.getCityCode().equals("469032")){
                    key = "4698";
                } else if(bean.getCityCode().equals("469033")){
                    key = "4699";
                } else if(bean.getCityCode().equals("419001")){
                    key = "4199";
                } else {
                    key = bean.getCityCode().substring(0,4);
                }

                if(cityKeys.get(key) != null){
                    index = proKeys.get(bean.getCityCode().substring(0,2));
                    index1 = cityKeys.get(key);
                    citysEntity.getAreas().get(index).get(index1).add(bean);
                }
            }
        }

        return citysEntity;
    }

    @Override
    protected void onPostExecute(CitysEntity o) {
        if(o != null && cityParserCallBack != null){
            cityParserCallBack.onParseCitys(o);
        }
    }

    public CityParserTask setCityParserCallBack(CityParserCallBack cityParserCallBack) {
        this.cityParserCallBack = cityParserCallBack;
        return this;
    }

    /**
     * 解析回调
     */
    public interface CityParserCallBack{
        void onParseCitys(CitysEntity citysEntity);
    }

    /**
     * 省市区数据封装
     */
    public static class CitysEntity{
        /**
         * 省份数据
         */
        private ArrayList<CityBean> provinces;

        /**
         * 城市数据
         */
        private ArrayList<ArrayList<CityBean>> citys;

        /**
         * 区域数据
         */
        private ArrayList<ArrayList<ArrayList<CityBean>>> areas;

        public CitysEntity() {
            provinces = new ArrayList<>();
            citys = new ArrayList<>();
            areas = new ArrayList<>();
        }

        public ArrayList<CityBean> getProvinces() {
            return provinces;
        }

        public void setProvinces(ArrayList<CityBean> provinces) {
            this.provinces = provinces;
        }

        public ArrayList<ArrayList<CityBean>> getCitys() {
            return citys;
        }

        public void setCitys(ArrayList<ArrayList<CityBean>> citys) {
            this.citys = citys;
        }

        public ArrayList<ArrayList<ArrayList<CityBean>>> getAreas() {
            return areas;
        }

        public void setAreas(ArrayList<ArrayList<ArrayList<CityBean>>> areas) {
            this.areas = areas;
        }
    }
}
