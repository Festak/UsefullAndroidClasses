package com.lykkex.LykkeWallet.gui.utils.filter

import android.text.TextUtils
import android.widget.Filter
import com.lykkex.LykkeWallet.gui.adapters.filter.FilteredList

/**
 * @author e.fetskovich on 7/27/18.
 */
class BaseFilter<TModel>(private val filterListener: OnFilterCompleteListener<TModel>,
                         var filterStrategy: FilterStrategy<TModel>) : Filter() {

    private var filteredList: FilteredList<TModel> = FilteredList(ArrayList(), LinkedHashMap())
    var filterableMap: Map<String, List<TModel>> = LinkedHashMap()


    override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
        val tempMap: LinkedHashMap<Int, String> = LinkedHashMap()
        val list = ArrayList<TModel>()
        for (entry in filterableMap) {
            if (!TextUtils.isEmpty(constraint)) {
                val tempList = ArrayList<TModel>()

                for (listObject in entry.value) {
                    if (filterStrategy.shouldAddItem(listObject, constraint.toString())) {
                        tempList.add(listObject)
                    }
                }

                tempMap[list.size] = entry.key
                list.addAll(tempList)
            } else {
                tempMap[list.size] = entry.key
                list.addAll(entry.value)
                break
            }
        }

        val filterResults = Filter.FilterResults()
        filteredList = FilteredList(list, tempMap)
        filterResults.count = filteredList.getSize()
        filterResults.values = filteredList

        return filterResults
    }


    override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults?) {
        val filteredMap = if (results?.values != null) results.values as FilteredList<TModel> else FilteredList(ArrayList(), LinkedHashMap())
        filterListener.onFilterComplete(filteredMap)
    }

    interface OnFilterCompleteListener<TModel> {
        fun onFilterComplete(filteredList: FilteredList<TModel>)
    }
}