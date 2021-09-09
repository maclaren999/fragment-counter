package ua.ukrop.fragmentcounter

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import ua.ukrop.fragmentcounter.ui.main.KEY_INTENT_COUNT
import ua.ukrop.fragmentcounter.ui.main.MainViewModel
import ua.ukrop.fragmentcounter.ui.main.PagesFragmentStateAdapter

private const val TAG = "TAG_CounterActivity"
private const val TAG2 = "TAG_callbacks"

class CounterActivity : FragmentActivity() {
    private lateinit var viewPager: ViewPager2
    private val itemsViewModel: MainViewModel by viewModels()

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            val count = intent.getIntExtra(KEY_INTENT_COUNT, 1).also { Log.d(TAG, "count = $it") }
            viewPager.openFragmentFromIntent(count, itemsViewModel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)

        viewPager = findViewById(R.id.pager)
        if (viewPager.adapter == null) {
            viewPager.adapter = PagesFragmentStateAdapter(this)
        }

        val notificationManager =
            ContextCompat.getSystemService(
                this,
                NotificationManager::class.java
            ) as NotificationManager

        var oldListSize = 1
        itemsViewModel.pagesLiveData.observe(this) { newPagesList ->
            (viewPager.adapter as PagesFragmentStateAdapter).updateList(newPagesList)
            when {
                newPagesList.size > oldListSize -> {
                    oldListSize = newPagesList.size
                    viewPager.adapter?.notifyItemInserted(newPagesList.size - 1)
                    viewPager.setCurrentItem(newPagesList.size - 1, true)
                }
                newPagesList.size < oldListSize -> {
                    oldListSize = newPagesList.size
                    viewPager.adapter?.notifyItemRemoved(newPagesList.size)
                    notificationManager.cancel(newPagesList.size + 1)
                }
                newPagesList.size == oldListSize -> {
                }
            }
        }
    }
}

fun ViewPager2.openFragmentFromIntent(count: Int, itemsViewModel: MainViewModel) {
    val position = count - 1
    val itemCount = this.adapter!!.itemCount
    Log.d(TAG, "position = $position; items = $itemCount")
    this.setCurrentItem(position, true)
}
