package ua.ukrop.fragmentcounter

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import ua.ukrop.fragmentcounter.ui.main.KEY_INTENT_COUNT
import ua.ukrop.fragmentcounter.ui.main.MainViewModel
import ua.ukrop.fragmentcounter.ui.main.PagesFragmentStateAdapter

private const val KEY_ITEM_TEXT = "androidx.viewpager2.integration.testapp.KEY_ITEM_TEXT"
private const val KEY_CLICK_COUNT = "androidx.viewpager2.integration.testapp.KEY_CLICK_COUNT"
private const val TAG = "TAG_CounterActivity"
private const val TAG2 = "TAG_callbacks"

class CounterActivity : FragmentActivity() {
    private lateinit var viewPager: ViewPager2
    private val itemsViewModel: MainViewModel by viewModels()

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.let {
            val count = intent.getIntExtra(KEY_INTENT_COUNT, 1)
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
                }
                newPagesList.size == oldListSize -> {
                }
            }
        }
    }
}

fun ViewPager2.openFragmentFromIntent(count: Int, itemsViewModel: MainViewModel) {
    val position = count - 1
    if(this.adapter!!.itemCount < count){
        itemsViewModel.setList(count)
    }
    this.setCurrentItem(position, false)
}
