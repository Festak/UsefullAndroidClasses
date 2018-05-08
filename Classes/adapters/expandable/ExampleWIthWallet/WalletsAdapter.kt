package com.lykkex.LykkeWallet.gui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.lykkex.LykkeWallet.R
import com.lykkex.LykkeWallet.gui.adapters.expandable.MultitypeTreeExpandableRecyclerViewAdapter
import com.lykkex.LykkeWallet.gui.adapters.expandable.enums.ViewType
import com.lykkex.LykkeWallet.gui.adapters.expandable.models.ListItemModel
import com.lykkex.LykkeWallet.gui.adapters.expandable.models.TreeNodeModel
import com.lykkex.LykkeWallet.gui.internal.ViewHolder
import com.lykkex.LykkeWallet.gui.models.ShowInfoModel
import com.lykkex.LykkeWallet.gui.recyclerviewitems.*
import com.lykkex.LykkeWallet.gui.utils.helpers.assets.AssetsSorter
import com.lykkex.LykkeWallet.gui.utils.helpers.assets.AssetsSorterWorker
import com.lykkex.LykkeWallet.rest.wallet.response.models.AssetsWallet
import com.lykkex.LykkeWallet.rest.wallet.response.models.Category
import kotlinx.android.synthetic.main.bottom_info_item.view.*
import java.util.*

/**
 * @author e.fetskovich on 4/24/18.
 */

