package com.isl.operadora.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.isl.operadora.Model.Contact;
import com.isl.operadora.R;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by webx on 08/04/15.
 */
public class ContactAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    public Activity mActivity;
    public ArrayList<Contact> mContacs;
    public LayoutInflater inflater;

    public ContactAdapter(Activity act, ArrayList<Contact> contacts){
        mActivity = act;
        mContacs = contacts;
        inflater = LayoutInflater.from(act);
    }

    @Override
    public int getCount() {
        return mContacs.size();
    }

    @Override
    public Contact getItem(int position) {
        return mContacs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_contacts, null);
            myViewHolder = new MyViewHolder();
            convertView.setTag(myViewHolder);
        }else{
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        myViewHolder.name = (TextView) convertView.findViewById(R.id.name);
        myViewHolder.name.setText(getItem(position).getName());

        myViewHolder.number = (TextView) convertView.findViewById(R.id.number);
        myViewHolder.number.setText(getItem(position).getNumber());

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        String headerText = String.valueOf(getItem(position).getName().subSequence(0, 1).charAt(0));
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return mContacs.get(position).getName().subSequence(0, 1).charAt(0);
    }

    private class MyViewHolder {
        public TextView name;
        public TextView number;
    }

    class HeaderViewHolder {
        TextView text;
    }
}
