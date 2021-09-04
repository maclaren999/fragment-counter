package ua.ukrop.fragmentcounter.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.ukrop.fragmentcounter.R
import ua.ukrop.fragmentcounter.databinding.MainFragmentBinding

private const val KEY_FRAGMENT_NUMBER = "KEY_FRAGMENT_NUMBER"

class MainFragment : Fragment() {

    companion object {
        fun newInstance(number: Int) = MainFragment().apply {
            arguments = Bundle(1).apply {
                putInt(KEY_FRAGMENT_NUMBER, number)
            }
        }
    }

    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.textViewCounter.text = savedInstanceState?.getInt(KEY_FRAGMENT_NUMBER).toString()
        binding.buttonMinus.setOnClickListener{
            TODO()
        }
        binding.buttonPlus.setOnClickListener{
            TODO()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }


}
