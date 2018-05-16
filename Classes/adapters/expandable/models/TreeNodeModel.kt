package com.lykkex.LykkeWallet.gui.adapters.expandable.models

import com.lykkex.LykkeWallet.gui.adapters.expandable.enums.Hierarchy
import java.util.*

/**
 * @author e.fetskovich on 4/24/18.
 */

// Child constructor
class TreeNodeModel(val data: ListItemModel, val hierarchyType: Hierarchy = Hierarchy.CHILD) {

    var isExpand: Boolean = false;
    var expandType: ExpandType = ExpandType.EXPAND_TO_BOTTOM;

    constructor(data: ListItemModel, isExpand: Boolean,
                expandType: ExpandType = ExpandType.EXPAND_TO_BOTTOM) : this(data, Hierarchy.PARENT) {
        // Parent constructor
        this.isExpand = isExpand;
        this.expandType = expandType;
    }

    var parent: TreeNodeModel? = null

    var childList: MutableList<TreeNodeModel>? = null

    fun addChild(node: TreeNodeModel) {
        if (childList == null) {
            childList = ArrayList()
        }
        node.parent = this
        childList?.add(node)
    }

    fun getChildCount(): Int {
        return childList?.size ?: 0;
    }

    var count = 0;
    fun getChildCountWithExpandedTypes(): Int {
        count = 0;
        return getTotalExpandedChildCountInList(childList);
    }

    fun getTotalChildCount() : Int{
        count = 0;
        return getTotalChildCountInList(childList);
    }

    fun getTotalExpandedChildCountInList(list: MutableList<TreeNodeModel>?): Int {
        if (list != null) {
            list.forEach { model ->
                count++;
                if (model.isExpand) {
                    getTotalExpandedChildCountInList(model.childList);
                }
            }
        }
        return count;
    }

    fun getTotalChildCountInList(list: MutableList<TreeNodeModel>?): Int {
        if (list != null) {
            list.forEach { model ->
                count++;
                if (model.childList != null) {
                    count--
                    getTotalChildCountInList(model.childList);
                }
            }
        }
        return count;
    }

    fun getVisibleChildCount(): Int {
        return if (isExpand && childList != null) childList!!.size else 0;
    }

    fun collapse(): Boolean {
        if (isExpand && getChildCount() > 0) {
            isExpand = false
            return true
        }
        return false
    }

    fun expand(): Boolean {
        if (!isExpand && getChildCount() > 0) {
            isExpand = true
            return true
        }
        return false
    }

    // Type contains ViewType(by value) and HierarchyType(by less/more then zero)
    fun getType(): Int {
        return data.viewType.code * hierarchyType.code
    }

    enum class ExpandType {
        EXPAND_TO_TOP,
        EXPAND_TO_BOTTOM
    }

}
