package com.destiny.Router.validation.common.strategy

import android.text.TextUtils

/**
 * @author e.fetskovich on 12/27/18.
 */
class LengthMinLimitValidator (var limitValue: Int) : ValidationStrategy {

    override fun validate(text: String?): Boolean {
        return TextUtils.isEmpty(text) || (text?.length ?: 0 < limitValue)
    }
}