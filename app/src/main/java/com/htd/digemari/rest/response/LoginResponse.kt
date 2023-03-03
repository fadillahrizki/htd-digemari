package com.htd.digemari.rest.response

import com.google.gson.annotations.SerializedName
import com.htd.digemari.model.LoginModel

class LoginResponse {
    @SerializedName("opd")
    lateinit var user: LoginModel

    @SerializedName("error")
    lateinit var error: String
}