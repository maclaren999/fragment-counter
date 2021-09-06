package ua.ukrop.fragmentcounter.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import ua.ukrop.fragmentcounter.CounterActivity
import ua.ukrop.fragmentcounter.R
import ua.ukrop.fragmentcounter.databinding.MainFragmentBinding

private const val KEY_FRAGMENT_NUMBER = "KEY_FRAGMENT_NUMBER"
private const val TAG = "TAG_MainFragment"
const val KEY_INTENT_COUNT = "KEY_COUNT"

class MainFragment : Fragment() {

    private val number: Int by lazy { requireArguments().getInt(KEY_NUMBER) }


    companion object {
        private const val KEY_NUMBER = "number"
        private const val CHANNEL_ID = "chID"
        private const val CHANNEL_NAME = "chName"

        fun newInstance(number: Int): MainFragment {
            return MainFragment().also {
                it.arguments = bundleOf(KEY_NUMBER to number)
            }
        }
    }

    private lateinit var notificationManager: NotificationManager
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
        binding.buttonCreateNotification.setOnClickListener {
            val notification = createNotification()
            with(NotificationManagerCompat.from(requireContext())) {
                val currentNotificationId = number
                notify(currentNotificationId, notification)
            }
        }

        itemsViewModel.pagesLiveData.observe(requireActivity()) { fragmentList ->
            if (isFirstFragment() && fragmentList.size <= 1)
                binding.buttonMinus.visibility = View.GONE
            else binding.buttonMinus.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
    }

    fun createNotification() =
        NotificationCompat.Builder(requireContext(), CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_baseline_add_24)
            setContentTitle(getString(R.string.content_title))
            setContentText(notificationText())
            setPriority(NotificationCompat.PRIORITY_HIGH)
            setVibrate(longArrayOf(0, 250, 250, 250))
            setContentIntent(createPendingIntent())
            setAutoCancel(true)
        }.build()

    private fun createPendingIntent(): PendingIntent? {
        val intent = Intent(requireContext(), CounterActivity::class.java).apply {
            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            putExtra(KEY_INTENT_COUNT, number)
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        return pendingIntent
    }

    fun notificationText() = getString(R.string.Notification) + number.toString()

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val descriptionText = getString(R.string.channel_desctioption)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                enableVibration(true)
            }
            notificationManager =
                getSystemService(
                    requireContext(),
                    NotificationManager::class.java
                ) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    fun isFirstFragment(): Boolean = number == 1
}
