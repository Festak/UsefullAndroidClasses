package com.lykkex.LykkeWallet.gui.utils.animation.home

import android.view.View
import com.lykkex.LykkeWallet.gui.utils.UiUtils
import com.lykkex.LykkeWallet.gui.utils.animation.AnimationFinishedCallback
import com.lykkex.LykkeWallet.gui.utils.animation.AnimationHelper
import org.androidannotations.annotations.EBean

/**
 * @author e.fetskovich on 9/27/18.
 */

@EBean(scope = EBean.Scope.Singleton)
open class HomeDataLoaderManager {

    companion object {
        const val FADE_IN_LAYOUT_ANIM_MILLS = 200L
        const val FADE_OUT_LAYOUT_ANIM_MILLS = 400L

        fun fadeInOutViewsAnimation(viewToHide: View, viewToShow: View) {
            if(UiUtils.isViewVisibleAtScreen(viewToHide)) {
                viewToHide.setVisibility(View.INVISIBLE)
                AnimationHelper.animateViewFade(object : AnimationFinishedCallback {
                    override fun onAnimationFinished(view: View) {
                        viewToShow.setVisibility(View.VISIBLE)
                        AnimationHelper.animateViewFade(null,
                                AnimationHelper.NOT_FADED,
                                AnimationHelper.FADED,
                                viewToShow,
                                FADE_IN_LAYOUT_ANIM_MILLS)
                    }
                }, AnimationHelper.FADED, AnimationHelper.NOT_FADED, viewToHide, FADE_OUT_LAYOUT_ANIM_MILLS)
            } else {
                viewToHide.setVisibility(View.INVISIBLE)
                viewToShow.setVisibility(View.VISIBLE)
            }
        }

    }

    var balanceSectionLoaded = false
    var quotesSectionLoaded = false
    var lykkeSectionLoaded = false

    fun clearAllSections() {
        balanceSectionLoaded = false
        quotesSectionLoaded = false
        lykkeSectionLoaded = false
    }

}