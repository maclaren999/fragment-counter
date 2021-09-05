package ua.ukrop.fragmentcounter

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.viewModels
//import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import ua.ukrop.fragmentcounter.ui.main.MainViewModel

/**
 * Shows how to use notifyDataSetChanged with [ViewPager2]
 */
abstract class CounterBaseActivity : FragmentActivity() {

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)

        viewPager = findViewById(R.id.pager)
        viewPager.adapter = createViewPagerAdapter()

        itemsViewModel.fragmentCounter.observe(this){
            viewPager.adapter!!.notifyDataSetChanged()
        }
//        buttonGoTo.setOnClickListener {
//            viewPager.setCurrentItem(itemSpinner.selectedItemPosition, true)
//        }

//        fun changeDataSet(performChanges: () -> Unit) {
//            if (checkboxDiffUtil.isChecked) {
//                /** using [DiffUtil] */
//                val idsOld = items.createIdSnapshot()
//                performChanges()
//                val idsNew = items.createIdSnapshot()
//                DiffUtil.calculateDiff(object : DiffUtil.Callback() {
//                    override fun getOldListSize(): Int = idsOld.size
//                    override fun getNewListSize(): Int = idsNew.size
//
//                    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
//                        idsOld[oldItemPosition] == idsNew[newItemPosition]
//
//                    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
//                        areItemsTheSame(oldItemPosition, newItemPosition)
//                }, true).dispatchUpdatesTo(viewPager.adapter!!)
//            } else {
//                /** without [DiffUtil] */
//                val oldPosition = viewPager.currentItem
//                val currentItemId = items.itemId(oldPosition)
//                performChanges()
//                viewPager.adapter!!.notifyDataSetChanged()
//                if (items.contains(currentItemId)) {
//                    val newPosition =
//                        (0 until items.size).indexOfFirst { items.itemId(it) == currentItemId }
//                    viewPager.setCurrentItem(newPosition, false)
//                }
//            }
//        }
    }


//    fun changeDataSet(performChanges: () -> Unit) {
//        val oldPosition = viewPager.currentItem
//        val currentItemId = itemsViewModel.itemId(oldPosition)
//        performChanges()
//        viewPager.adapter!!.notifyDataSetChanged()
//        if (itemsViewModel.contains(currentItemId)) {
//            val newPosition =
//                (0 until itemsViewModel.size).indexOfFirst { itemsViewModel.itemId(it) == currentItemId }
//            viewPager.setCurrentItem(newPosition, false)
//        }
//    }

    abstract fun createViewPagerAdapter(): RecyclerView.Adapter<*>

    val itemsViewModel: MainViewModel by viewModels()


}
