package com.example.floriel.orangesafetyservices.data

import java.util.*

data class Contact(val name: String, val phoneNumber: String, val id: String = UUID.randomUUID().toString())