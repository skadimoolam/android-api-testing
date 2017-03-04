package dev.adi.testapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.net.URI;

import dev.adi.testapp.adapter.ContactListAdapter;

public class ContactsActivity extends AppCompatActivity {

    private ListView lvContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        lvContacts = (ListView) findViewById(R.id.lv_contacts);
        getContacts();
    }

    private void getContacts() {
        String[] CONTACT_PROJECTION = new String[] { ContactsContract.Contacts._ID, ContactsContract.Contacts.LOOKUP_KEY, ContactsContract.Contacts.DISPLAY_NAME };
//        String[] CONTACT_PROJECTION = new String[] { ContactsContract.Contacts._ID, ContactsContract.Contacts.LOOKUP_KEY, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER };
        Uri CONTACT_URI = ContactsContract.Contacts.CONTENT_URI;

        final Cursor cursor = getContentResolver().query(CONTACT_URI, CONTACT_PROJECTION, null, null, ContactsContract.Contacts.DISPLAY_NAME);
        ContactListAdapter adapter = new ContactListAdapter(this, cursor);
        lvContacts.setAdapter(adapter);
        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(ContactsActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();

                cursor.move(position);
                String selectedContactLookup = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                long selectedContactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Uri selectedContactUri = ContactsContract.Contacts.getLookupUri(selectedContactId, selectedContactLookup);

                Intent i = new Intent(Intent.ACTION_EDIT);
                i.setDataAndType(selectedContactUri, ContactsContract.Contacts.CONTENT_ITEM_TYPE);
                i.putExtra("finishActivityOnSaveCompleted", true);
                startActivity(i);
            }
        });
    }

}
