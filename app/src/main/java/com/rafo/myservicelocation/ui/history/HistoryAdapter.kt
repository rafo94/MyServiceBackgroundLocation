package com.rafo.myservicelocation.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rafo.myservicelocation.data.db.LocationEntity
import com.rafo.myservicelocation.databinding.HistoryItemBinding
import com.rafo.myservicelocation.utils.dateToString
import java.util.*

/**
 * Created by Rafik Gasparyan on 07/20/22
 */
class HistoryAdapter(private val itemClickListener: (UUID) -> Unit) :
    ListAdapter<LocationEntity, HistoryAdapter.HistoryHolder>(DiffCallback()) {

    inner class HistoryHolder(private val binding: HistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(locationEntity: LocationEntity) {
            with(binding) {
                itemDate.text = locationEntity.date.dateToString("dd-MMM-yy hh:mm")
                root.setOnClickListener {
                    itemClickListener.invoke(locationEntity.id)
                }
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<LocationEntity>() {
        override fun areItemsTheSame(oldItem: LocationEntity, newItem: LocationEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: LocationEntity, newItem: LocationEntity) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = HistoryItemBinding.inflate(inflater, parent, false)
        return HistoryHolder(binding = view)
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        holder.bind(getItem(position))
    }
}