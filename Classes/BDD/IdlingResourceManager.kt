package com.lykkex.LykkeWallet.gui.utils

import android.support.test.espresso.IdlingResource

/**
 * @author e.fetskovich on 4/13/18.
 */

object IdlingResourceManager
{
    private val countingIdlingResource = CustomCountingIdlingResource("GLOBAL")

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        countingIdlingResource.decrement()
    }

    fun getCountingIdlingResource(): IdlingResource {
        return countingIdlingResource
    }
}
