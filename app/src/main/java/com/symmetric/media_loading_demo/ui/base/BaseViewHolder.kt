
package com.symmetric.media_loading_demo.ui.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: ViewDataBinding) : RecyclerView.ViewHolder(itemView.root) {

    abstract fun onBind(position: Int)
}
