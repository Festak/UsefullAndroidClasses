package com.lykkex.LykkeWallet.gui.utils.animation

import android.view.View


/**
 * @author e.fetskovich on 9/19/18.
 */
object AnimationHelper : BaseAnimations() {


    fun fromLeftToRightEnters(callback: AnimationFinishedCallback?, animateArea: Float, duration: Long, vararg viewsToAnimate: View) {
        for (view in viewsToAnimate) {
            animateWithTranslateFade(callback, -animateArea, CURRENT_POSITION, NOT_FADED, FADED, duration, view)
        }
    }

    fun fromRightToLeftGone(callback: AnimationFinishedCallback?, animateArea: Float, duration: Long, vararg viewsToAnimate: View) {
        for (view in viewsToAnimate) {
            animateWithTranslateFade(callback, CURRENT_POSITION, -animateArea, FADED, NOT_FADED, duration, view)
        }
    }

    fun fromRightToLeftEnters(callback: AnimationFinishedCallback?, animateArea: Float, duration: Long, vararg viewsToAnimate: View) {
        for (view in viewsToAnimate) {
            animateWithTranslateFade(callback, animateArea, CURRENT_POSITION, NOT_FADED, FADED, duration, view)
        }
    }

    fun fromLeftToRightGone(callback: AnimationFinishedCallback?, animateArea: Float, duration: Long, vararg viewsToAnimate: View) {
        for (view in viewsToAnimate) {
            animateWithTranslateFade(callback, CURRENT_POSITION, animateArea, FADED, NOT_FADED, duration, view)
        }
    }



}