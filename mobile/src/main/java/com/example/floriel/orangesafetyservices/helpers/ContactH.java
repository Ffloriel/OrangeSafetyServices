package com.example.floriel.orangesafetyservices.helpers;

import com.example.floriel.orangesafetyservices.objects.AddressBookContact;
import java.util.List;

/**
 * Created by Floriel on 10/24/2016.
 */

public class ContactH {


    static List<AddressBookContact> getContactList() {
        List<AddressBookContact> list = new LinkedList<AddressBookContact>();
        LongSparseArray<AddressBookContact> array = new LongSparseArray<AddressBookContact>();
        long start = System.currentTimeMillis();

        String[] projection = {
                ContactsContract.Data.MIMETYPE,
                ContactsContract.Data.CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Contactables.DATA,
                ContactsContract.CommonDataKinds.Contactables.TYPE,
        };
        String selection = ContactsContract.Data.MIMETYPE + " in (?, ?)";
        String[] selectionArgs = {
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
        };
        String sortOrder = ContactsContract.Contacts.SORT_KEY_ALTERNATIVE;

        Uri uri = ContactsContract.CommonDataKinds.Contactables.CONTENT_URI;
// we could also use Uri uri = ContactsContract.Data.CONTENT_URI;

// ok, let's work...
        Cursor cursor = getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);

        final int mimeTypeIdx = cursor.getColumnIndex(ContactsContract.Data.MIMETYPE);
        final int idIdx = cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID);
        final int nameIdx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        final int dataIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.DATA);
        final int typeIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.TYPE);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(idIdx);
            AddressBookContact addressBookContact = array.get(id);
            if (addressBookContact == null) {
                addressBookContact = new AddressBookContact(id, cursor.getString(nameIdx), getResources());
                array.put(id, addressBookContact);
                list.add(addressBookContact);
            }
            int type = cursor.getInt(typeIdx);
            String data = cursor.getString(dataIdx);
            String mimeType = cursor.getString(mimeTypeIdx);
            if (mimeType.equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                // mimeType == ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
                addressBookContact.addEmail(type, data);
            } else {
                // mimeType == ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                addressBookContact.addPhone(type, data);
            }
        }
        long ms = System.currentTimeMillis() - start;
        cursor.close();

// done!!! show the results...
        int i = 1;
        for (AddressBookContact addressBookContact : list) {
            Log.d(TAG, "AddressBookContact #" + i++ + ": " + addressBookContact.toString(true));
        }
        final String cOn = "<b><font color='#ff9900'>";
        final String cOff = "</font></b>";
        Spanned l1 = Html.fromHtml("got " + cOn + array.size() + cOff + " contacts<br/>");
        Spanned l2 = Html.fromHtml("query took " + cOn + ms / 1000f + cOff + " s (" + cOn + ms + cOff + " ms)");

        Log.d(TAG, "\n\n╔══════ query execution stats ═══════" );
        Log.d(TAG, "║    " + l1);
        Log.d(TAG, "║    " + l2);
        Log.d(TAG, "╚════════════════════════════════════" );
        SpannableStringBuilder msg = new SpannableStringBuilder().append(l1).append(l2);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        TextView tv = new TextView(this);
        tv.setTextSize(20);
        tv.setBackgroundColor(0xff000033);
        tv.setPadding(24, 8, 24, 24);
        tv.setText(msg);
        ll.addView(tv);
        ListView lv = new ListView(this);
        lv.setAdapter(new ArrayAdapter<AddressBookContact>(this, android.R.layout.simple_list_item_1, list));
        ll.addView(lv);
        new AlertDialog.Builder(this).setView(ll).setPositiveButton("close", null).create().show();
    }


}
