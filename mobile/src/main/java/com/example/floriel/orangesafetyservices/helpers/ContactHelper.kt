package com.example.floriel.orangesafetyservices.helpers

import android.provider.ContactsContract

val PROJECTION: Array<String> = arrayOf(
        ContactsContract.CommonDataKinds.Contactables.MIMETYPE,
        ContactsContract.CommonDataKinds.Contactables.CONTACT_ID,
        ContactsContract.CommonDataKinds.Contactables.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Contactables.PHOTO_THUMBNAIL_URI,
        ContactsContract.CommonDataKinds.Contactables.DATA,
        ContactsContract.CommonDataKinds.Contactables.TYPE)

val SELECTION: String = ContactsContract.CommonDataKinds.Contactables.MIMETYPE + " in (?, ?)"
val SELECTION_ARGS: Array<String> = arrayOf(
        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
)
