package com.csobrino.pruebatecnica.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Product : Serializable {
    @SerializedName("apod_site")
    var apodSite = ""
    var copyright = ""
    var date = ""
    var description = ""
    var hdurl = ""
    var title = ""
    var url = ""

    fun getDateAndCopyRight(): String {
        return "[$date] $copyright"
    }

    //local param
    var isExpanded = false
}