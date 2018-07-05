package com.lykkex.LykkeWallet.wamp

import ws.wamp.jawampa.PubSubData

/**
 * @author Created by i.statkevich on 16.2.18.
 */
class OneToManyListeners(private var listeners: MutableSet<WampSubscription.DataListener>)
    : WampSubscription.DataListener {

    override fun onSubscriptionReceived(data: PubSubData) {
        listeners.forEach {
            it.onSubscriptionReceived(data)
        }
    }

    override fun onSubscriptionFailed(t: Throwable) {
        listeners.forEach {
            it.onSubscriptionFailed(t)
        }
    }

    override fun onSubscriptionCompleted() {
        listeners.forEach {
            it.onSubscriptionCompleted()
        }
    }
}