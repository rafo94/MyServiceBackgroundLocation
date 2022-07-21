package com.rafo.myservicelocation.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rafo.myservicelocation.R
import com.rafo.myservicelocation.databinding.FragmentHistoryBinding
import com.rafo.myservicelocation.ui.map.MapsFragment
import java.util.*

class HistoryFragment : Fragment() {

    private var binding: FragmentHistoryBinding? = null

    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentHistoryBinding.inflate(inflater, container, false)
        binding = view
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = HistoryAdapter { id ->
            openHistoryFragment(id)
        }
        binding?.let {
            with(it) { historyList.adapter = adapter }
        }
        viewModel.getAllData.observe(viewLifecycleOwner) { locationList ->
            if (locationList.isNotEmpty()) {
                adapter.submitList(locationList)
            }
        }
    }

    private fun openHistoryFragment(id: UUID) {
        activity?.let { act ->
            act.supportFragmentManager.beginTransaction().apply {
                replace(R.id.container, MapsFragment.newInstance(id))
                addToBackStack(null)
            }.run { commit() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}