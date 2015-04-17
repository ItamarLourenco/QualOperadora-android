package com.isl.operadora.Ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.gc.materialdesign.views.ButtonFlat;
import com.isl.operadora.Adapter.CallAdapter;
import com.isl.operadora.Adapter.ContactAdapter;
import com.isl.operadora.Base.BaseFragment;
import com.isl.operadora.Model.Calls;
import com.isl.operadora.Model.Contact;
import com.isl.operadora.R;
import com.isl.operadora.Util.Util;
import com.isl.operadora.Widgets.CustomFontTextView;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class CallFragment extends BaseFragment implements View.OnClickListener{


    private LinearLayout mLoadingLinear;
    private LinearLayout mCarriesLinear;
    private LinearLayout mNotFound;
    private LinearLayout wLabelCanvas;
    private LinearLayout mLabelCarrieOld;

    private CustomFontTextView mCarrie;
    private CustomFontTextView mPortabilty;
    private CustomFontTextView mWhen;
    private CustomFontTextView mCarrieOld;
    private CustomFontTextView mContact;
    private CustomFontTextView mPhone;
    private CustomFontTextView mLoadingText;

    private ButtonFlat mButtonLougout;
    private ButtonFlat mButtonLougoutNotFound;
    private ButtonFlat mButtonSave;
    private ButtonFlat mButtonCall;
    private ButtonFlat mSmsCall;

    private ImageView mCarrieImage;
    private AlertDialog mDialog;
    private ContactAdapter mContactAdapter;

    private Contact currentContact;

    private ArrayList<Calls> mCalls;
    private ListView mListView;

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

        mListView = (ListView) view.findViewById(R.id.listView);
        mListView.setAdapter(new CallAdapter(this, getActivity(), mCalls));

        return view;
    }

    public void onClickListView(int position)
    {
        if(!Util.isNetworkAvailable())
        {
            Crouton.makeText(getActivity(), R.string.error, Style.ALERT).show();
            return;
        }

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_search_contacts, null);

        mDialog = new AlertDialog.Builder( getActivity() ).create();
        mDialog.setView(view);
        mDialog.setInverseBackgroundForced(true);

        mLoadingLinear = (LinearLayout) view.findViewById(R.id.loading);
        mCarriesLinear = (LinearLayout) view.findViewById(R.id.carries);

        mCarrie = (CustomFontTextView) view.findViewById(R.id.carrie);
        mCarrie.setTypeface(getString(R.string.opensansregular));
        mPortabilty = (CustomFontTextView) view.findViewById(R.id.portabilty);
        mPortabilty.setTypeface(getString(R.string.opensansregular));
        mWhen = (CustomFontTextView) view.findViewById(R.id.when);
        mWhen.setTypeface(getString(R.string.opensansregular));
        wLabelCanvas = (LinearLayout) view.findViewById(R.id.labelCanvas);
        mCarrieImage = (ImageView) view.findViewById(R.id.carrieImage);
        mButtonLougout = (ButtonFlat) view.findViewById(R.id.logout);
        mButtonLougout.setOnClickListener(this);
        mButtonSave = (ButtonFlat) view.findViewById(R.id.save);
        mButtonSave.setOnClickListener(this);
        mNotFound = (LinearLayout) view.findViewById(R.id.notFound);
        mButtonLougoutNotFound = (ButtonFlat) view.findViewById(R.id.logoutNotFount);
        mButtonLougoutNotFound.setOnClickListener(this);
        mLabelCarrieOld = (LinearLayout) view.findViewById(R.id.labelCarrieOld);
        mCarrieOld = (CustomFontTextView) view.findViewById(R.id.carrieOld);
        mContact = (CustomFontTextView) view.findViewById(R.id.contact);
        mPhone = (CustomFontTextView) view.findViewById(R.id.phone);
        mLoadingText = (CustomFontTextView) view.findViewById(R.id.loadingText);
        mButtonCall = (ButtonFlat) view.findViewById(R.id.call);;
        mButtonCall.setOnClickListener(this);
        mSmsCall = (ButtonFlat) view.findViewById(R.id.sms);;
        mSmsCall.setOnClickListener(this);

        mDialog.show();
    }

    @Override
    public void onClick(View v)
    {

    }
}
