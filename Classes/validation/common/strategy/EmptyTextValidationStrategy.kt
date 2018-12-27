package com.destiny.Router.validation.common.strategy

import android.text.TextUtils

/**
 * @author e.fetskovich on 12/27/18.
 */
class EmptyTextValidationStrategy : ValidationStrategy {

    override fun validate(text: String?): Boolean {
        return TextUtils.isEmpty(text)
    }

}