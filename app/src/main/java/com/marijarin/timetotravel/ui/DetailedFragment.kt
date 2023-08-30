package com.marijarin.timetotravel.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.marijarin.timetotravel.R
import com.marijarin.timetotravel.databinding.FragmentDetailedBinding
import com.marijarin.timetotravel.dto.Flight
import com.marijarin.timetotravel.util.Utils.toDate
import com.marijarin.timetotravel.viewModel.FlightViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedFragment: Fragment() {
    private val viewModel: FlightViewModel by activityViewModels()
    private var fragmentBinding: FragmentDetailedBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailedBinding.inflate(inflater, container, false)
        fragmentBinding = binding
        val json = arguments?.getString("flight")
        val flight = Gson().fromJson(json, Flight::class.java)
        binding.oneFlight.apply {
            cityFrom.text = flight.startCity
            cityTo.text = flight.endCity
            datesText.text = getString(R.string.dates, toDate(flight.startDate), toDate(flight.endDate) )
            price.text = flight.price.toString()
            like.isChecked = flight.likedByMe
            like.setOnClickListener {
                viewModel.likeByToken(flight.searchToken)
            }
        }
        binding.backButton.setOnClickListener { findNavController().navigateUp() }
        return binding.root
    }
    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}