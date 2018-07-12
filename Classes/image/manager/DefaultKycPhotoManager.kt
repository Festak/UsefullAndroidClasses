package com.lykkex.LykkeWallet.gui.activity.kyc.document.image.manager

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.lykkex.LykkeWallet.gui.LykkeApplication_
import com.lykkex.LykkeWallet.gui.utils.image.transformer.ImageTransformer
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*


/**
 * @author Created by i.statkevich on 25.1.18.
 */
open class DefaultKycPhotoManager(var activity: Activity,
                                  val imageTransformer: ImageTransformer<Uri>)
    : KycPhotoManager {

    override fun prepareAndSave(sourceUri: Uri, destinationFile: File): Bitmap? {
        try {
            var bitmap = getBitmap(sourceUri)
            bitmap = imageTransformer.transform(bitmap!!, sourceUri)
            saveBitmap(bitmap!!, destinationFile)

            return bitmap
        } catch (e: Exception) {
            // do nothing
        }

        return null
    }

    override fun openImage(file: File?): Bitmap? {
        if (file == null) {
            return null
        }

        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        return BitmapFactory.decodeFile(file.absolutePath, options)
    }

    override fun downloadDocumentAndAdjustOrientation(
            id: String, width: Int, height: Int, file: File, listener: KycPhotoManager.ActionListener) {
        val call = LykkeApplication_.getInstance().restApi1.getKycFiles(id)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                var bitmap = downloadToDisk(response?.body(), file)

                if (bitmap != null) {
                    bitmap = imageTransformer.transform(bitmap, null)
                }

                if (bitmap != null) {
                    listener.onPhotoActionSuccessfully(bitmap)
                } else {
                    listener.onPhotoActionFailed()
                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                listener.onPhotoActionFailed()
            }
        })
    }

    private fun getBitmap(uri: Uri): Bitmap? {
        val imageStream = activity.contentResolver.openInputStream(uri)
        val options = BitmapFactory.Options()
        options.inSampleSize = 2
        return BitmapFactory.decodeStream(imageStream, null, options)
    }

    private fun saveBitmap(bitmap: Bitmap, destinationFile: File) {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream)

        val fileOutputStream = FileOutputStream(destinationFile)
        outputStream.writeTo(fileOutputStream)
        fileOutputStream.close()
        fileOutputStream.flush()
    }

    protected fun downloadToDisk(body: ResponseBody?, file: File): Bitmap? {
        if (body == null) {
            return null
        }

        try {
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)
                inputStream = body.byteStream()
                outputStream = FileOutputStream(file)

                while (true) {
                    val read = inputStream!!.read(fileReader)

                    if (read == -1) {
                        break
                    }

                    outputStream.write(fileReader, 0, read)
                }
                outputStream.flush()

                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                return BitmapFactory.decodeFile(file.absolutePath, options)
            } catch (e: IOException) {
                return null
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            return null
        }
    }
}