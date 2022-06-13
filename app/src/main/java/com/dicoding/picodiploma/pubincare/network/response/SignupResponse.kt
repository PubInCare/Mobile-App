package com.dicoding.picodiploma.pubincare.network.response

import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,
)