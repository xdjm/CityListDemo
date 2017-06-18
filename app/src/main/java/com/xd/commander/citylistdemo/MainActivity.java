package com.xd.commander.citylistdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.animation.AlphaInAnimation;
import com.xd.commander.citylistdemo.data.GetData;
import com.xd.commander.citylistdemo.pojo.City;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private SearchView searchView;
    private RecyclerView recyclerViewCity, recyclerViewBar;
    private LinearLayoutManager linearLayoutManager;
    private BaseQuickAdapter<String, BaseViewHolder> adapter_bar;
    private BaseQuickAdapter<City.CitiesBean, BaseViewHolder> adapter_city;
    private int[] section_position = {0, 12, 38, 58, 79, 83, 97, 109, 156, 186, 193, 223, 232, 249, 262, 279, 283, 318, 337, 357, 382, 414};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRecyclerView();
        setAdapter();
        bindingAll();
        addLocationLayout();
        addHotCityLayout();
    }

    private void setRecyclerView() {
        searchView = (SearchView) findViewById(R.id.search_bar);
        recyclerViewCity = (RecyclerView) findViewById(R.id.recycler);
        recyclerViewBar = (RecyclerView) findViewById(R.id.recyclerbar);
        recyclerViewCity.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBar.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBar.stopScroll();
        recyclerViewCity.addItemDecoration(new ItemDecoration() {

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                int childCount = parent.getChildCount();
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();
                Paint dividerPaint = new Paint();
                dividerPaint.setColor(ContextCompat.getColor(MainActivity.this, android.R.color.darker_gray));
                for (int i = 0; i < childCount - 1; i++) {
                    if (i != 0) {
                        View view = parent.getChildAt(i);
                        float top = view.getBottom();
                        float bottom = view.getBottom() + 2;
                        c.drawRect(left, top, right, bottom, dividerPaint);
                    }
                }
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 1;
            }
        });
    }

    private void setAdapter() {
        linearLayoutManager = (LinearLayoutManager) recyclerViewCity.getLayoutManager();
        adapter_city = new BaseQuickAdapter<City.CitiesBean, BaseViewHolder>(R.layout.layout_main_item, GetData.getCity(loadJson(this))) {
            @Override
            protected void convert(BaseViewHolder helper, City.CitiesBean item) {
                helper.setText(R.id.tv, item.getName());
            }
        };
        adapter_city.openLoadAnimation(new AlphaInAnimation());
        adapter_city.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MainActivity.this, GetData.getCity(loadJson(MainActivity.this)).get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter_bar = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.layout_main_item_bar, GetData.getBar()) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.textView, item);
            }
        };
        adapter_bar.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position != 0 && position != 1)
                    linearLayoutManager.scrollToPositionWithOffset(section_position[position - 2] + 1, 0);
            }
        });
    }

    private void addLocationLayout() {
        View locationView = getLayoutInflater().inflate(R.layout.layout_main_item_location, (ViewGroup) recyclerViewCity.getParent(), false);
        final Button btn = (Button) locationView.findViewById(R.id.btn_loaction);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setText("定位中");
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn.setText("郑州");
                    }
                }, 2000);
            }
        });
        adapter_city.setHeaderView(locationView);
    }

    private void addHotCityLayout() {
        View hotCityView = getLayoutInflater().inflate(R.layout.layout_main_item_hotcity, (ViewGroup) recyclerViewCity.getParent(), false);
        adapter_city.addHeaderView(hotCityView);
    }

    private void bindingAll() {
        recyclerViewCity.setAdapter(adapter_city);
        recyclerViewBar.setAdapter(adapter_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private static String loadJson(Context context) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open("city.json");
            byte[] buffer = new byte[is.available()];
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
