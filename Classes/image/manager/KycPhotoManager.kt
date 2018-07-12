package com.lykkex.LykkeWallet.gui.activity.kyc.document.image.manager

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

/**
 * @author Created by i.statkevich on 25.1.18.
 */
interface KycPhotoManager {
    interface ActionListener {
        fun onPhotoActionSuccessfully(bitmap: Bitmap)
        fun onPhotoActionFailed()
    }

    fun prepareAndSave(sourceUri: Uri, destinationFile: File): Bitmap?
    fun openImage(file: File?): Bitmap?
    fun downloadDocumentAndAdjustOrientation(id: String, width: Int, height: Int, file: File, listener: ActionListener)
}