package com.lykkex.LykkeWallet.gui.utils.base_navigation

import android.os.Bundle
import android.support.annotation.IdRes
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment

/**
 * @author e.fetskovich on 9/14/18.
 */
data class FragmentData(var fragment: BaseFragment, var bundle: Bundle?, @IdRes var tabId: Int)
