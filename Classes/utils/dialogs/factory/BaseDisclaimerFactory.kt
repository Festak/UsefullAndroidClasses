package com.lykkex.LykkeWallet.gui.utils.dialogs.factory

import android.app.Fragment
import com.lykkex.LykkeWallet.R
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.wallet.BlockchainWithdrawFragment
import com.lykkex.LykkeWallet.gui.widgets.dialogs.BaseDisclaimerDialog

/**
 * @author e.fetskovich on 6/25/18.
 */
class BaseDisclaimerFactory(val fragment: Fragment) : DisclaimerFactory {

    override fun createBaseDisclaimerDialog(id: String): BaseDisclaimerDialog? {
        return when (id) {
            BlockchainWithdrawFragment.DISCLAIMER_FUND_DIALOG -> BaseDisclaimerDialog.Companion.newInstance(
                    BlockchainWithdrawFragment.DISCLAIMER_FUND_DIALOG,
                    fragment.getString(R.string.blockchain_withdraw_disclaimer_fund_title),
                    fragment.getString(R.string.blockchain_withdraw_disclaimer_fund_text),
                    true)
            BlockchainWithdrawFragment.DISCLAIMER_TRANSFER_DIALOG -> BaseDisclaimerDialog.Companion.newInstance(
                    BlockchainWithdrawFragment.DISCLAIMER_TRANSFER_DIALOG,
                    fragment.getString(R.string.blockchain_withdraw_disclaimer_transfer_title),
                    fragment.getString(R.string.blockchain_withdraw_disclaimer_transfer_text),
                    true)
            else -> null
        }
    }

}