package com.lykkex.LykkeWallet.wamp.models

import com.google.gson.annotations.SerializedName

/**
 * @author Created by i.statkevich on 18.12.17.
 */
data class Quote(@SerializedName("m") val market: String?,
                 @SerializedName("a") val instrument: String?,
                 @SerializedName("pt") val priceType: String?,
                 @SerializedName("t") val timestamp: String?,
                 @SerializedName("p") val price: String?)
