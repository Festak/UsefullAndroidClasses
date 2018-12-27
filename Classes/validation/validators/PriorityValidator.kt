package com.destiny.Router.validation.validators

import android.content.Context
import com.destiny.Router.validation.BaseAbstractValidator
import com.destiny.Router.validation.BaseValidationModel

/**
 * @author e.fetskovich on 12/27/18.
 */
class PriorityValidator(var context: Context, var list: List<BaseValidationModel>) : BaseAbstractValidator() {

    override fun validateFields(): Boolean {
        list.forEach {
            if (it.stategy.validate(it.editText.text.toString())) {
                totalValidatedMessage = it.additionalMessage ?: context.getString(it.errorMsg)
                return true
            }
        }
        return false
    }

}