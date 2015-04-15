package com.isl.operadora.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.isl.operadora.R;
import com.isl.operadora.Ui.MainActivity;
import com.isl.operadora.Widgets.CustomFontTextView;

import java.util.ArrayList;

/**
 * Created by itamarlourenco on 11/04/15.
 */
public class DddAdapter extends BaseAdapter {

    private ArrayList<String> mDdds;
    public LayoutInflater mInflater;
    public MainActivity mMainActivity;

    public DddAdapter(MainActivity act, ArrayList<String> ddds)
    {
        this.mDdds = ddds;
        mInflater = LayoutInflater.from(act);
        mMainActivity = act;
    }

    @Override
    public int getCount() {
        return mDdds.size();
    }

    @Override
    public String getItem(int position) {
        return mDdds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.row_ddd, null);
            myViewHolder = new MyViewHolder();
            convertView.setTag(myViewHolder);
        }else{
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        myViewHolder.ddd = (TextView) convertView.findViewById(R.id.ddd);
        myViewHolder.ddd.setText(getItem(position));

        myViewHolder.item = (RippleView) convertView.findViewById(R.id.item);
        myViewHolder.item.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mMainActivity.onClickListView(position);
            }
        });

        return convertView;
    }


    public class MyViewHolder{
        public TextView ddd;
        public RippleView item;
    }


}
