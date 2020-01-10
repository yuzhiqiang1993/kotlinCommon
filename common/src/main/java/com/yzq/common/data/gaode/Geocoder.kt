package com.yzq.common.data.gaode


import com.google.gson.annotations.SerializedName

data class Geocoder(
    var location: Location = Location(),
    @SerializedName("formatted_address")
    var formattedAddress: String = "",
    var business: String = "",
    var addressComponent: AddressComponent = AddressComponent(),
    var pois: List<Any> = listOf(),
    var roads: List<Any> = listOf(),
    var poiRegions: List<PoiRegion> = listOf(),
    @SerializedName("sematic_description")
    var sematicDescription: String = "",
    var cityCode: Int = 0
)