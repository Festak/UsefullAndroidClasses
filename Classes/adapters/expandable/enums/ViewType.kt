package com.lykkex.LykkeWallet.gui.adapters.expandable.enums

/**
 * @author e.fetskovich on 4/23/18.
 */

enum class ViewType constructor(val code: Int) {
    UNDEFINED(0),
    CATEGORY(10),
    ASSET(20),
    INFO(30);

    companion object {
        fun createByCode(code: Int): ViewType {
            return ViewType.values().firstOrNull { it.code == code } ?: UNDEFINED
        }
    }

}
