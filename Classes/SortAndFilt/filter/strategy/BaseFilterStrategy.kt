package com.lykkex.LykkeWallet.gui.utils.filter.strategy

import com.lykkex.LykkeWallet.gui.utils.filter.FilterStrategy
import com.lykkex.LykkeWallet.gui.utils.sorter.strategy.BaseSortStrategy

/**
 * @author e.fetskovich on 7/27/18.
 */
class BaseFilterStrategy<FObject, FField>(private var sortStrategy: BaseSortStrategy<FObject, FField>) : FilterStrategy<FObject> {
    override fun shouldAddItem(item: FObject, filteredText: String): Boolean {
        return sortStrategy.getFieldValue(item)?.contains(filteredText, true) ?: false

    }
}