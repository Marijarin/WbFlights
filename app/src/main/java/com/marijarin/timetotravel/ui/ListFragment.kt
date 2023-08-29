package com.marijarin.timetotravel.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.marijarin.timetotravel.R
import com.marijarin.timetotravel.adapter.FlightsAdapter
import com.marijarin.timetotravel.adapter.OnFlightListener
import com.marijarin.timetotravel.databinding.FragmentListBinding
import com.marijarin.timetotravel.dto.Flight
import com.marijarin.timetotravel.model.ModelState
import com.marijarin.timetotravel.viewModel.FlightViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment: Fragment() {
    private val viewModel: FlightViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentListBinding.inflate(inflater, container, false)
        val adapter = FlightsAdapter(object : OnFlightListener{
            override fun goToFlight(flight: Flight) {
                findNavController().navigate(R.id.action_listFragment_to_detailedFragment,
                    bundleOf("flight" to Gson().toJson(flight, Flight::class.java)))
            }

            override fun like(flight: Flight) {
                viewModel.likeByToken(flight.searchToken)
            }
        })
        binding.list.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.flights.collectLatest {
                    adapter.submitList(it)
                }
            }
        }
        viewModel.dataState.observe(viewLifecycleOwner) { state ->
           when(state){
               ModelState.Error -> {
                   Snackbar.make(binding.root, R.string.error_loading, Snackbar.LENGTH_INDEFINITE)
                       .setAction(R.string.retry_loading) { viewModel.loadFlights() }
                       .show()
               }
               ModelState.Loading -> {
                   binding.progress.visibility = View.VISIBLE
                   binding.list.visibility = View.GONE
               }
               ModelState.Success -> {
                   binding.progress.visibility = View.GONE
                   binding.list.visibility = View.VISIBLE
               }
               else -> Unit
           }

        }

        return binding.root
    }
}