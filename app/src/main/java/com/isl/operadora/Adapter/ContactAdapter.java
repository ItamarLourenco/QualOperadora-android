package com.isl.operadora.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.isl.operadora.Application.AppController;
import com.isl.operadora.Model.Contact;
import com.isl.operadora.R;
import com.isl.operadora.Ui.ContactsFragment;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Createcd by webx on 08/04/15.
 */
public class ContactAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    public Activity mActivity;
    public ContactsFragment mFragment;
    public ArrayList<Contact> mContacts;
    public LayoutInflater mInflater;

    public ContactAdapter(ContactsFragment fragment, Activity act, ArrayList<Contact> contacts){
        mActivity = act;
        mContacts = contacts;
        mInflater = LayoutInflater.from(act);
        mFragment = fragment;
    }

    @Override
    public int getCount() {
        return mContacts.size();
    }

    @Override
    public Contact getItem(int position) {
        return mContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.list_contacts, null);

            myViewHolder = new MyViewHolder();

            myViewHolder.item = (RippleView) convertView.findViewById(R.id.item);
            myViewHolder.name = (TextView) convertView.findViewById(R.id.name);
            myViewHolder.number = (TextView) convertView.findViewById(R.id.number);
            myViewHolder.label = (TextView) convertView.findViewById(R.id.label);


            convertView.setTag(myViewHolder);
        }else{
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        myViewHolder.item.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mFragment.onClickListView(position);
            }
        });

        myViewHolder.name.setText(getItem(position).getName());
        myViewHolder.number.setText(getItem(position).getNumber());
        myViewHolder.label.setText(getItem(position).getLabel());

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
        try{
            String headerText = String.valueOf(getItem(position).getName().subSequence(0, 1).charAt(0));
            holder.text.setText(headerText);

            return convertView;
        }catch (NullPointerException e){
            return new View(AppController.getInstance());
        }
    }

    @Override
    public long getHeaderId(int position) {
        return getItem(position).getName().subSequence(0, 1).charAt(0);
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
