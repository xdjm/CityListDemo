package com.xd.commander.citylistdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xd.commander.citylistdemo.adapter.AdapterRecyclerView;
import com.xd.commander.citylistdemo.adapter.BaseAdapter;
import com.xd.commander.citylistdemo.adapter.BaseViewHolder;
import com.xd.commander.citylistdemo.adapter.ItemLine;
import com.xd.commander.citylistdemo.pojo.City;
import com.xd.commander.citylistdemo.utils.HanziToPinyin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinkedList<String> linkedList;
    private static String COLORS_FILE_NAME = "city.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<City> list = new ArrayList<>();
        LinearLayoutManager layout = new LinearLayoutManager(this);
        //先显示底部数据
        //layout.setStackFromEnd(true);
        //反转整个列表顺序
        //layout.setReverseLayout(true);
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setLayoutManager(layout);
        final City[] cities = new Gson().fromJson(loadJson(this),City[].class);

        for(int i=0;i<cities.length;i++){
            ArrayList<HanziToPinyin.Token> list1 = HanziToPinyin.getInstance().get(cities[i].getName());
             linkedList=new LinkedList<>();
            linkedList.add( list1.get(0).target.substring(0,1));
            list.add(cities[i]);
        }
        recyclerView.setAdapter(new BaseAdapter<City>(this,list) {

            @Override
            public void convert(BaseViewHolder holder, City city) {
//                TextView textView = holder.getView(R.id.tv);
//                textView.setText(city.getName());
                holder.setText(R.id.tv,city.getName());
            }
            @Override
            public int getItemLayout() {
                return R.layout.layout_main_item;
            }
        });
        recyclerView.addItemDecoration(new ItemLine());
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(recyclerView);
    }
    private static String loadJson(Context context) {

        String jsonString;

        try {
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
