package com.lykkex.LykkeWallet.gui.utils.base_navigation

import android.app.FragmentManager
import android.os.Bundle
import android.support.annotation.IdRes
import com.lykkex.LykkeWallet.R
import com.lykkex.LykkeWallet.gui.activity.MainActivity
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.*
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.about.fragment.AboutFragment_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.funds.FundsFragment_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.my_lykke.news.NewsFragmentV2_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.privatewallet.PrivateWalletFragment_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.profile.ProfileFragment_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.tradings.trade.TradeContainerFragment_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.tradings.trade.trade_graph.TradeGraphFragment_
import com.lykkex.LykkeWallet.gui.home.HomeFragment_
import com.lykkex.LykkeWallet.gui.utils.Constants

/**
 * @author e.fetskovich on 9/14/18.
 */
class BaseTabNavigator(var activity: MainActivity) {

    private var navigationMap: Map<Int, MutableList<FragmentData>> = hashMapOf(
            R.id.nav_funds_bar to mutableListOf(FragmentData(getFragmentByNavId(R.id.nav_funds_bar), null, R.id.nav_funds_bar)),
            R.id.nav_home to mutableListOf(FragmentData(getFragmentByNavId(R.id.nav_home), null, R.id.nav_home)),
            R.id.nav_side_bar to mutableListOf(FragmentData(getFragmentByNavId(R.id.nav_side_bar), null, R.id.nav_side_bar)),
            R.id.nav_exchange to mutableListOf(FragmentData(getFragmentByNavId(R.id.nav_exchange), null, R.id.nav_exchange)))

    var currentMainTab: Int = R.id.nav_home

    fun navigateByMainTab(@IdRes navResId: Int) {
        currentMainTab = navResId
        val list = navigationMap.get(navResId)!!
        if (list.size > 1) {
            // Switch on the previous fragment
            val fragmentData = list.get(list.size - 1)
            switchFragment(fragmentData.tabId, fragmentData.bundle)
        } else {
            activity.setHomeButtonEnabled(false)
            switchFragment(navResId, null)
        }
    }


    fun navigateToNextFragments(@IdRes tabId: Int, bundle: Bundle?) {
        val fragment = getFragmentByNavId(tabId)
        navigationMap.get(currentMainTab)!!.add(FragmentData(fragment, bundle, tabId))
        switchFragment(tabId, bundle)
    }

    fun navigateToBackFragment() {
        val list = navigationMap.get(currentMainTab)!!
        if (list.size - 2 > 0) {
            // navigate to previous fragment
            val data = list.get(list.size - 2)
            list.removeAt(list.size - 1)
            switchFragment(data.tabId, data.bundle)
        } else if (list.size == 1) {
            activity.finish()
        } else {
            // navigate to main tab
            activity.setHomeButtonEnabled(false)
            list.removeAt(list.size - 1)
            navigateByMainTab(currentMainTab)
        }
    }


    private fun switchFragment(tabId: Int, bundle: Bundle?) {
        val fragmentManager = activity.fragmentManager
        val transaction = fragmentManager.beginTransaction()
        val currFragment = fragmentManager.findFragmentById(R.id.fragmentContainer)

        currFragment?.let {
            if (getTabIdForFragmentClass(it.javaClass.simpleName) != null) {
                transaction.detach(it)
            } else {
                for (i in 1..fragmentManager.backStackEntryCount) {
                    fragmentManager.beginTransaction()
                            .remove(fragmentManager.findFragmentById(R.id.fragmentContainer))
                            .commit()
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                }
            }
        }

        var fragment = fragmentManager.findFragmentByTag(tabId.toString())
        if (fragment == null) {
            fragment = getFragmentByNavId(tabId)

            fragment.arguments = bundle
            transaction.add(R.id.fragmentContainer, fragment, tabId.toString())
        } else {
            transaction.attach(fragment)
        }
        transaction.commit()
    }

    fun getTabIdForFragmentClass(className: String): Int? {
        return when (className) {
            HomeFragment_::class.java.simpleName -> R.id.nav_home
            WalletFragment_::class.java.simpleName -> R.id.nav_trading
            TradeContainerFragment_::class.java.simpleName -> R.id.nav_exchange
            PrivateWalletFragment_::class.java.simpleName -> R.id.nav_private
            FundsFragment_::class.java.simpleName -> R.id.nav_funds_bar
            SidebarFragmentV2_::class.java.simpleName -> R.id.nav_side_bar
            MarginMainFragment_::class.java.simpleName -> R.id.nav_margin
            TradeGraphFragment_::class.java.simpleName -> R.id.nav_graph_bar
            SettingFragmentV2_::class.java.simpleName -> R.id.more_settings
            SupportFragment_::class.java.simpleName -> R.id.more_support
            ProfileFragment_::class.java.simpleName -> R.id.more_profile
            NewsFragmentV2_::class.java.simpleName -> R.id.more_news
            AboutFragment_::class.java.simpleName -> R.id.nav_about
            else -> return null
        }
    }

    fun getFragmentByNavId(@IdRes navResId: Int) = when (navResId) {
        R.id.nav_home -> HomeFragment_.builder().build()
        R.id.nav_trading -> WalletFragment_.builder().build()
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

    fun setTitleForTab() {
        val list = navigationMap.get(currentMainTab)!!
        val fragmentData = list.get(list.size - 1)
        val title = fragmentData.bundle?.getString(Constants.EXTRA_CUSTOM_TITLE) ?: getTitleForTab(fragmentData.tabId)
        activity.setTitle(title)
    }

    private fun getTitleForTab(tabId: Int): String {
        when (tabId) {
            R.id.nav_home -> return activity.getString(R.string.home_item)
            R.id.nav_trading -> return activity.getString(R.string.trading_wallet_item)
            R.id.nav_private -> return activity.getString(R.string.private_wallet_item)
            R.id.nav_side_bar -> return activity.getString(R.string.more)
            R.id.nav_exchange -> return activity.getString(R.string.trade_item)
            R.id.nav_funds_bar -> return activity.getString(R.string.funds_title)
            R.id.nav_margin -> return activity.getString(activity.getMarginTitle())
            R.id.more_settings -> return activity.getString(R.string.more_settings)
            R.id.more_news -> return activity.getString(R.string.more_news)
            R.id.more_profile -> return activity.getString(R.string.profile)
            R.id.more_support -> return activity.getString(R.string.more_support)
            R.id.nav_about -> return activity.getString(R.string.settings_about)
            else -> throw IllegalArgumentException()
        }
    }

}