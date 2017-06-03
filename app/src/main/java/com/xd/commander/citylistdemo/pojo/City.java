package com.xd.commander.citylistdemo.pojo;

import java.util.List;

/**
 * ${project_name} Created by Administrator on 2017/5/25 at${time}
 * <p>
 * Copyright (C) 2017 By yjm
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class City {

    /**
     * name : 北京
     * cities : [{"name":"东城"},{"name":"西城"},{"name":"朝阳"},{"name":"丰台"},{"name":"石景山"},{"name":"海淀"},{"name":"门头沟"},{"name":"房山"},{"name":"通州"},{"name":"顺义"},{"name":"昌平"},{"name":"大兴"},{"name":"怀柔"},{"name":"平谷"}]
     */

    private String name;
    private List<CitiesBean> cities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CitiesBean> getCities() {
        return cities;
    }

    public void setCities(List<CitiesBean> cities) {
        this.cities = cities;
    }

    public static class CitiesBean {
        /**
         * name : 东城
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
