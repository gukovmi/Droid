package com.example.droid.loan.domain.entities

import android.os.Parcel
import android.os.Parcelable

data class LoanConditions(
    val percent: Double,
    val period: Int,
    val maxAmount: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(percent)
        parcel.writeInt(period)
        parcel.writeLong(maxAmount)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<LoanConditions> {
        override fun createFromParcel(parcel: Parcel): LoanConditions {
            return LoanConditions(parcel)
        }

        override fun newArray(size: Int): Array<LoanConditions?> {
            return arrayOfNulls(size)
        }
    }
}