package com.lykkex.LykkeWallet.gui.utils.base_navigation

import android.support.annotation.IdRes
import com.lykkex.LykkeWallet.R
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.*
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.about.fragment.AboutFragment_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.funds.FundsFragment_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.my_lykke.news.NewsFragmentV2_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.privatewallet.PrivateWalletFragment_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.profile.ProfileFragment_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.tradings.trade.TradeContainerFragment_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.tradings.trade.trade_graph.TradeGraphFragment_
import com.lykkex.LykkeWallet.gui.home.HomeFragment_
import com.lykkex.LykkeWallet.gui.trading_wallet.TradingWalletContainerFragment_

/**
 * @author e.fetskovich on 9/21/18.
 */
data class NavigationModel (@IdRes val tabId: Int,
                            var tabTitle: String,
                            var tabClassName: String) {

    fun getCurrentTabFragment() = when (tabId) {
        R.id.nav_home -> HomeFragment_.builder().build()
        R.id.nav_trading -> TradingWalletContainerFragment_.builder().build()
        R.id.nav_exchange -> TradeContainerFragment_.builder().build()
        R.id.nav_private -> PrivateWalletFragment_.builder().build()
        R.id.nav_side_bar -> SidebarFragmentV2_.builder().build()
        R.id.nav_funds_bar -> FundsFragment_.builder().build()
        R.id.nav_margin -> MarginMainFragment_.builder().build()
        R.id.nav_graph_bar -> TradeGraphFragment_.builder().build()
        R.id.more_settings -> SettingFragmentV2_.builder().build()
        R.id.more_support -> SupportFragment_.builder().build()
        R.id.more_profile -> ProfileFragment_.builder().build()
        R.id.more_news -> NewsFragmentV2_.builder().build()
        R.id.nav_about -> AboutFragment_.builder().build()
        else -> throw IllegalArgumentException()
    }
}