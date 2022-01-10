package com.kumparan.assessment.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * data class user untuk serialization dan local database.
 */
@Parcelize
data class User(
    var id: Int = 0,
    var address: Address = Address(),
    var company: Company = Company(),
    var email: String = "",
    var name: String = "",
    var phone: String = "",
    var username: String = "",
    var website: String = ""
): Parcelable {
    @Parcelize
    data class Address(
        var city: String = "",
        var geo: Geo = Geo(),
        var street: String = "",
        var suite: String = "",
        var zipcode: String = ""
    ): Parcelable {
        @Parcelize
        data class Geo(
            var lat: String = "",
            var lng: String = ""
        ): Parcelable
    }

    @Parcelize
    data class Company(
        var bs: String = "",
        var catchPhrase: String = "",
        var name: String = ""
    ): Parcelable
}