package com.isl.operadora.Ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.isl.operadora.Base.BaseFragment;
import com.isl.operadora.R;

public class CallFragment extends BaseFragment {

    public static CallFragment newInstance() {
        CallFragment fragment = new CallFragment();
        return fragment;
    }

    public CallFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_call, container, false);
    }
}
