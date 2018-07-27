package com.lykkex.LykkeWallet.gui.utils.filter

/**
 * @author e.fetskovich on 7/27/18.
 */
interface FilterStrategy<Item> {
    fun shouldAddItem(item: Item, filteredText: String) : Boolean
}