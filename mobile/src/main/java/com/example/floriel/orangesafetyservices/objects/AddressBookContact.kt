package com.example.floriel.orangesafetyservices.objects

import android.content.res.Resources
import android.util.LongSparseArray

data class AddressBookContact(val id: Long, val name: String, val res: Resources) {
    val phones: LongSparseArray<String>? = null
}
