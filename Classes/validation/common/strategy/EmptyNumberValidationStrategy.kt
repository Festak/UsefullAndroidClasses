package com.destiny.Router.validation.common.strategy

import android.text.TextUtils

/**
 * @author e.fetskovich on 12/27/18.
 */
class EmptyNumberValidationStrategy : ValidationStrategy{

    private val EMPTY_NUMBER_VALUE = "0"

    override fun validate(text: String?): Boolean {
        return TextUtils.isEmpty(text) || text?.equals(EMPTY_NUMBER_VALUE) == true
    }
}