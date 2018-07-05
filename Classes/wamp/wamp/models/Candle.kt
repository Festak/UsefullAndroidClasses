package com.lykkex.LykkeWallet.wamp.models

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by i.statkevich on 18.12.17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class Candle {
    @get:JsonProperty("m")
    @set:JsonProperty("m")
    var market: String? = null

    @get:JsonProperty("a")
    @set:JsonProperty("a")
    var instrument: String? = null

    @get:JsonProperty("p")
    @set:JsonProperty("p")
    var priceType: String? = null

    @get:JsonProperty("i")
    @set:JsonProperty("i")
    var interval: String? = null

    @get:JsonProperty("t")
    @set:JsonProperty("t")
    var timestamp: String? = null

    @get:JsonProperty("o")
    @set:JsonProperty("o")
    var openPrice: String? = null

    @get:JsonProperty("c")
    @set:JsonProperty("c")
    var closePrice: String? = null

    @get:JsonProperty("h")
    @set:JsonProperty("h")
    var highestPrice: String? = null

    @get:JsonProperty("l")
    @set:JsonProperty("l")
    var lowestPrice: String? = null

    @get:JsonProperty("v")
    @set:JsonProperty("v")
    var tradingVolume: String? = null

    override fun toString() : String {
        return "market: $market, instrument: $instrument, priceType: $priceType, " +
                "interval: $interval, timestamp: $timestamp, openPrice: $openPrice, " +
                "closePrice: $closePrice, highestPrice: $highestPrice, lowestPrice: $lowestPrice, " +
                "tradingVolume: $tradingVolume"
    }
}