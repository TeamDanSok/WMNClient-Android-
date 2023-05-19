package com.example.wmn

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wmn.databinding.RowBinding

class ListAdapter(val items: Array<String>)  : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    interface OnItemClickListener {
    }

    var itemClickListener:OnItemClickListener?= null

    inner class ViewHolder (val binding: RowBinding):RecyclerView.ViewHolder(binding.root){
        init {
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textView.text = items[position]
    }
}