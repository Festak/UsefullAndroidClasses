package com.destiny.Router.validation

import android.support.annotation.StringRes
import android.widget.EditText
import com.destiny.Router.validation.common.strategy.ValidationStrategy

/**
 * @author e.fetskovich on 12/27/18.
 */
data class BaseValidationModel (var editText: EditText,
                                var stategy: ValidationStrategy,
                                @StringRes var errorMsg: Int,
                                var additionalMessage: String? = null)