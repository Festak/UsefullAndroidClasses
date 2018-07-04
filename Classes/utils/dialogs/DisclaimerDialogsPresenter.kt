package com.lykkex.LykkeWallet.gui.utils.dialogs

import android.app.Fragment
import com.lykkex.LykkeWallet.gui.utils.dialogs.factory.BaseDisclaimerFactory
import com.lykkex.LykkeWallet.gui.utils.dialogs.factory.DisclaimerFactory
import com.lykkex.LykkeWallet.gui.widgets.dialogs.BaseDisclaimerDialog
import com.lykkex.LykkeWallet.gui.widgets.dialogs.base.ButtonType

/**
 * @author e.fetskovich on 6/25/18.
 */

class DisclaimerDialogsPresenter(private val fragment: Fragment, private val transferDialogs: HashSet<String>?,
                                 private val shouldSkipNextTime: ShouldSkipNextTime) : DialogsPresenter {
    private val disclaimerFactory: DisclaimerFactory

    init {
        disclaimerFactory = BaseDisclaimerFactory(fragment)
    }

    override fun showDialog(id: String, showAll: Boolean) {
        val dialog = disclaimerFactory.createBaseDisclaimerDialog(id)
        dialog?.setDialogCallback(initOnDisclaimerDialogButtonClick(showAll))
        dialog?.show(fragment.fragmentManager, id)
    }

    override fun showDialogs() {
        if (transferDialogs != null && !transferDialogs.isEmpty()) {
            val text = transferDialogs.elementAt(0)
            showDialog(text, true)
            transferDialogs.remove(text)
        }
    }


    private fun initOnDisclaimerDialogButtonClick(showAll: Boolean): BaseDisclaimerDialog.DisclaimerDialogCallback {
        return object : BaseDisclaimerDialog.DisclaimerDialogCallback {
            override fun onResultProceed(buttonType: ButtonType, dialogId: String, shouldAddCheckboxChecked: Boolean) {
                skipDialogNextTime(shouldAddCheckboxChecked, dialogId)
                showOtherDialogs(showAll)
            }
        }
    }

    private fun skipDialogNextTime(shouldSkip: Boolean, dialogId: String) {
        if (shouldSkip) {
            shouldSkipNextTime.onShouldSkipNextTimeCallback(dialogId)
        }
    }

    private fun showOtherDialogs(showAll: Boolean) {
        if (showAll) {
            showDialogs()
        }
    }

    interface ShouldSkipNextTime {
        fun onShouldSkipNextTimeCallback(id: String)
    }

}
