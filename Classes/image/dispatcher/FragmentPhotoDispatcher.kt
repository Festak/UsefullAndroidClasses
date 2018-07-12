package com.lykkex.LykkeWallet.gui.utils.image.dispatcher

import android.content.Intent

/**
 * @author Created by i.statkevich on 2.3.18.
 */
class FragmentPhotoDispatcher(private val fragment: android.app.Fragment) : BasePhotoDispatcher(fragment.context) {
    override fun startPhotoGalleryActivity(intent: Intent, requestCode: Int) {
        fragment.startActivityForResult(intent, requestCode)
    }

    override fun startTakeImageCaptureActivity(intent: Intent, requestCode: Int) {
        fragment.startActivityForResult(intent, requestCode)
    }
}