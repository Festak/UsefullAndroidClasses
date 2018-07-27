package com.lykkex.LykkeWallet.gui.utils.sorter.fields

/**
 * @author e.fetskovich on 5/16/18.
 */

enum class AssetsSortField : SortedField<AssetsSortField> {
    DISPLAY_ID,
    ID;

    override fun getField(): AssetsSortField {
        return this;
    }
}
