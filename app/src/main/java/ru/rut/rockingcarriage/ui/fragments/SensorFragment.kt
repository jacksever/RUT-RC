package ru.rut.rockingcarriage.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.rut.rockingcarriage.databinding.FragmentMainBinding
import ru.rut.rockingcarriage.ui.viewmodels.SensorViewModel

class SensorFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val sensorViewModel: SensorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            viewModel = sensorViewModel
            stateData.viewModel = sensorViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onResume() {
        super.onResume()
        sensorViewModel.startRecording()
    }

    override fun onPause() {
        super.onPause()
        sensorViewModel.stopRecording()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}