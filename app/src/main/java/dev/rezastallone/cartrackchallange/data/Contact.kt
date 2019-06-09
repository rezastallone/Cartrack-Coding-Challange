package dev.rezastallone.cartrackchallange.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "contacts")
class Contact(
    @PrimaryKey
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    @Embedded
    val address: Address,
    val phone: String,
    val website: String,
    @Embedded(prefix = "company")
    val company: Company
) : Parcelable