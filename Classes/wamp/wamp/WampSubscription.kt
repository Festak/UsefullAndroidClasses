package com.lykkex.LykkeWallet.wamp

import rx.Subscription
import ws.wamp.jawampa.PubSubData

/**
 * @author Created by i.statkevich on 15.2.18.
 */
interface WampSubscription {
    companion object {
        val LOG_TAG = "WampSubscription"
    }

    class TopicData(
            var subscription: Subscription,
            var listeners: MutableSet<WampSubscription.DataListener> = LinkedHashSet())

    interface DataListener {
        fun onSubscriptionReceived(data: PubSubData)
        fun onSubscriptionFailed(t: Throwable)
        fun onSubscriptionCompleted()
    }

    interface Manager {
        fun subscribe(topic: String, listener: DataListener): Boolean

        fun unsubscribe(listener: DataListener): Boolean

        fun release()
    }
}