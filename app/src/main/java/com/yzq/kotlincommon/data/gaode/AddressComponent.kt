package com.yzq.kotlincommon.data.gaode


import com.google.gson.annotations.SerializedName

data class AddressComponent(
    var country: String = "",
    @SerializedName("country_code")
    var countryCode: Int = 0,
    @SerializedName("country_code_iso")
    var countryCodeIso: String = "",
    @SerializedName("country_code_iso2")
    var countryCodeIso2: String = "",
    var province: String = "",
    var city: String = "",
    @SerializedName("city_level")
    var cityLevel: Int = 0,
    var district: String = "",
    var town: String = "",
    var adcode: String = "",
    var street: String = "",
    @SerializedName("street_number")
    var streetNumber: String = "",
    var direction: String = "",
    var distance: String = ""
)