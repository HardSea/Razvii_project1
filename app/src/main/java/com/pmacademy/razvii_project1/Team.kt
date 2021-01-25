package com.pmacademy.razvii_project1

import android.os.Parcel
import android.os.Parcelable

class Team(val name: String?) : Parcelable {
    //For future possible expansion of the functionality, a parcelable class was added

    private constructor(parcel: Parcel?) : this(
        name = parcel?.readString()
    )

    companion object {
        @JvmStatic
        val CREATOR = object : Parcelable.Creator<Team> {
            override fun createFromParcel(source: Parcel?): Team = Team(source)
            override fun newArray(size: Int): Array<Team?> = arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
    }
}