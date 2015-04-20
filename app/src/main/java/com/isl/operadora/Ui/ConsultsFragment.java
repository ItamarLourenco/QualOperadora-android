package com.isl.operadora.Ui;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.isl.operadora.Base.BaseFragment;
import com.isl.operadora.R;
import com.isl.operadora.Util.Logger;
import com.isl.operadora.Util.Mask;

public class ConsultsFragment extends BaseFragment implements View.OnClickListener{

    private EditText mTextPhone;
    private ImageView mBackSpace;

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
        mBackSpace = (ImageView) view.findViewById(R.id.backspace);
        mBackSpace.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mTextPhone.setText("");
                return false;
            }
        });
        

        mBackSpace.setOnClickListener(this);
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
        else if(v instanceof ImageView)
        {
            switch (v.getId())
            {
                case R.id.backspace:
                    onBackSpace();
                    break;
            }
        }
    }

    private void onBackSpace() {
        if(!TextUtils.isEmpty(mTextPhone.getText()))
        {
            StringBuffer stringBuffer = new StringBuffer(mTextPhone.getText());
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            mTextPhone.setText(stringBuffer.toString().trim());
            mTextPhone.setSelection(mTextPhone.getText().length());
        }
    }
}
