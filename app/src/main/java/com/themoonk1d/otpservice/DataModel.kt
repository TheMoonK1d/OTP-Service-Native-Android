package com.themoonk1d.otpservice

import kotlinx.serialization.Serializable

@Serializable
data class DataModel(val otp: String, val to: String){
    init {
        val otp = this.otp
        val to = this.to
    }
}