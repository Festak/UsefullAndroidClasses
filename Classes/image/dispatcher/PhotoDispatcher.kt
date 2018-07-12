package com.lykkex.LykkeWallet.gui.utils.image.dispatcher

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

/**
 * @author Created by i.statkevich on 26.1.18.
 */

interface PhotoDispatcher {
    companion object {
        val REQUEST_IMAGE_CAPTURE = 101
        val REQUEST_GALLERY_ACTION = 102
    }

    fun getPhotoFromGallery()

    fun takeImageCapture(temporaryDir: File, photoName: String, format: Bitmap.CompressFormat): Uri?
}
