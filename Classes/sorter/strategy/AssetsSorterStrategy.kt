package com.lykkex.LykkeWallet.gui.utils.sorter.strategy

import com.lykkex.LykkeWallet.gui.managers.AssetsManager
import com.lykkex.LykkeWallet.gui.utils.sorter.fields.AssetsSortField
import com.lykkex.LykkeWallet.gui.utils.sorter.fields.SortedField
import com.lykkex.LykkeWallet.rest.wallet.response.models.AssetsWallet

/**
 * @author e.fetskovich on 5/16/18.
 */

class AssetsSorterStrategy(private var sortedField: SortedField<AssetsSortField>) : BaseSortStrategy<AssetsWallet, AssetsSortField> {

    override fun getFieldValue(sortObject: AssetsWallet): String {
        var fieldValue = ""
        when (sortedField) {
            AssetsSortField.DISPLAY_ID -> fieldValue = AssetsManager.getInstance().displayIdForAsset(sortObject.id)
            AssetsSortField.ID -> fieldValue = sortObject.id
        }
        return fieldValue
    }

    override fun setSortedField(sortedField: SortedField<AssetsSortField>) {
        this.sortedField = sortedField
    }
}
