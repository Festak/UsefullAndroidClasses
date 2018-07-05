package com.lykkex.LykkeWallet.wamp.models

import com.google.gson.annotations.SerializedName

/**
 * @author Created by i.statkevich on 19.2.18.
 */
class ConfirmCommand(@SerializedName("RequestId") var requestId: String? = null,
                     @SerializedName("RequestType") var requestType: String? = null,
                     @SerializedName("Context") var context: String? = null)
