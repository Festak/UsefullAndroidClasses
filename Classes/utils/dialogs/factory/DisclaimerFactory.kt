package com.lykkex.LykkeWallet.gui.utils.dialogs.factory

import com.lykkex.LykkeWallet.gui.widgets.dialogs.BaseDisclaimerDialog

/**
 * @author e.fetskovich on 6/25/18.
 */
interface DisclaimerFactory {
    fun createBaseDisclaimerDialog(id: String) : BaseDisclaimerDialog?
}