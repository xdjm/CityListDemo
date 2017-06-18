package com.xd.commander.citylistdemo.data;

import com.google.gson.Gson;
import com.xd.commander.citylistdemo.pojo.City;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Copyright (C) 2017 By yjm at 8:58
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class GetData {
    private static  String[] barList = {"定位", "热门", "A", "B", "C", "D", "E", "F", "G", "H" , "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "W", "X", "Y", "Z"};
    public static LinkedList<String> getBar(){
        LinkedList<String> list = new LinkedList<>();
        Collections.addAll(list, barList);
        return list;
    }
    public static LinkedList<City.CitiesBean> getCity(String s) {
        LinkedList<City.CitiesBean> list = new LinkedList<>();
        City[] cities = new Gson().fromJson(s, City[].class);
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
        LinkedList<City.CitiesBean> link = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i == 0)
                link.add(new City.CitiesBean("A"));
            else if (list.get(i - 1).getChar() != list.get(i).getChar()) {
                link.add(new City.CitiesBean(String.valueOf(list.get(i).getChar()).toUpperCase()));
            }
            link.add(list.get(i));
        }
        return link;
    }
}
