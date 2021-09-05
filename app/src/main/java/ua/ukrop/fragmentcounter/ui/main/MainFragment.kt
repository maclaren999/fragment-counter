package ua.ukrop.fragmentcounter.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.ukrop.fragmentcounter.databinding.MainFragmentBinding

private const val KEY_FRAGMENT_NUMBER = "KEY_FRAGMENT_NUMBER"
private const val TAG = "TAG_MainFragment"

class MainFragment(number: Int?) : Fragment() {
    val number = number ?: 0
        .also { Log.e(TAG, "Receiving NULL in MainFragment constructor!") }

    companion object {
        fun newInstance(number: Int?) = MainFragment(number).also {
            Log.d(TAG, "newInstance(number = $number)")
        }
    }

    private lateinit var binding: MainFragmentBinding
    private val itemsViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.textViewCounter.text =
            number.toString()//savedInstanceState?.getInt(KEY_FRAGMENT_NUMBER).toString()
        binding.buttonMinus.setOnClickListener {
            itemsViewModel.removeLastItem()
        }
        binding.buttonPlus.setOnClickListener {
            itemsViewModel.addNewItem()
        }
        itemsViewModel.pagesLiveData.observe(requireActivity()) { fragmentList ->
            if (isFirstFragment() && fragmentList.size <= 1)
                binding.buttonMinus.visibility = View.GONE
            else binding.buttonMinus.visibility = View.VISIBLE
        }
    }

    fun isFirstFragment(): Boolean = number == 1
}
