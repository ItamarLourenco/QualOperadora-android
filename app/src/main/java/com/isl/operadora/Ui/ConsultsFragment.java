package com.isl.operadora.Ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.andexert.library.RippleView;
import com.gc.materialdesign.views.ButtonFlat;
import com.isl.operadora.Application.AppController;
import com.isl.operadora.Base.BaseFragment;
import com.isl.operadora.Model.Calls;
import com.isl.operadora.Model.Portabily;
import com.isl.operadora.R;
import com.isl.operadora.Request.ContactRequest;
import com.isl.operadora.Util.Mask;
import com.isl.operadora.Util.Util;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ConsultsFragment extends BaseFragment implements View.OnClickListener{
    private int TIME_VIBRATE = 10;

    private EditText mTextPhone;
    private ImageView mBackSpace;
    private Vibrator mVibrator;
    private AlertDialog mDialog;
    private String carrier;


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

        mVibrator = (Vibrator) AppController.getInstance().getSystemService(Context.VIBRATOR_SERVICE);

        mTextPhone = (EditText) view.findViewById(R.id.textPhone);
        mBackSpace = (ImageView) view.findViewById(R.id.backspace);
        mTextPhone.addTextChangedListener(Mask.insert("(##)#####-####", mTextPhone));
        mBackSpace.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mTextPhone.setText("");
                return false;
            }
        });
        mBackSpace.setOnClickListener(this);


        view.findViewById(R.id.one).setOnClickListener(this);
        view.findViewById(R.id.two).setOnClickListener(this);
        view.findViewById(R.id.tree).setOnClickListener(this);
        view.findViewById(R.id.four).setOnClickListener(this);
        view.findViewById(R.id.five).setOnClickListener(this);
        view.findViewById(R.id.six).setOnClickListener(this);
        view.findViewById(R.id.seven).setOnClickListener(this);
        view.findViewById(R.id.eight).setOnClickListener(this);
        view.findViewById(R.id.nine).setOnClickListener(this);
        view.findViewById(R.id.zero).setOnClickListener(this);
        view.findViewById(R.id.call).setOnClickListener(this);
        view.findViewById(R.id.search).setOnClickListener(this);

        return view;
    }

    private void onBackSpace()
    {
        if(!TextUtils.isEmpty(mTextPhone.getText()))
        {
            StringBuffer stringBuffer = new StringBuffer(mTextPhone.getText());
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            mTextPhone.setText(stringBuffer.toString().trim());
            mTextPhone.setSelection(mTextPhone.getText().length());
        }
    }

    private void showDialog()
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
        DialogCustom.loadViews(view, this);
        mDialog.show();
        searchPhone();
    }

    private void searchPhone()
    {
        final String number = Util.clearNumber(mTextPhone.getText().toString());

        new ContactRequest(new String[] { number }){
            @Override
            public void onFinish(Portabily.PushPortabily portabily)
            {
                Calls call = new Calls(number, "", 0, "", "");
                DialogCustom.populateViewCalls(portabily, call, call);
                if(DialogCustom.mLabelContact != null && DialogCustom.mLabelPhone != null)
                {
                    DialogCustom.mLabelContact.setVisibility(View.GONE);
                    DialogCustom.mLabelPhone.setVisibility(View.GONE);
                }
            }
        };
    }


    @Override
    public void onClick(View view)
    {
        if(view instanceof Button)
        {
            Button button = (Button) view;
            mTextPhone.append(button.getText());
            mVibrator.vibrate(TIME_VIBRATE);
        }
        else if(view instanceof RippleView)
        {
            switch (view.getId())
            {
                case R.id.call:
                    startActivity(
                            new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Util.clearNumber(mTextPhone.getText().toString())))
                    );
                    mVibrator.vibrate(TIME_VIBRATE);
                    break;

                case R.id.search:
                    showDialog();
                    mVibrator.vibrate(TIME_VIBRATE);
                    break;
            }
        }
        else if(view instanceof ImageView)
        {
            switch (view.getId())
            {
                case R.id.backspace:
                    onBackSpace();
                    mVibrator.vibrate(TIME_VIBRATE);
                    break;
            }
        }
        else if(view instanceof ButtonFlat)
        {
            switch (view.getId())
            {
                case R.id.logout:
                    if(mDialog.isShowing())
                        mDialog.dismiss();
                    break;

                case R.id.save:
                    addContact();
                    break;

                case R.id.call:
                    startActivity(
                            new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Util.clearNumber(mTextPhone.getText().toString()) ))
                    );
                    break;

                case R.id.sms:
                    startActivity(
                            new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + Util.clearNumber(mTextPhone.getText().toString()) ))
                    );
                    break;

                case R.id.logoutNotFount:
                    if(mDialog.isShowing())
                        mDialog.dismiss();
                    break;
            }
        }
    }

    private void addContact()
    {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.PHONE, Util.clearNumber(mTextPhone.getText().toString()));

        if(carrier == null){
            carrier = "";
        }
        intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, carrier);

        getActivity().startActivity(intent);
    }
}
