package com.lykkex.LykkeWallet.gui.utils

import android.content.Context

object ResourceIdentifier {

    const val TYPE_ID = "id"
    const val TYPE_DRAWABLE = "drawable"
    const val TYPE_STRING = "string"

    fun getIdForStringViewId(context: Context, id: String): Int {
        return getIdentifier(context, id, TYPE_ID)
    }

    fun getIdForStringDrawable(context: Context, id: String): Int {
        return getIdentifier(context, id, TYPE_DRAWABLE)
    }

    fun getIdForString(context: Context, id: String): Int {
        return getIdentifier(context, id, TYPE_STRING)
    }

    fun getStringResourceByName(context: Context, stringId: String): String {
        return context.getString(getIdForString(context, stringId))
    }

    fun getIdentifier(context: Context, id: String, defType: String): Int {
        return context.resources.getIdentifier(id, defType, context.packageName)
    }

}