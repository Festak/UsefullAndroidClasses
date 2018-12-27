package com.destiny.Router.validation.common.strategy

/**
 * @author e.fetskovich on 12/27/18.
 */
class CharContainsValidationStrategy(var array: CharArray) : ValidationStrategy {

    override fun validate(text: String?): Boolean {
        array.forEach {
            if (text?.contains(it) == true) {
                return true
            }
        }
        return false
    }
}