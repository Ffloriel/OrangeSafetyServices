package com.example.floriel.orangesafetyservices.helpers

import android.provider.ContactsContract

val PROJECTION: Array<String> = arrayOf(
        ContactsContract.CommonDataKinds.Contactables.MIMETYPE,
        ContactsContract.CommonDataKinds.Contactables.CONTACT_ID,
        ContactsContract.CommonDataKinds.Contactables.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Contactables.PHOTO_THUMBNAIL_URI,
        ContactsContract.CommonDataKinds.Contactables.DATA,
        ContactsContract.CommonDataKinds.Contactables.TYPE)

