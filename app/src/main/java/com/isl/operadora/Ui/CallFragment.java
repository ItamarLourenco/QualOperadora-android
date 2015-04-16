package com.isl.operadora.Ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.isl.operadora.Adapter.CallAdapter;
import com.isl.operadora.Base.BaseFragment;
import com.isl.operadora.Model.Calls;
import com.isl.operadora.R;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class CallFragment extends BaseFragment {

    private ArrayList<Calls> mCalls;
    private StickyListHeadersListView mListView;

    public static CallFragment newInstance() {
        CallFragment fragment = new CallFragment();
        return fragment;
    }

    public CallFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call, container, false);

        mCalls = Calls.getCalls();

        mListView = (StickyListHeadersListView) view.findViewById(R.id.listView);
        mListView.setAdapter(new CallAdapter(this, getActivity(), mCalls));

        return view;
    }

    public void onClickListView(int position)
    {

    }
}
