package com.destiny.Router.validation.validators

/**
 * @author e.fetskovich on 12/27/18.
 */
interface DataValidator {
    fun validate() : Boolean
    fun getValidateMessage() : String?
}