package com.destiny.Router.validation.common.strategy

/**
 * @author e.fetskovich on 12/27/18.
 */
interface ValidationStrategy {
    fun validate(text: String?): Boolean
}