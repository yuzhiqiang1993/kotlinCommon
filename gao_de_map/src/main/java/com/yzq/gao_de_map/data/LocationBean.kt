package com.yzq.gao_de_map.data

import android.os.Parcel
import android.os.Parcelable


/**
 * @description: 定位信息实体类
 * @author : yzq
 * @date   : 2018/11/13
 * @time   : 13:48
 *
 */

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class LocationBean(
    var address: String = "", // 上海市闵行区梅强路228号靠近春申创意园A座
    var city: String = "", // 上海市
    var cityCode: String = "", // 021
    var country: String = "", // 中国
    var district: String = "", // 闵行区
    var districtCode: String = "", // 310112
    var latitude: Double = 0.0, // 31.108519
    var longitude: Double = 0.0, // 121.418365
    var province: String = "" // 上海市
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readDouble(),
        source.readDouble(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(address)
        writeString(city)
        writeString(cityCode)
        writeString(country)
        writeString(district)
        writeString(districtCode)
        writeDouble(latitude)
        writeDouble(longitude)
        writeString(province)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<LocationBean> = object : Parcelable.Creator<LocationBean> {
            override fun createFromParcel(source: Parcel): LocationBean = LocationBean(source)
            override fun newArray(size: Int): Array<LocationBean?> = arrayOfNulls(size)
        }
    }
}