package com.lykkex.LykkeWallet.wamp

import android.util.Log
import rx.Observable
import rx.Subscription
import ws.wamp.jawampa.PubSubData
import ws.wamp.jawampa.WampClient
import java.util.*

/**
 * @author Created by i.statkevich on 15.2.18.
 */
class WampManager(private val wampClient: WampClient) : WampSubscription.Manager {

    private val topics = HashMap<String, WampSubscription.TopicData>()
    private var beforeConnected = false

    init {
        initAndOpenWamp()
    }

    override fun subscribe(topic: String, listener: WampSubscription.DataListener): Boolean {
        var topicData = topics[topic]

        if (topicData == null) {
            topicData = createTopicAndMakeSubscription(topic)
                    ?: return false
            topics.put(topic, topicData)
        }

        return topicData.listeners.add(listener)
    }

    override fun unsubscribe(listener: WampSubscription.DataListener): Boolean {
        val (topic, topicData) = findTopicData(listener)
                ?: return false

        if (topicData.listeners.size <= 1) {
            // Topic has only one listener, deletes the topic with a listener
            unsubscribeWampTopic(topicData)
            topics.remove(topic)
        } else {
            topicData.listeners.remove(listener)
        }

        return true
    }

    override fun release() {
        topics.values.forEach { topicData ->
            topicData.subscription.unsubscribe()
            topicData.listeners.clear()
        }
        wampClient.close().subscribe({}, {})
    }

    private fun initAndOpenWamp() {
        wampClient.statusChanged()?.subscribe(
                { state ->
                    Log.d(WampSubscription.LOG_TAG, "WampClient state = " + state.toString())
                    if (state is WampClient.ConnectedState) {
                        handleConnectedState()
                    }
                },
                { throwable ->
                    Log.d(WampSubscription.LOG_TAG, throwable.toString())
                    throwable.printStackTrace()
                })
        wampClient.open()
    }

    private fun handleConnectedState() {
        if (beforeConnected || topics.size > 0) {
            resubscribe()
        }

        beforeConnected = true
    }

    private fun createTopicAndMakeSubscription(topic: String): WampSubscription.TopicData? {
        val listeners: MutableSet<WampSubscription.DataListener> = LinkedHashSet()
        val oneToManyListeners = OneToManyListeners(listeners)

        val subscription = subscribeWampTopic(topic, oneToManyListeners)
                ?: return null

        return WampSubscription.TopicData(subscription, listeners)
    }

    private fun findTopicData(listener: WampSubscription.DataListener)
            : Pair<String, WampSubscription.TopicData>? {

        topics.forEach { topicEntry ->
            if (topicEntry.value.listeners.firstOrNull { it == listener } != null) {
                return Pair(topicEntry.key, topicEntry.value)
            }
        }

        return null
    }

    private fun unsubscribeWampTopic(topicData: WampSubscription.TopicData) {
        topicData.subscription.unsubscribe()
    }

    private fun subscribeWampTopic(topic: String, oneToManyListeners: OneToManyListeners)
            : Subscription? {
        val observable: Observable<PubSubData>?
                = wampClient.makeSubscription(topic.toLowerCase())
                ?: return null

        return observable?.subscribe(
                { data -> oneToManyListeners.onSubscriptionReceived(data) },
                { t -> oneToManyListeners.onSubscriptionFailed(t) },
                { oneToManyListeners.onSubscriptionCompleted() })
                ?: return null
    }

    private fun resubscribe() {
        topics.forEach {
            val topicData = it.value
            unsubscribeWampTopic(topicData)
            subscribeWampTopic(it.key, OneToManyListeners(topicData.listeners))
                    ?.let { subscription ->
                        topicData.subscription = subscription
                    }
        }
    }
}