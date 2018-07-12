package com.lykkex.LykkeWallet.gui.utils.image.transformer

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.support.media.ExifInterface
import android.util.Log
import com.lykkex.LykkeWallet.gui.activity.kyc.document.image.manager.DefaultKycPhotoManager
import java.io.IOException
import java.io.InputStream

/**
 * @author Created by i.statkevich on 2.3.18.
 */
class OrientationImageTransformer(private val activity: Activity)
    : ImageTransformer<Uri> {

    /**
     * This method rotates a bitmap to fill ImageView which has horizontal orientation
     */

    override fun transform(bitmap: Bitmap, data: Uri?): Bitmap? {
        try {
            val orientation = getOrientation(data)
            val matrix: Matrix? = if (bitmap.width >= bitmap.height) {
                // Works with landscape oriented bitmap
                adjustLandscapeBitmap(orientation)
            } else {
                // Defines a matrix to transform a bitmap from portrait to landscape orientation
                rotateToLandscapeOrientation(orientation)
            }

            return if (matrix != null) cloneBitmap(bitmap, matrix) else bitmap
        } catch (e: Exception) {
            // do nothing
        }

        return null
    }

    private fun getOrientation(photoUri: Uri?): Int {
        if (photoUri == null) {
            return ExifInterface.ORIENTATION_UNDEFINED
        }

        var inputStream: InputStream? = null

        try {
            inputStream = activity.contentResolver.openInputStream(photoUri)
            val exif = ExifInterface(inputStream!!)

            return exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
        } catch (e: IOException) {
            // do nothing
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                // do nothing
            }
        }

        return ExifInterface.ORIENTATION_UNDEFINED
    }

    private fun adjustLandscapeBitmap(orientation: Int): Matrix? {
        val matrix = Matrix()

        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                matrix.setRotate(180f)
                matrix.postScale(-1f, 1f)
                // do nothing return exist image
                return null
            }
            else -> return null
        }

        return matrix
    }

    private fun rotateToLandscapeOrientation(orientation: Int): Matrix {
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
            ExifInterface.ORIENTATION_TRANSPOSE -> {
                matrix.setRotate(90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_TRANSVERSE -> {
                matrix.setRotate(-90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(-90f)
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            else -> matrix.setRotate(90f)
        }

        return matrix
    }

    private fun cloneBitmap(bitmap: Bitmap, matrix: Matrix): Bitmap? {
        return try {
            val result = Bitmap.createBitmap(
                    bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            bitmap.recycle()
            result
        } catch (e: OutOfMemoryError) {
            Log.w(DefaultKycPhotoManager::class.java.simpleName, "Error while rotating bitmap.", e)
            null
        }
    }
}