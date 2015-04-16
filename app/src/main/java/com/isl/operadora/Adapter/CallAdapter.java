package com.isl.operadora.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.isl.operadora.Model.Calls;
import com.isl.operadora.R;
import com.isl.operadora.Ui.CallFragment;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by webx on 15/04/15.
 */
public class CallAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private CallFragment mCallFragment;
    private Activity mActivity;
    private ArrayList<Calls> mCalls;
    private LayoutInflater mInflater;

    public CallAdapter(CallFragment callFragment, Activity activity, ArrayList<Calls> calls)
    {
        this.mCallFragment = callFragment;
        this.mActivity = activity;
        this.mCalls = calls;
        this.mInflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return mCalls.size();
    }

    @Override
    public Calls getItem(int position) {
        return mCalls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.list_contacts, null);
            myViewHolder = new MyViewHolder();

            myViewHolder.name = (TextView) convertView.findViewById(R.id.name);
            myViewHolder.label = (TextView) convertView.findViewById(R.id.label);
            myViewHolder.number = (TextView) convertView.findViewById(R.id.number);
            myViewHolder.item = (RippleView) convertView.findViewById(R.id.item);

            convertView.setTag(myViewHolder);
        }
        else
        {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        myViewHolder.item.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCallFragment.onClickListView(position);
            }
        });

        myViewHolder.name.setText(getItem(position).getNumber());
        myViewHolder.number.setVisibility(View.GONE);
        myViewHolder.label.setText(String.valueOf(getItem(position).getDuration()));

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = mInflater.inflate(R.layout.listview_contacts_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        String headerText = String.valueOf(getItem(position).getNumber().subSequence(0, 1).charAt(0));
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return mCalls.get(position).getNumber().subSequence(0, 1).charAt(0);
    }

    private class MyViewHolder {
        public TextView name;
        public TextView number;
        public TextView label;
        public RippleView item;
    }

    class HeaderViewHolder {
        TextView text;
    }
}
