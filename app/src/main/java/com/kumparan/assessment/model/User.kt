package com.kumparan.assessment.model

/**
 * data class user untuk serialization.
 */
data class User(
    var address: Address = Address(),
    var company: Company = Company(),
    var email: String = "",
    var id: Int = 0,
    var name: String = "",
    var phone: String = "",
    var username: String = "",
    var website: String = ""
) {
    data class Address(
        var city: String = "",
        var geo: Geo = Geo(),
        var street: String = "",
        var suite: String = "",
        var zipcode: String = ""
    ) {
        data class Geo(
            var lat: String = "",
            var lng: String = ""
        )
    }

    data class Company(
        var bs: String = "",
        var catchPhrase: String = "",
        var name: String = ""
    )
}