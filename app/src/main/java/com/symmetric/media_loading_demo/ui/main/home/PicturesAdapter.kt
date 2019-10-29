package com.symmetric.media_loading_demo.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.symmetric.media_loading_demo.data.model.db.PictureDb
import com.symmetric.media_loading_demo.databinding.ItemBinding
import com.symmetric.media_loading_demo.ui.base.BaseViewHolder


class PicturesAdapter() : PagedListAdapter<PictureDb, BaseViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        return ViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class ViewHolder(private val mBinding: ItemBinding) : BaseViewHolder(mBinding) {

        private lateinit var mViewModel: PictureViewModel


        override fun onBind(position: Int) {
            if (getItem(position) != null) {
                mViewModel = PictureViewModel(getItem(position)!!)
                mBinding.viewModel = mViewModel

            }

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
            override fun areItemsTheSame(
                oldItem: PictureDb,
                newItem: PictureDb
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PictureDb,
                newItem: PictureDb
            ) = oldItem == newItem
        }
    }
}