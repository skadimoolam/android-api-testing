package dev.adi.testapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import dev.adi.testapp.R;

public class ContactListAdapter extends CursorAdapter {

    public ContactListAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
    }

    @Override
    public void bindView(View v, Context context, Cursor cursor) {
        TextView tvName = (TextView) v.findViewById(R.id.tv_contact_name);
        tvName.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

        TextView tvPhone = (TextView) v.findViewById(R.id.tv_contact_phone);
        tvPhone.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)));
//        tvPhone.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
    }
}
