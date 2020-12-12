package com.example.droid.loan.domain.entities

import android.os.Parcel
import android.os.Parcelable


data class Loan(
    val amount: Long,
    var date: String,
    val firstName: String,
    val id: Long,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val state: State
) : Parcelable {
    override fun writeToParcel(parcel: Parcel?, p1: Int) {
        parcel?.writeLong(amount)
        parcel?.writeString(date)
        parcel?.writeString(firstName)
        parcel?.writeLong(id)
        parcel?.writeString(lastName)
        parcel?.writeDouble(percent)
        parcel?.writeInt(period)
        parcel?.writeString(phoneNumber)
        parcel?.writeString(state.name)
    }

    override fun describeContents(): Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString().toString(),
        State.valueOf(parcel.readString().toString())
    )

    companion object CREATOR : Parcelable.Creator<Loan> {
        override fun createFromParcel(parcel: Parcel): Loan {
            return Loan(parcel)
        }

        override fun newArray(size: Int): Array<Loan?> {
            return arrayOfNulls(size)
        }
    }
}
