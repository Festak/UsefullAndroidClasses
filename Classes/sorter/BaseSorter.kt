package com.lykkex.LykkeWallet.gui.utils.sorter

import com.lykkex.LykkeWallet.gui.utils.sorter.strategy.BaseSortStrategy

/**
 * @author e.fetskovich on 5/16/18.
 */

class BaseSorter<SObject, SField>(private var sortStrategy: BaseSortStrategy<SObject, SField>) : Sorter<SObject> {

    override fun getListOfSortedItems(objects: List<SObject>, sortType: SortType): List<SObject> {
        var sortedList = emptyList<SObject>()
        when (sortType) {
            SortType.ASC -> sortedList = objects.sortedWith(compareBy({ sortStrategy.getFieldValue(it) }))
            SortType.DESC -> sortedList = objects.sortedWith(compareByDescending({ sortStrategy.getFieldValue(it) }))
        }
        return sortedList
    }
}
