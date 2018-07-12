package com.lykkex.LykkeWallet.gui.utils.image.transformer

import android.graphics.Bitmap

/**
 * @author Created by i.statkevich on 2.3.18.
 */
interface ImageTransformer<in T> {
    fun transform(bitmap: Bitmap, data: T? = null): Bitmap?
}