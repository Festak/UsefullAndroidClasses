package com.lykkex.LykkeWallet.gui.utils.image.dispatcher

import android.app.Activity
import android.content.Intent

/**
 * @author Created by i.statkevich on 2.3.18.
 */
class ActivityPhotoDispatcher(private val activity: Activity) : BasePhotoDispatcher(activity) {
    override fun startPhotoGalleryActivity(intent: Intent, requestCode: Int) {
        activity.startActivityForResult(intent, requestCode)
    }

    override fun startTakeImageCaptureActivity(intent: Intent, requestCode: Int) {
        activity.startActivityForResult(intent, requestCode)
    }
}