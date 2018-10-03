package com.lykkex.LykkeWallet.gui.utils.base_navigation

import android.app.FragmentManager
import android.os.Bundle
import android.support.annotation.IdRes
import com.lykkex.LykkeWallet.R
import com.lykkex.LykkeWallet.gui.activity.MainActivity
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.MarginMainFragment_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.SettingFragmentV2_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.SidebarFragmentV2_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.SupportFragment_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.about.fragment.AboutFragment_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.funds.FundsFragment_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.my_lykke.news.NewsFragmentV2_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.privatewallet.PrivateWalletFragment_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.profile.ProfileFragment_
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.tradings.trade.TradeContainerFragment_
import com.lykkex.LykkeWallet.gui.home.HomeFragment_
import com.lykkex.LykkeWallet.gui.trading_wallet.TradingWalletContainerFragment_
import com.lykkex.LykkeWallet.gui.utils.Constants

/**
 * @author e.fetskovich on 9/14/18.
 */
class BaseTabNavigator(var activity: MainActivity) {
    private var navigationMap: Map<Int, MutableList<FragmentData>>

    private var navigationModels: List<NavigationModel>

    init {
        navigationModels = listOf(
                NavigationModel(R.id.nav_home, "", HomeFragment_::class.java.simpleName),
                NavigationModel(R.id.nav_trading, activity.getString(R.string.trading_wallet_item), TradingWalletContainerFragment_::class.java.simpleName),
                NavigationModel(R.id.nav_private, activity.getString(R.string.private_wallet_item), PrivateWalletFragment_::class.java.simpleName),
                NavigationModel(R.id.nav_side_bar, activity.getString(R.string.more), SidebarFragmentV2_::class.java.simpleName),
                NavigationModel(R.id.nav_exchange, activity.getString(R.string.trade_item), TradeContainerFragment_::class.java.simpleName),
                NavigationModel(R.id.nav_funds_bar, activity.getString(R.string.funds_title), FundsFragment_::class.java.simpleName),
                NavigationModel(R.id.nav_margin, activity.getString(activity.marginTitle), MarginMainFragment_::class.java.simpleName),
                NavigationModel(R.id.more_settings, activity.getString(R.string.more_settings), SettingFragmentV2_::class.java.simpleName),
                NavigationModel(R.id.more_news, activity.getString(R.string.more_news), NewsFragmentV2_::class.java.simpleName),
                NavigationModel(R.id.nav_graph_bar, "", ""),
                NavigationModel(R.id.more_profile, activity.getString(R.string.profile), ProfileFragment_::class.java.simpleName),
                NavigationModel(R.id.more_support, activity.getString(R.string.more_support), SupportFragment_::class.java.simpleName),
                NavigationModel(R.id.nav_about, activity.getString(R.string.settings_about), AboutFragment_::class.java.simpleName)
        )

        navigationMap = hashMapOf(
                R.id.nav_funds_bar to mutableListOf(FragmentData(getFragmentByNavId(R.id.nav_funds_bar), null, R.id.nav_funds_bar)),
                R.id.nav_home to mutableListOf(FragmentData(getFragmentByNavId(R.id.nav_home), null, R.id.nav_home)),
                R.id.nav_side_bar to mutableListOf(FragmentData(getFragmentByNavId(R.id.nav_side_bar), null, R.id.nav_side_bar)),
                R.id.nav_exchange to mutableListOf(FragmentData(getFragmentByNavId(R.id.nav_exchange), null, R.id.nav_exchange)))
    }

    var currentMainTab: Int = R.id.nav_home

    fun navigateByMainTab(@IdRes navResId: Int) {
        currentMainTab = navResId
        val list = navigationMap.get(navResId)!!
        if (list.size > 1) {
            // Switch on the previous fragment
            val fragmentData = list[list.size - 1]
            switchFragment(fragmentData.tabId, fragmentData.bundle, NavigationDirection.TAB)
        } else {
            activity.setHomeButtonEnabled(false)
            switchFragment(navResId, null, NavigationDirection.TAB)
        }
    }


    fun navigateToNextFragments(@IdRes tabId: Int, bundle: Bundle?) {
        val fragment = getFragmentByNavId(tabId)
        navigationMap[currentMainTab]!!.add(FragmentData(fragment, bundle, tabId))
        switchFragment(tabId, bundle, NavigationDirection.NEXT)
    }

    fun navigateToBackFragment() {
        val list = navigationMap.get(currentMainTab)!!
        when {
            list.size - 2 > 0 -> {
                // navigate to previous fragment
                val data = list[list.size - 2]
                list.removeAt(list.size - 1)
                switchFragment(data.tabId, data.bundle, NavigationDirection.BACK)
            }
            list.size == 1 -> activity.finish()
            else -> {
                // navigate to main tab
                activity.setHomeButtonEnabled(false)
                list.removeAt(list.size - 1)
                navigateByMainTab(currentMainTab)
            }
        }
    }


    private fun switchFragment(tabId: Int, bundle: Bundle?, direction: NavigationDirection) {
        val fragmentManager = activity.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val currFragment = fragmentManager.findFragmentById(R.id.fragmentContainer)
        currFragment?.let {
            if (getTabIdForFragmentClass(it.javaClass.simpleName) != null) {
                transaction.detach(it)
            } else {
                fragmentManager.beginTransaction()
                        .remove(fragmentManager.findFragmentById(R.id.fragmentContainer))
                        .commit()
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }

        var fragment = fragmentManager.findFragmentByTag(tabId.toString() + currentMainTab.toString())
        if (fragment == null) {
            fragment = getFragmentByNavId(tabId)

            fragment.arguments = bundle
            transaction.add(R.id.fragmentContainer, fragment, tabId.toString() + currentMainTab.toString())
        } else {
            transaction.attach(fragment)
        }
        transaction.commit()
    }


    fun getTabIdForFragmentClass(className: String): Int? {
        return (navigationModels.firstOrNull { it.tabClassName.equals(className, false) })?.tabId
    }

    private fun getTitleForTab(tabId: Int): String {
        return (navigationModels.firstOrNull { it.tabId == tabId })?.tabTitle ?: ""
    }

    fun getFragmentByNavId(@IdRes navResId: Int): BaseFragment {
        return (navigationModels.firstOrNull { it.tabId == navResId })?.getCurrentTabFragment()
                ?: HomeFragment_.builder().build()
    }

    fun setTitleForTab() {
        val list = navigationMap.get(currentMainTab)!!
        val fragmentData = list[list.size - 1]
        val title = fragmentData.bundle?.getString(Constants.EXTRA_CUSTOM_TITLE) ?: getTitleForTab(fragmentData.tabId)
        activity.title = title
        setToolbarIconByTab(fragmentData.tabId)
    }

    private fun setToolbarIconByTab(tabId: Int) {
        if (tabId == R.id.nav_home) {
            configureToolbar()
        } else {
            activity.supportActionBar?.setDisplayUseLogoEnabled(false)
        }
    }

    private fun configureToolbar() {
        val actionBar = activity.supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true)
            actionBar.setLogo(R.drawable.right_drawable_logo)
            actionBar.setDisplayUseLogoEnabled(true)
        }
    }
}