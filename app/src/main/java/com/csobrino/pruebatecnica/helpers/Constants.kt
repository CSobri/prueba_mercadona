package com.csobrino.pruebatecnica.helpers

object Constants {
    //DATE PATTERNS
    const val SHORT_DATE_PATTERN = "dd MMM"
    const val MEDIUM_DATE_PATTERN = "dd/MM/yyyy"
    const val SERVER_DATE_PATTERN = "yyyy-MM-dd"
    const val SERVER_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"
    const val SERVER_USER_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ"
    const val IMAGE_DATETIME_PATTERN = "yyyyMMdd_HHmmss"

    const val HOUR_PATTERN = "HH:mm"
    const val LONG_HOUR_PATTERN = "HH:mm:ss"
    const val LONG_DATETIME_PATTERN = "dd/MM/yyyy • hh:mm aa"

    //WEBSERVICES CODES
    const val SERVER_SUCCESS_CODE = 200
    const val SERVER_CREATED_CODE = 201
    const val SERVER_NOCONTENT_CODE = 204
    const val SERVER_BADREQUEST_CODE = 400
    const val SERVER_UNAUTHORIZED_CODE = 401
    const val SERVER_FORBIDDEN_CODE = 403
    const val SERVER_NOTFOUND_CODE = 404
    const val SERVER_TIMEOUT_CODE = 408
    const val SERVER_INTERNALSERVER_CODE = 500
    const val SERVER_BANNED_CODE = 403
}