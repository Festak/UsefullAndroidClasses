package com.lykkex.LykkeWallet.gui.adapters.expandable.enums

/**
 * @author e.fetskovich on 4/23/18.
 */

// Because Hierarchy type is always Child or Parent, we use this values-notation
enum class Hierarchy constructor(val code: Int) {
    CHILD(-1),
    PARENT(1);
}
