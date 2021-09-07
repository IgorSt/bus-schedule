package com.example.bbusschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bbusschedule.adapter.BusStopAdapter
import com.example.bbusschedule.databinding.FullScheduleFragmentBinding
import com.example.bbusschedule.viewmodels.BusScheduleViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FullScheduleFragment : Fragment() {

    private var _binding: FullScheduleFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: BusScheduleViewModel by activityViewModels {
        BusScheduleViewModel.BusScheduleViewModelFactory(
            (activity?.application as BusScheduleApplication).database.scheduleDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FullScheduleFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val busStopAdapter = BusStopAdapter {
            val action = FullScheduleFragmentDirections
                .actionFullScheduleFragmentToStopScheduleFragment(
                    stopName = it.stopName
                )
            view.findNavController().navigate(action)
        }

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = busStopAdapter

        lifecycle.coroutineScope.launch {
            viewModel.fullSchedule().collect(){
                busStopAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}