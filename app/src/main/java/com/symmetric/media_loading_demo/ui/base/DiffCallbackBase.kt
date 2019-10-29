package com.symmetric.media_loading_demo.ui.base

import androidx.recyclerview.widget.DiffUtil

class DiffCallbackBase<T : DiffCallbackBase.RequiredMethods>(
    private val dataSet1: List<T>,
    private val dataSet2: List<T>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return dataSet1.size
    }

    override fun getNewListSize(): Int {
        return dataSet2.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return dataSet2[oldItemPosition].id == dataSet1[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return dataSet1[oldItemPosition] == dataSet2[newItemPosition]
    }

    interface RequiredMethods {
        val id: Long
    }

    companion object {

        private val TAG = "diff_callback_base"
    }
}
