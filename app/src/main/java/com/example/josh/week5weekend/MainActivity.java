package com.example.josh.week5weekend;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.josh.week5weekend.models.Contact;
import com.example.josh.week5weekend.models.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemClicked {
    public static final String TAG = "_TAG";
    List<Contact> contactList = new ArrayList<>();
    String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};
    private RecyclerView rvContacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvContacts = findViewById(R.id.rvContacts);
        ActivityCompat.requestPermissions(this, permissions, 10);
        getContactList();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(contactList);
        setUpRecyclerView(adapter);


    }
    public void setUpRecyclerView(RecyclerViewAdapter recyclerViewAdapter){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvContacts = findViewById(R.id.rvContacts);
        rvContacts.setAdapter(recyclerViewAdapter);
        rvContacts.setLayoutManager(layoutManager);
        recyclerViewAdapter.setOnClick(MainActivity.this);
    }

    private void getContactList() {

        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        while (cursor != null && cursor.moveToNext()) {
            Contact tempContact = new Contact();
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            tempContact.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

            //Used to retrieve the Phone number
            Cursor phones = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                    null, null);
            phones.moveToFirst();
            tempContact.setNumber(phones.getString(phones.getColumnIndex("data1")));
            //Used to retrieve the address
            Uri postal_uri = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
            Cursor postal_cursor  = getContentResolver().query(postal_uri,null,  ContactsContract.Data.CONTACT_ID + "=" + id, null,null);

            postal_cursor.moveToFirst();
            tempContact.setAddress(postal_cursor.getString(postal_cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS)));
            contactList.add(tempContact);

        }
    }


    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: ");
        Intent searchAddress = new  Intent(Intent.ACTION_VIEW,Uri.parse("geo:0,0?q="+contactList.get(position).getAddress()));
        startActivity(searchAddress);
    }
}
