package dev.rezastallone.cartrackchallange.data

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address (
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    @Embedded
    val geo: Geo
) : Parcelable