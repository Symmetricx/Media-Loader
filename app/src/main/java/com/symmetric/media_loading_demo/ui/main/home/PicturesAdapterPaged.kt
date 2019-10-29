/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.symmetric.media_loading_demo.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.symmetric.media_loading_demo.data.model.db.PictureDb
import com.symmetric.media_loading_demo.databinding.ItemBinding
import com.symmetric.media_loading_demo.ui.base.BaseViewHolder


class PicturesAdapterPaged(val list: ArrayList<PictureDb>) : PagedListAdapter<PictureDb, BaseViewHolder>(DIFF_CALLBACK) {

    private var mListener: ItemAdapterListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

            return ViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun addItems(items: List<PictureDb>){
        this.list.addAll(items)
        notifyDataSetChanged()
    }

    public interface ItemAdapterListener {
    }

    public fun setListener(listener: ItemAdapterListener) {
        mListener = listener
    }


    inner class ViewHolder(private val mBinding: ItemBinding) : BaseViewHolder(mBinding) {

        private lateinit var mViewModel: PictureViewModel


        override fun onBind(position: Int) {
            val item = list[position]
            mViewModel = PictureViewModel(item)
            mBinding.viewModel = mViewModel

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<PictureDb>() {
            // Item details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(oldItem: PictureDb,
                                         newItem: PictureDb) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PictureDb,
                                            newItem: PictureDb) = oldItem == newItem
        }
    }
}