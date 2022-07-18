package com.example.fampayproject.models


import com.google.gson.annotations.SerializedName


/**
 * This data class is used to parse out the `card_groups` object returned from
 * the Web API response which contains a List of [CardGroup]
 */
data class CardGroupObject (
    @SerializedName("card_groups")
    val cardGroup: List<CardGroup>
)
