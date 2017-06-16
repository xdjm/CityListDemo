package com.xd.commander.citylistdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.xd.commander.citylistdemo.pojo.City;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinkedList<City.CitiesBean> list = new LinkedList<>();
        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(layout);
        final City[] cities = new Gson().fromJson(loadJson(this), City[].class);
        for (City city : cities) {
            for (int ii = 0; ii < city.getCities().size(); ii++)
                list.add(city.getCities().get(ii));
        }

        Collections.sort(list, new Comparator<City.CitiesBean>() {
            @Override
            public int compare(City.CitiesBean o1, City.CitiesBean o2) {
                return o1.getChar() - o2.getChar();
            }
        });

        LinkedList<City.CitiesBean> link =new LinkedList<>();
        for(int i=0;i<list.size();i++){
            if(i==0)
                link.add(new City.CitiesBean("A"));
            else if(list.get(i-1).getChar()!=list.get(i).getChar()){
                link.add(new City.CitiesBean(String.valueOf(list.get(i).getChar()).toUpperCase()));
            }
            link.add(list.get(i));
        }
        recyclerView.setAdapter(new BaseQuickAdapter<City.CitiesBean, BaseViewHolder>(R.layout.layout_main_item, link) {

            /**
             * Implement this method and use the helper to adapt the view to the given item.
             *
             * @param helper A fully initialized helper.
             * @param item   The item that needs to be displayed.
             */
            @Override
            protected void convert(BaseViewHolder helper, City.CitiesBean item) {
                helper.setText(R.id.tv, item.getName());
            }
        });
    }
    private static String loadJson(Context context) {

        String jsonString;

        try {
            String COLORS_FILE_NAME = "city.json";
            InputStream is = context.getAssets().open(COLORS_FILE_NAME);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jsonString;
    }
}
