package com.lykkex.LykkeWallet.gui.activity.kyc.document.image.uploader

import com.lykkex.LykkeWallet.gui.LykkeApplication_
import com.lykkex.LykkeWallet.gui.models.kyc.document.Step
import com.lykkex.LykkeWallet.gui.utils.Constants
import com.lykkex.LykkeWallet.gui.utils.validation.BaseCallBackListener
import com.lykkex.LykkeWallet.rest.base.models.Error
import com.lykkex.LykkeWallet.rest.base.models.PBaseCallback
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import java.io.File

/**
 * @author Created by i.statkevich on 25.1.18.
 */

class DefaultKycPhotoUploader(private val profileType: String) : KycPhotoUploader {
    private var restApi = LykkeApplication_.getInstance().restApi1
    private var callUploading: Call<Error>? = null
    private var callSubmit: Call<Error>? = null

    override fun uploadPhoto(file: File, documentData: Step.DocumentData, listener: KycPhotoUploader.Listener) {
        callUploading = createCall(createMultipart(file, MULTIPART_NAME), documentData)
        callUploading?.enqueue(PBaseCallback(object : BaseCallBackListener<Error> {
            override fun onSuccess(result: Error?) {
                if (result?.code == 0) {
                    listener.onRequestSuccessful()
                } else {
                    listener.onRequestFailed(result)
                }
            }

            override fun onFail(error: Error?) {
                listener.onRequestFailed(error)
            }
        }))
    }

    override fun submitUploading(listener: KycPhotoUploader.Listener) {
        callSubmit = restApi.postKycProfile(profileType)
        callSubmit?.enqueue(PBaseCallback(object : BaseCallBackListener<Error> {
            override fun onSuccess(result: Error?) {
                if (result?.code == 0) {
                    listener.onRequestSuccessful()
                } else {
                    listener.onRequestFailed(
                            Error(Constants.ERROR_SERVER, null, "Server error"))
                }
            }

            override fun onFail(error: Error?) {
                listener.onRequestFailed(error)
            }
        }))
    }

    override fun cancel() {
        callUploading?.cancel()
        callSubmit?.cancel()
    }

    private fun createCall(multipartPart: MultipartBody.Part, data: Step.DocumentData): Call<Error> = restApi.uploadKycFile(data.documentType, data.fileType, multipartPart)

    private fun createMultipart(file: File, name: String): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
                name,
                file.name,
                RequestBody.create(MediaType.parse("image/*"), file))
    }

    companion object {
        val MULTIPART_NAME = "file"
    }
}