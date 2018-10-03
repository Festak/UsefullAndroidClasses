package com.lykkex.LykkeWallet.gui.utils.base_navigation

import android.content.Context
import android.os.Bundle
import com.lykkex.LykkeWallet.R
import com.lykkex.LykkeWallet.gui.activity.FragmentActivity_
import com.lykkex.LykkeWallet.gui.activity.MainActivity
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.MainFragmentData
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.enums.ActivityFragmentEnum
import com.lykkex.LykkeWallet.gui.managers.AssetsManager
import com.lykkex.LykkeWallet.gui.utils.Constants
import com.lykkex.LykkeWallet.rest.trading.response.model.AssetPair

/**
 * @author e.fetskovich on 9/14/18.
 */
object NavigationHelper {

    fun openTradeGraphWithTab(assetPair: AssetPair, activity: MainActivity) {
        val bundle = Bundle()
        bundle.putString(Constants.EXTRA_CUSTOM_TITLE, getAssetTitle(assetPair))
        bundle.putSerializable(Constants.EXTRA_ASSET_PAIR, assetPair)
        activity.openSecondLevelFragment(R.id.nav_graph_bar, false, bundle)
    }

    fun openTradeGraphWithContext(assetPair: AssetPair, context: Context) {
        FragmentActivity_
                .intent(context)
                .fragmentData(MainFragmentData(ActivityFragmentEnum.TRADE_GRAPH, getAssetTitle(assetPair)))
                .extra(Constants.EXTRA_ASSET_PAIR, assetPair)
                .start()
    }

    private fun getAssetTitle(assetPair: AssetPair): String {
        val assetsManager = AssetsManager.getInstance()
        return "${assetsManager.displayIdForAsset(assetPair.baseAssetId)}/${assetsManager.displayIdForAsset(assetPair.quotingAssetId)}"
    }

}