package com.destiny.Router.validation

import com.destiny.Router.validation.validators.DataValidator

/**
 * @author e.fetskovich on 12/27/18.
 */
abstract class BaseAbstractValidator : DataValidator {
    open var totalValidatedMessage: String? = null

    override fun validate(): Boolean {
        totalValidatedMessage = ""
        return validateFields()
    }

    // True - not passed, false - passed
    abstract fun validateFields() : Boolean

    override fun getValidateMessage(): String? {
        return totalValidatedMessage
    }

}