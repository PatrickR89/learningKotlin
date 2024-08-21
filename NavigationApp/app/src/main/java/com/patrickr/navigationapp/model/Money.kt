package com.patrickr.navigationapp.model

import android.os.Parcel
import android.os.Parcelable
import java.math.BigDecimal

data class Money(val amount: BigDecimal): Parcelable {
	constructor(parcel: Parcel): this(BigDecimal(0)) {

	}
	override fun describeContents(): Int {
		return 0
	}

	override fun writeToParcel(p0: Parcel, p1: Int) {

	}

	companion object CREATOR: Parcelable.Creator<Money> {
		override fun createFromParcel(p0: Parcel): Money {
			return Money(parcel = p0)
		}

		override fun newArray(p0: Int): Array<Money?> {
    return arrayOfNulls(p0)
		}
	}
}
