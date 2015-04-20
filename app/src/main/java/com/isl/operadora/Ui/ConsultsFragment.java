package com.isl.operadora.Ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.andexert.library.RippleView;
import com.isl.operadora.Base.BaseFragment;
import com.isl.operadora.R;
import com.isl.operadora.Util.Logger;

public class ConsultsFragment extends BaseFragment implements View.OnClickListener{
    private EditText mTextPhone;
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
        View view = inflater.inflate(R.layout.fragment_consult, container, false);

        mTextPhone = (EditText) view.findViewById(R.id.textPhone);

        ((Button) view.findViewById(R.id.one)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.two)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.tree)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.four)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.five)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.six)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.seven)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.eight)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.nine)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.zero)).setOnClickListener(this);

        ((RippleView) view.findViewById(R.id.call)).setOnClickListener(this);
        ((RippleView) view.findViewById(R.id.search)).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v instanceof Button)
        {
            Button button = (Button) v;
            mTextPhone.append(button.getText());
        }
        else if(v instanceof RippleView)
        {
            switch (v.getId())
            {
                case R.id.call:
                    Logger.t("CALL");
                    break;

                case R.id.search:
                    Logger.t("BUSCAR");
                    break;
            }
        }

    }
}
