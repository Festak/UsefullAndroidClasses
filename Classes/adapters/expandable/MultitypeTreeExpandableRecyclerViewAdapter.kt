package com.lykkex.LykkeWallet.gui.adapters.expandable

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.lykkex.LykkeWallet.gui.adapters.expandable.models.ListItemModel
import com.lykkex.LykkeWallet.gui.adapters.expandable.models.TreeNodeModel
import com.lykkex.LykkeWallet.gui.internal.ViewHolder
import java.util.*

/**
 * @author e.fetskovich on 4/23/18.
 */

abstract class MultitypeTreeExpandableRecyclerViewAdapter : RecyclerView.Adapter<ViewHolder<out ViewGroup>>() {

    private var allNodes: List<TreeNodeModel> = ArrayList()
    private var displayNodes: MutableList<TreeNodeModel> = ArrayList()

    protected abstract fun createParentView(parent: ViewGroup, viewType: Int): ViewGroup
    protected abstract fun createChildView(parent: ViewGroup, viewType: Int): ViewGroup
    protected abstract fun onBindParentViewHolder(holder: ViewHolder<out ViewGroup>, data: ListItemModel,
                                                  childCount: Int, totalChildCount: Int, isExpand: Boolean)
    protected abstract fun onBindChildViewHolder(holder: ViewHolder<out ViewGroup>, data: ListItemModel)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<out ViewGroup> {
        return if (isParent(viewType)) {
            ViewHolder(createParentView(parent, viewType))
        } else {
            ViewHolder(createChildView(parent, viewType))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder<out ViewGroup>, position: Int) {
        val viewType = holder.itemViewType
        if (isParent(viewType)) {
            val model = displayNodes[position]
            onBindParentViewHolder(holder, model.data, model.getChildCount(), model.getTotalChildCount(), model.isExpand)
        } else {
            onBindChildViewHolder(holder, displayNodes[position].data)
        }
    }

    protected fun setNodes(nodes: List<TreeNodeModel>) {
        allNodes = nodes
        calculateDisplayNodes()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return displayNodes.size
    }

    override fun getItemViewType(position: Int): Int {
        return displayNodes[position].getType()
    }

    protected fun isParent(viewType: Int): Boolean {
        return viewType > 0;
    }

    private fun calculateDisplayNodes() {
        displayNodes = ArrayList()
        addNodesRecursively(allNodes)
    }

    private fun addNodesRecursively(list: List<TreeNodeModel>) {
        list.forEach { node ->
            if (node.expandType === TreeNodeModel.ExpandType.EXPAND_TO_BOTTOM) {
                displayNodes.add(node)
                addChildren(node)
            } else {
                addChildren(node)
                displayNodes.add(node)
            }
        }
    }

    private fun addChildren(node: TreeNodeModel) {
        if (node.getVisibleChildCount() > 0) {
            addNodesRecursively(node.childList!!)
        }
    }

    /**
     * @param position
     * @param collapsePrevious
     * @return true if expanded, false if collapsed
     */
    @JvmOverloads
    protected fun toggle(position: Int, collapsePrevious: Boolean = false): Boolean {
        val treeNode = displayNodes[position]
        val type = treeNode.expandType
        return if (treeNode.isExpand) {
            collapseChildren(treeNode, type, position)
        } else {
            expandChildren(treeNode, type, position, collapsePrevious)
        }
    }

    private fun expandChildren(treeNode: TreeNodeModel, type: TreeNodeModel.ExpandType, position: Int, collapsePrevious: Boolean): Boolean {
        var newPosition = position
        if (collapsePrevious && collapsePrevious(type)) {
            newPosition = displayNodes.indexOf(treeNode)
        }
        if (treeNode.expand()) {
            calculateDisplayNodes()
            notifyItemChanged(newPosition)
            val childrenCount = treeNode.getChildCountWithExpandedTypes()
            if (type === TreeNodeModel.ExpandType.EXPAND_TO_BOTTOM) {
                notifyItemRangeInserted(newPosition + 1, childrenCount)
            } else {
                notifyItemRangeInserted(newPosition, childrenCount)
            }
        }
        return true
    }

    private fun collapseChildren(treeNode: TreeNodeModel, type: TreeNodeModel.ExpandType, position: Int): Boolean {
        if (treeNode.collapse()) {
            collapseItems(treeNode, type, position)
        }
        return false
    }

    private fun collapsePrevious(type: TreeNodeModel.ExpandType): Boolean {
        displayNodes.forEach { node ->
            if (node.collapse()) {
                val position = displayNodes.indexOf(node)
                collapseItems(node, type, position)
                return true
            }
        }
        return false
    }

    private fun collapseItems(node: TreeNodeModel, type: TreeNodeModel.ExpandType, position: Int) {
        calculateDisplayNodes()
        notifyItemChanged(position)
        val childrenCount = node.getChildCountWithExpandedTypes()
        if (type === TreeNodeModel.ExpandType.EXPAND_TO_BOTTOM) {
            notifyItemRangeRemoved(position + 1, childrenCount)
        } else {
            notifyItemRangeRemoved(position - childrenCount, childrenCount)
        }
    }

    fun getData(position: Int): ListItemModel {
        return displayNodes[position].data
    }
}
