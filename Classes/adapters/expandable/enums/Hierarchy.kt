package com.lykkex.LykkeWallet.gui.adapters.expandable.enums

/**
 * @author e.fetskovich on 4/23/18.
 */

enum class Hierarchy constructor(val code: Int) {
    UNDEFINED(0),
    CHILD(-1),
    PARENT(1);

    companion object {
        fun createByCode(code: Int): Hierarchy {
            return Hierarchy.values().firstOrNull { it.code == code } ?: UNDEFINED
        }
    }


}
