package com.csobrino.pruebatecnica.data

import com.google.gson.annotations.SerializedName

class Planet {
    @SerializedName("apod_site")
    var apodSite = ""
    var copyright = ""
    var date = ""
    var description = ""
    var hdurl = ""
    var title = ""
    var url = ""
}