package com.lykkex.LykkeWallet.gui.utils.sorter

/**
 * @author e.fetskovich on 5/11/18.
 */
interface Sorter<T> {
    fun getListOfSortedItems(objects: List<T>, sortType: SortType): List<T>
}