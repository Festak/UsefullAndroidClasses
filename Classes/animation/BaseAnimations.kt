package com.lykkex.LykkeWallet.gui.utils.animation

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation

/**
 * @author e.fetskovich on 9/27/18.
 */
open class BaseAnimations {
    val CURRENT_POSITION = 0f
    val NOT_FADED = 0f
    val FADED = 1f

    fun animateViewFade(callback: AnimationFinishedCallback?, fromFade: Float, toFade: Float, view: View, duration: Long) {
        val anim = initFadeAnimation(fromFade, toFade, duration)
        setAnimationListener(view, callback, anim)
        view.startAnimation(anim)
    }

    fun animateWithTranslateFade(callback: AnimationFinishedCallback?,
                                           fromX: Float, toX: Float, fromFade: Float, toFade: Float, duration: Long, view: View) {
        val animate = initTranslateFadeAnimation(fromX, toX, fromFade, toFade, duration)
        setAnimationListener(view, callback, animate)
        view.startAnimation(animate)
    }

     fun initFadeAnimation(fromFade: Float, toFade: Float, duration: Long): AlphaAnimation {
        val anim = AlphaAnimation(fromFade, toFade)
        anim.duration = duration
        return anim
    }

     fun initTranslateFadeAnimation(fromX: Float, toX: Float, fromFade: Float, toFade: Float, duration: Long): AnimationSet {
        val set = AnimationSet(true)
        val animate = TranslateAnimation(
                fromX,
                toX,
                AnimationHelper.CURRENT_POSITION,
                AnimationHelper.CURRENT_POSITION)
        animate.duration = duration
        set.addAnimation(animate)
        val anim = initFadeAnimation(fromFade, toFade, duration)
        set.addAnimation(anim)
        return set
    }


    fun setAnimationListener(view: View, callback: AnimationFinishedCallback?, animation: Animation) {
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
                // do nothing
            }

            override fun onAnimationEnd(animation: Animation?) {
                callback?.onAnimationFinished(view)
            }

            override fun onAnimationStart(animation: Animation?) {
                // do nothing
            }
        })
    }

}