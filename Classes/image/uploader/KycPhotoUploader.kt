package com.lykkex.LykkeWallet.gui.activity.kyc.document.image.uploader

import com.lykkex.LykkeWallet.gui.models.kyc.document.Step
import com.lykkex.LykkeWallet.rest.base.models.Error
import java.io.File

/**
 * @author Created by i.statkevich on 25.1.18.
 */
interface KycPhotoUploader {
    interface Listener {
        fun onRequestSuccessful()
        fun onRequestFailed(error: Error?)
    }

    fun uploadPhoto(file: File, documentData: Step.DocumentData, listener: Listener)
    fun submitUploading(listener: Listener)
    fun cancel()
}