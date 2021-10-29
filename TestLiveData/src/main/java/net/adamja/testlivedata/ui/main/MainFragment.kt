package net.adamja.testlivedata.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import net.adamja.testlivedata.R
import net.adamja.testlivedata.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // ViewModel
        binding.message1.text = "Message 1"


        // LiveData #1
        val countObserver = Observer<Test> { test ->
            binding.message2.text = "Test count 1: ${test.count}"
        }

        viewModel.getTest().observe(viewLifecycleOwner, countObserver)


        // LiveData #2
        viewModel.getTest().observe(
            viewLifecycleOwner,
            { test ->
                binding.message3.text = "Test count 2: ${test.count}"
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}