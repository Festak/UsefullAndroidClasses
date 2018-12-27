package com.destiny.Router.validation

import android.widget.EditText

/**
 * @author e.fetskovich on 12/19/18.
 */
data class NotEmptyModel (var editText: EditText, var name: String){
    init {
        name = name.toLowerCase()
    }
}