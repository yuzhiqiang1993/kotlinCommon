package com.yzq.common.data.gaode


import com.squareup.moshi.Json

data class AddressComponent(
    var country: String = "",
    @Json(name = "country_code")
    var countryCode: Int = 0,
    @Json(name = "country_code_iso")
    var countryCodeIso: String = "",
    @Json(name = "country_code_iso2")
    var countryCodeIso2: String = "",
    var province: String = "",
    var city: String = "",
    @Json(name = "city_level")
    var cityLevel: Int = 0,
    var district: String = "",
    var town: String = "",
    var adcode: String = "",
    var street: String = "",
    @Json(name = "street_number")
    var streetNumber: String = "",
    var direction: String = "",
    var distance: String = ""
)