package com.yzq.common.data.gaode


import com.squareup.moshi.Json

data class Geocoder(
    var location: Location = Location(),
    @Json(name = "formatted_address")
    var formattedAddress: String = "",
    var business: String = "",
    var addressComponent: AddressComponent = AddressComponent(),
    var pois: List<Any> = listOf(),
    var roads: List<Any> = listOf(),
    var poiRegions: List<PoiRegion> = listOf(),
    @Json(name = "sematic_description")
    var sematicDescription: String = "",
    var cityCode: Int = 0
)