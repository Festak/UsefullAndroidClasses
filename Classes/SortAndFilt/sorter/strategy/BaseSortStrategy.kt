package com.lykkex.LykkeWallet.gui.utils.sorter.strategy

import com.lykkex.LykkeWallet.gui.utils.sorter.fields.SortedField

/**
 * @author e.fetskovich on 5/16/18.
 */

interface BaseSortStrategy<SObject, SField> {
    fun getFieldValue(sortObject: SObject): String
    fun setSortedField(sortedField: SortedField<SField>)
}
