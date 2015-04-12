package com.isl.operadora.Ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.isl.operadora.Base.BaseFragment;
import com.isl.operadora.R;

public class ConsultsFragment extends BaseFragment {
    public static ConsultsFragment newInstance() {
        ConsultsFragment fragment = new ConsultsFragment();
        return fragment;
    }

    public ConsultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consult, container, false);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}