class WalletsAdapter(collapsedCategoriesIds: Set<String>, expandedNotDefaultAssetsInCategory: Set<String>,
                     private val walletClickListener: OnWalletClickListener) : MultitypeTreeExpandableRecyclerViewAdapter(), View.OnClickListener {

    private val EMPTY_NON_DEFAULT_ASSETS = 0;

    private val collapsedCategoriesIds: HashSet<String>
    private val expandedNotDefaultAssetsInCategory: HashSet<String>
    private val sorter: AssetsSorter

    init {
        this.collapsedCategoriesIds = HashSet(collapsedCategoriesIds)
        this.expandedNotDefaultAssetsInCategory = HashSet(expandedNotDefaultAssetsInCategory)
        sorter = AssetsSorterWorker()
    }

    fun setData(categories: List<Category>, assetWallets: List<AssetsWallet>) {
        val nodes = ArrayList<TreeNodeModel>()
        categories.forEach { category ->
            val parent = TreeNodeModel(
                    ListItemModel(category, ViewType.CATEGORY),
                    !collapsedCategoriesIds.contains(category.id),
                    TreeNodeModel.ExpandType.EXPAND_TO_BOTTOM
            )

            if (!assetWallets.isEmpty() && !TextUtils.isEmpty(category.id)) {
                val listOfWalletsInCategory = sorter.getListWithItemsOfTheCategory(category.id, assetWallets)

                val topAssets = sorter.getListOfSortedAssetAlphabeticallyWithType(listOfWalletsInCategory, AssetsWallet.AssetTypeInWalletList.TOP)
                topAssets.forEach { parent.addChild(TreeNodeModel(ListItemModel(it, ViewType.ASSET))) }

                val bottomAssets = sorter.getListOfSortedAssetAlphabeticallyWithType(listOfWalletsInCategory, AssetsWallet.AssetTypeInWalletList.BOTTOM)
                if (bottomAssets.isNotEmpty()) {
                    val bottomNodes = TreeNodeModel(
                            ListItemModel(ShowInfoModel(category.id, bottomAssets.size), ViewType.INFO),
                            expandedNotDefaultAssetsInCategory.contains(category.id),
                            TreeNodeModel.ExpandType.EXPAND_TO_TOP
                    )
                    bottomAssets.forEach { bottomNodes.addChild(TreeNodeModel(ListItemModel(it, ViewType.BOTTOM_ASSET))) }
                    parent.addChild(bottomNodes)
                }

                if (parent.getChildCount() > 0) {
                    nodes.add(parent)
                }
            }
        }
        setNodes(nodes)
    }


    override fun createParentView(parent: ViewGroup, viewType: Int): ViewGroup {
        val viewTypeEnum = ViewType.createByCode(Math.abs(viewType))
        var parentViewGroup: ViewGroup = parent
        when (viewTypeEnum) {
            ViewType.CATEGORY -> parentViewGroup = initCategoryViewItem(parent.context)
            ViewType.INFO -> parentViewGroup = initInfoViewItem(parent.context)
        }
        return parentViewGroup
    }

    private fun initCategoryViewItem(context: Context): ViewGroup {
        val category = WalletListItem_.build(context)
        category.setOnClickListener(this)
        return category
    }

    private fun initInfoViewItem(context: Context): ViewGroup {
        val info = WalletListShowAllItem_.build(context)
        info.setOnClickListener(this)
        return info
    }

    override fun createChildView(parent: ViewGroup, viewType: Int): ViewGroup {
        val viewTypeEnum = ViewType.createByCode(Math.abs(viewType))
        var viewItem: ViewGroup = parent

        when (viewTypeEnum) {
            ViewType.ASSET -> viewItem = initAssetViewItem(parent.context)
            ViewType.BOTTOM_ASSET -> viewItem = initBottomAssetViewItem(parent.context)
        }
        return viewItem
    }

    private fun initAssetViewItem(context: Context): ViewGroup {
        val viewItem = WalletInfoItem_.build(context)
        viewItem.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        viewItem.setOnClickListener(this)
        (viewItem as WalletInfoItem).imgPlus.setOnClickListener(this)
        return viewItem
    }

    private fun initBottomAssetViewItem(context: Context): ViewGroup {
        val viewItem = BottomWalletInfoItem_.build(context)
        viewItem.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        viewItem.setOnClickListener(this)
        (viewItem as BottomWalletInfoItem).imgPlus.setOnClickListener(this)
        return viewItem
    }

    override fun onBindParentViewHolder(holder: ViewHolder<out ViewGroup>, data: ListItemModel, childCount: Int, totalChildCount: Int, isExpand: Boolean) {
        val type = data.viewType
        when (type) {
            ViewType.CATEGORY -> {
                val listItem = holder.getItemView() as WalletListItem
                listItem.render(data.data as Category, totalChildCount, isExpand)
                listItem.tag = holder
            }
            ViewType.INFO -> {
                val showAllItem = holder.getItemView() as WalletListShowAllItem
                val itemsCountToShow = if (isExpand) 0 else childCount
                showAllItem.render(itemsCountToShow)
                showAllItem.tag = holder
            }
        }
    }

    override fun onBindChildViewHolder(holder: ViewHolder<out ViewGroup>, data: ListItemModel) {
        val type = data.viewType
        when (type) {
            ViewType.ASSET -> onBindAsset(holder, data.data as AssetsWallet)
            ViewType.BOTTOM_ASSET -> onBindBottomAsset(holder, data.data as AssetsWallet)
        }
    }

    private fun onBindAsset(holder: ViewHolder<out ViewGroup>, assetWallet: AssetsWallet){
        val assetItem = holder.getItemView() as WalletInfoItem
        assetItem.render(assetWallet)
        assetItem.tag = holder
        assetItem.imgPlus.tag = holder
    }

    private fun onBindBottomAsset(holder: ViewHolder<out ViewGroup>, assetWallet: AssetsWallet){
        val assetItem = holder.getItemView() as BottomWalletInfoItem
        assetItem.render(assetWallet)
        assetItem.tag = holder
        assetItem.imgPlus?.tag = holder
    }

    override fun onClick(view: View) {
        if (view.tag is ViewHolder<*>) {
            val holder = view.tag as ViewHolder<*>
            val position = holder.adapterPosition
            if (position == RecyclerView.NO_POSITION) {
                return
            }

            if (isParent(holder.itemViewType)) {
                onParentClick(holder, position)
            } else {
                onChildClick(position, view)
            }
        }
    }

    private fun onParentClick(holder: ViewHolder<*>, position: Int) {
        when (getData(position).viewType) {
            ViewType.CATEGORY -> onCategoryClicked(position)
            ViewType.INFO -> onInfoClicked(holder, position)
        }
    }

    private fun onCategoryClicked(position: Int) {
        val id = (getData(position).data as Category).id
        val expanded = toggle(position)
        addOrRemoveIdByExpandType(expanded, collapsedCategoriesIds, id)
    }

    private fun onInfoClicked(viewHolder: ViewHolder<*>, position: Int) {
        val (categoryId, itemsCount) = getData(position).data as ShowInfoModel
        val expanded = toggle(position)
        val layout = viewHolder.getItemView() as WalletListShowAllItem
        if (expanded) {
            layout.render(itemsCount)
        } else {
            layout.render(EMPTY_NON_DEFAULT_ASSETS)
        }

        addOrRemoveIdByExpandType(!expanded, expandedNotDefaultAssetsInCategory, categoryId ?: "")
    }

    private fun addOrRemoveIdByExpandType(isExpanded: Boolean, set: MutableSet<String>, id: String) {
        if (isExpanded) {
            set.remove(id)
        } else {
            set.add(id)
        }
    }

    private fun onChildClick(position: Int, view: View) {
        if (getData(position).viewType === ViewType.ASSET) {
            val wallet = getData(position).data as AssetsWallet
            if (view.id == R.id.imgPlus) {
                walletClickListener.onPlusClick(wallet)
            } else {
                walletClickListener.onWalletClick(wallet)
            }
        }
    }

    fun getCollapsedCategoriesIds(): Set<String> {
        return collapsedCategoriesIds
    }

    fun getExpandedNotDefaultAssetsInCategory(): Set<String> {
        return expandedNotDefaultAssetsInCategory
    }

    interface OnWalletClickListener {
        fun onWalletClick(assetsWallet: AssetsWallet)
        fun onPlusClick(assetsWallet: AssetsWallet)
    }

}
