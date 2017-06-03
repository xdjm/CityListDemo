package com.xd.commander.citylistdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/5/26 at 19:32
 * <p>
 * Copyright (C) 2017 By Administrator
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
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>
{
    private Context mContext;
    private int mLayoutId;
    private List<T> mlist;


    protected BaseAdapter(Context context, List<T> list)
    {
        mContext = context;
        mLayoutId = getItemLayout();
        mlist = list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        return BaseViewHolder.get(mContext, parent, mLayoutId);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position)
    {
        convert(holder, mlist.get(position));
    }
    @Override
    public int getItemCount()
    {
        return mlist.size();
    }
    public abstract void convert(BaseViewHolder holder, T t);
    protected abstract int getItemLayout();
}
