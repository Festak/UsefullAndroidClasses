package com.lykkex.LykkeWallet.gui.utils.image.dispatcher

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import com.lykkex.LykkeWallet.BuildConfig
import com.lykkex.LykkeWallet.gui.utils.image.dispatcher.PhotoDispatcher.Companion.REQUEST_GALLERY_ACTION
import com.lykkex.LykkeWallet.gui.utils.image.dispatcher.PhotoDispatcher.Companion.REQUEST_IMAGE_CAPTURE
import java.io.File
import java.io.IOException

/**
 * @author Created by i.statkevich on 26.1.18.
 */

abstract class BasePhotoDispatcher(private val context: Context) : PhotoDispatcher {
    /**
     * Suppose that the application has Manifest.permission.READ_EXTERNAL_STORAGE
     */
    override fun getPhotoFromGallery() {
        val intent = Intent()

        // Fix for permission issue on kitkat
        if (Build.VERSION.SDK_INT < 19) {
            intent.action = Intent.ACTION_GET_CONTENT
        } else {
            intent.action = Intent.ACTION_OPEN_DOCUMENT
            intent.addCategory(Intent.CATEGORY_OPENABLE)
        }
        intent.type = "image/*"

        startPhotoGalleryActivity(intent, REQUEST_GALLERY_ACTION)
    }

    /**
     * Suppose that the application has Manifest.permission.CAMERA
     */
    override fun takeImageCapture(
            temporaryDir: File, photoName: String, format: Bitmap.CompressFormat): Uri? {
        val imageCaptureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        var imageUri: Uri? = null

        try {
            val temporaryPhotoFile = createTemporaryFile(temporaryDir, photoName, format)
            imageUri = FileProvider.getUriForFile(
                    context, BuildConfig.APPLICATION_ID + ".provider", temporaryPhotoFile)

            /**
             * Kit-kat camera crash fix
             */
            val resolvedIntentActivities = context.packageManager.queryIntentActivities(
                    imageCaptureIntent, PackageManager.MATCH_DEFAULT_ONLY)
            resolvedIntentActivities
                    .map { it.activityInfo.packageName }
                    .forEach {
                        context.grantUriPermission(it, imageUri,
                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                                        or Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }

            temporaryPhotoFile.delete()

            imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (imageCaptureIntent.resolveActivity(context.packageManager) != null) {
            startTakeImageCaptureActivity(imageCaptureIntent, REQUEST_IMAGE_CAPTURE)
        }

        return imageUri
    }

    abstract protected fun startPhotoGalleryActivity(intent: Intent, requestCode: Int)

    abstract protected fun startTakeImageCaptureActivity(intent: Intent, requestCode: Int)

    @Throws(IOException::class)
    private fun createTemporaryFile(
            temporaryDir: File, prefix: String, format: Bitmap.CompressFormat): File {
        return File.createTempFile(prefix, format.toString().toLowerCase(), temporaryDir)
    }
}