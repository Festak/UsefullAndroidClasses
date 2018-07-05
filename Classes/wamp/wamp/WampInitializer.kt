package com.lykkex.LykkeWallet.wamp

import android.util.Log
import rx.functions.Action1
import ws.wamp.jawampa.WampClient
import ws.wamp.jawampa.WampClientBuilder
import ws.wamp.jawampa.auth.client.Ticket
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider
import ws.wamp.jawampa.transport.netty.NettyWampConnectionConfig
import java.util.concurrent.TimeUnit

/**
 * @author Created by i.statkevich on 18.12.17.
 */

class WampInitializer {
    companion object {
        val WAMP_HOST_PROD = "wss://wamp.lykke.com/ws/"
        val WAMP_HOST_DEV = "wss://wamp-dev.lykkex.net/ws/"
        val WAMP_HOST_TEST = "wss://wamp-test.lykkex.net/ws/"
        val WAMP_REALM_DEFAULT = "prices"

        val WAMP_USER_ID = "random"

        val TAG = WampInitializer::class.java.simpleName
        val MIN_RECONNECT_INTERVAL_SECONDS = 30
    }

    private var host: String? = null
    private var realm: String? = null
    private var wampClient: WampClient? = null
    private var authId: String? = null
    private var ticket: String? = null

    fun setHost(host: String): WampInitializer {
        this.host = host
        return this
    }

    fun setRealm(realm: String): WampInitializer {
        this.realm = realm
        return this
    }

    fun setAuthTicketParams(authId: String?, ticket: String?): WampInitializer {
        this.authId = authId
        this.ticket = ticket
        return this
    }

    fun create(): WampInitializer {
        if (host == null || realm == null) {
            throw IllegalStateException("host or realm is empty")
        }

        wampClient = create(host!!, realm!!)
        return this
    }

    fun init(): WampInitializer {
        return init(
                Action1 { state ->
                    Log.d(TAG, "WampClient state = " + state.toString())
                },
                Action1 { throwable ->
                    Log.d(TAG, throwable.toString())
                    throwable.printStackTrace()
                })
    }

    fun init(onStatusChanged: Action1<Any>?, onError: Action1<Throwable>?): WampInitializer {
        wampClient?.let {
            wampClient?.statusChanged()?.subscribe(onStatusChanged, onError)
            wampClient?.open()

            return this
        } ?: throw IllegalStateException("WampClient not created")
    }

    fun getWampClient(): WampClient? {
        return wampClient
    }

    @Throws(Exception::class)
    private fun create(wampHost: String, wampRealm: String): WampClient? {
        Log.d(TAG, "Wamp host: " + wampHost)
        Log.d(TAG, "Wamp realm: " + wampRealm)

        val builder = WampClientBuilder()
        builder.withUri(wampHost)
                .withRealm(wampRealm)
                .withInfiniteReconnects()
                .withCloseOnErrors(false)
                .withConnectorProvider(NettyWampClientConnectorProvider())
                .withConnectionConfiguration(NettyWampConnectionConfig
                        .Builder().withMaxFramePayloadLength(Integer.MAX_VALUE).build())
                .withReconnectInterval(MIN_RECONNECT_INTERVAL_SECONDS, TimeUnit.SECONDS)
        if (ticket != null && authId != null) {
            builder.withAuthId(authId)
            builder.withAuthMethod(Ticket(ticket))
        }

        return builder.build()
    }
}