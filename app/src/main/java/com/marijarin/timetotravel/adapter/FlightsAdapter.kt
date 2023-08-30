package com.marijarin.timetotravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marijarin.timetotravel.R
import com.marijarin.timetotravel.databinding.ItemFlightBinding
import com.marijarin.timetotravel.dto.Flight
import com.marijarin.timetotravel.util.Utils.toDate

interface OnFlightListener {
    fun goToFlight(flight: Flight) {}
    fun like(flight: Flight) {}
}

class FlightsAdapter(
    private val onFlightListener: OnFlightListener
) : ListAdapter<Flight, FlightViewHolder>(FlightDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val binding =
            ItemFlightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlightViewHolder(binding, onFlightListener)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val flight = getItem(position)
        holder.bind(flight)
    }

}

class FlightViewHolder(
    private val binding: ItemFlightBinding,
    private val onFlightListener: OnFlightListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(flight: Flight) {
        binding.apply {
            cityFrom.text = flight.startCity
            cityTo.text = flight.endCity
            datesText.text = itemView.context.getString(R.string.dates, toDate(flight.startDate), toDate(flight.endDate) )
            price.text = flight.price.toString()
            like.isChecked = flight.likedByMe
            root.setOnClickListener {
                onFlightListener.goToFlight(flight)
            }
            like.setOnClickListener {
                onFlightListener.like(flight)
            }
        }
    }
}

class FlightDiffCallBack : DiffUtil.ItemCallback<Flight>() {
    override fun areItemsTheSame(oldItem: Flight, newItem: Flight): Boolean {
        return oldItem.searchToken == newItem.searchToken
    }

    override fun areContentsTheSame(oldItem: Flight, newItem: Flight): Boolean {
        return oldItem == newItem
    }
}