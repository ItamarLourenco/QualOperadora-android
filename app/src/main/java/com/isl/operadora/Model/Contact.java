package com.isl.operadora.Model;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;

import com.isl.operadora.Application.AppController;
import com.isl.operadora.R;

import java.util.ArrayList;

/**
 * Created by webx on 08/04/15.
 */
public class Contact {
    private String name;
    private String number;
    private String label;
    private String carrier;
    private int id;
    private int type;

    public static ArrayList<Contact> contacts;

    public Contact(int id, String name, String number, String label, int type){
        this.id = id;
        this.name = name;
        this.number = number;
        this.label = label;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }

    public int getType(){
        return type;
    }

    public void setType(int type){
        this.type = type;
    }

    public String getCarrier(){
        if(carrier != null)
        {
            return carrier.trim();
        }
        return null;
    }

    public void setCarrier(String carrier){
        this.carrier = carrier;
    }

    public static ArrayList<Contact> getContacts(Activity act){
        contacts = new ArrayList<Contact>();
        Cursor phones = act.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if(phones != null){
            while (phones.moveToNext())
            {
                int id = phones.getInt(phones.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID));
                String name = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String label = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LABEL));
                int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                contacts.add(new Contact(id, name, phoneNumber, label, type));
            }
            phones.close();
        }
        return contacts;
    }

    public static boolean eddLabelNumber(int rawContactId, String contactNumber, String newLabel)
    {
        if (rawContactId != 0 && contactNumber != null)
        {
            Contact.delContact(rawContactId, contactNumber);
            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contactNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM)
                    .withValue(ContactsContract.CommonDataKinds.Phone.LABEL, newLabel)
                .build());
            try
            {
                AppController.getInstance().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                return true;
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }
            catch (OperationApplicationException e)
            {
                e.printStackTrace();
            }
        }

        return false;
    }

    private static boolean delContact(int rawContactId, String contactNumber)
    {
        if (rawContactId != 0 && contactNumber != null)
        {
            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
            ops.add(ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI)
                    .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? AND " + ContactsContract.CommonDataKinds.Phone.NUMBER + "=?",
                            new String[]{String.valueOf(rawContactId), contactNumber}).build());
            try
            {
                AppController.getInstance().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                return true;
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }
            catch (OperationApplicationException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String getContactDisplayNameByNumber(String number)
    {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        String name = AppController.getInstance().getString(R.string.unknow);

        ContentResolver contentResolver = AppController.getInstance().getContentResolver();
        Cursor contactLookup = contentResolver.query(uri, new String[] {ContactsContract.PhoneLookup._ID,
                ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);
        try {
            if (contactLookup != null && contactLookup.getCount() > 0)
            {
                contactLookup.moveToNext();
                name = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            }
        } finally {
            if (contactLookup != null)
            {
                contactLookup.close();
            }
        }

        return name;
    }
}
