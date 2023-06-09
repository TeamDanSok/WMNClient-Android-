package com.example.wmn.api
import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("no")
    val id: Int
)

data class PostParams(
    @SerializedName("dialog")
    val dialog: String,
    @SerializedName("userNo")
    val id: Int
)

data class ShoppingItem(
    @SerializedName("foodName")
    val foodName: String,
    @SerializedName("reason")
    val reason: String
)


