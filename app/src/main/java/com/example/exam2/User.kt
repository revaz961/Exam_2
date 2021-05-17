package com.example.exam2

import android.os.Parcelable
import android.view.View
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(var firstName: String, var secondName: String, var email: String, var age: Int):
    Parcelable {
    val id = View.generateViewId()
}