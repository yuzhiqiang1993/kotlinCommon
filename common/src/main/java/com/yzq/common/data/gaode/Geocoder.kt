package com.yzq.common.data.gaode

data class Geocoder(
    val result: Result? = null,
    val message: String = "",
    val status: Int
) {
    data class Result(
        val addressComponent: AddressComponent,
        val business: String,
        val cityCode: Int,
        val formatted_address: String,
        val location: Location,
        val poiRegions: List<Any>,
        val pois: List<Any>,
        val roads: List<Any>,
        val sematic_description: String
    ) {
        data class AddressComponent(
            val adcode: String,
            val city: String,
            val city_level: Int,
            val country: String,
            val country_code: Int,
            val country_code_iso: String,
            val country_code_iso2: String,
            val direction: String,
            val distance: String,
            val district: String,
            val province: String,
            val street: String,
            val street_number: String,
            val town: String,
            val town_code: String
        )

        data class Location(
            val lat: Double,
            val lng: Double
        )
    }
}