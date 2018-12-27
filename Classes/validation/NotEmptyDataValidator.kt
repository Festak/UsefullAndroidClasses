package com.destiny.Router.validation

import android.text.TextUtils

/**
 * @author e.fetskovich on 12/19/18.
 */
class NotEmptyDataValidator(var list: MutableList<NotEmptyModel>) : BaseValidator {

    override fun validate(): String? {
        val emptyFields = mutableListOf<String>()
        list.forEach {
            if (TextUtils.isEmpty(it.editText.text)) {
                emptyFields.add(it.name)
            }
        }

        if (emptyFields.isEmpty()) {
            return null
        } else {
            return emptyFields.joinToString()
        }
    }

}