package com.lykkex.LykkeWallet.gui.adapters.expandable;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.lykkex.LykkeWallet.gui.adapters.expandable.enums.Hierarchy;
import com.lykkex.LykkeWallet.gui.adapters.expandable.models.ListItemModel;
import com.lykkex.LykkeWallet.gui.adapters.expandable.models.TreeNodeModel;
import com.lykkex.LykkeWallet.gui.internal.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author e.fetskovich on 4/23/18.
 */

public abstract class MultitypeTreeExpandableRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder<? extends ViewGroup>> {

    private List<TreeNodeModel> allNodes = new ArrayList<>();
    private List<TreeNodeModel> displayNodes;

    protected abstract ViewGroup createParentView(ViewGroup parent, int viewType);

    protected abstract ViewGroup createChildView(ViewGroup parent, int viewType);

    protected abstract void onBindParentViewHolder(ViewHolder<? extends ViewGroup> holder, ListItemModel data, int childCount, boolean isExpand);

    protected abstract void onBindChildViewHolder(ViewHolder<? extends ViewGroup> holder, ListItemModel data);

    @Override
    public ViewHolder<? extends ViewGroup> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < Hierarchy.UNDEFINED.getCode()) {
            return new ViewHolder<>(createChildView(parent, viewType));
        } else if (viewType > Hierarchy.UNDEFINED.getCode()) {
            return new ViewHolder<>(createParentView(parent, viewType));
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder<? extends ViewGroup> holder, int position) {
        int viewType = holder.getItemViewType();
        if (viewType < Hierarchy.UNDEFINED.getCode()) {
            // Child
            onBindChildViewHolder(holder, displayNodes.get(position).getData());
        } else if (viewType > Hierarchy.UNDEFINED.getCode()) {
            // Parent
            TreeNodeModel model = displayNodes.get(position);
            onBindParentViewHolder(holder, model.getData(), model.getChildCount(), model.isExpand());
        } else {
            throw new IllegalStateException();
        }
    }

    protected void setNodes(List<TreeNodeModel> nodes) {
        allNodes = nodes;

        calculateDisplayNodes();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return displayNodes != null ? displayNodes.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return displayNodes.get(position).getType();
    }

    private void calculateDisplayNodes() {
        displayNodes = new ArrayList<>();
        addNodesRecursively(allNodes);
    }

    public void addNodesRecursively(List<TreeNodeModel> list) {
        for (TreeNodeModel node : list) {
            TreeNodeModel.ExpandType type = node.getExpandType();

            if (type == TreeNodeModel.ExpandType.EXPAND_TO_BOTTOM) {
                displayNodes.add(node);
                addChildren(node);
            } else {
                addChildren(node);
                displayNodes.add(node);
            }
        }
    }

    private void addChildren(TreeNodeModel node) {
        if (node.getVisibleChildCount() > 0) {
            addNodesRecursively(node.getChildList());
        }
    }

    /**
     * @param position
     * @return true if expanded, false if collapsed
     */
    protected boolean toggle(int position) {
        return toggle(position, false);
    }

    /**
     * @param position
     * @param collapsePrevious
     * @return true if expanded, false if collapsed
     */
    protected boolean toggle(int position, boolean collapsePrevious) {
        TreeNodeModel treeNode = displayNodes.get(position);
        TreeNodeModel.ExpandType type = treeNode.getExpandType();
        if (treeNode.isExpand()) {
            return collapseChildren(treeNode, type, position);
        } else {
            return expandChildren(treeNode, type, position, collapsePrevious);
        }
    }

    private boolean expandChildren(TreeNodeModel treeNode, TreeNodeModel.ExpandType type, int position, boolean collapsePrevious) {
        if (collapsePrevious && collapsePrevious(type)) {
            position = displayNodes.indexOf(treeNode);
        }
        if (treeNode.expand()) {
            calculateDisplayNodes();
            notifyItemChanged(position);
            int childrenCount = treeNode.getChildCountWithExpandedTypes();
            if (type == TreeNodeModel.ExpandType.EXPAND_TO_BOTTOM) {
                notifyItemRangeInserted(position + 1, childrenCount);
            } else {
                notifyItemRangeInserted(position + childrenCount, childrenCount);
            }
        }
        return true;
    }

    private boolean collapseChildren(TreeNodeModel treeNode, TreeNodeModel.ExpandType type, int position) {
        if (treeNode.collapse()) {
           collapseItems(treeNode, type, position);
        }
        return false;
    }

    private boolean collapsePrevious(TreeNodeModel.ExpandType type) {
        for (TreeNodeModel node : displayNodes) {
            if (node.isExpand()) {
                if (node.collapse()) {
                    int position = displayNodes.indexOf(node);
                    collapseItems(node, type, position);
                    return true;
                }
            }
        }
        return false;
    }

    private void collapseItems(TreeNodeModel node, TreeNodeModel.ExpandType type, int position){
        calculateDisplayNodes();
        notifyItemChanged(position);
        int childrenCount = node.getChildCountWithExpandedTypes();
        if (type == TreeNodeModel.ExpandType.EXPAND_TO_BOTTOM) {
            notifyItemRangeRemoved(position + 1, childrenCount);
        } else {
            notifyItemRangeRemoved(position + childrenCount, childrenCount);
        }
    }

    public ListItemModel getData(int position) {
        return displayNodes.get(position).getData();
    }


}
