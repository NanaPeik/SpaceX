package ge.sweeft.spacex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.lifecycle.*
import androidx.viewpager2.widget.ViewPager2
import ge.sweeft.spacex.data.ShipView
import ge.sweeft.spacex.databinding.ActivityMainBinding
import ge.sweeft.spacex.viewmodel.ShipViewModel
import dagger.hilt.android.AndroidEntryPoint
import ge.sweeft.spacex.adapter.SliderAdapter
import ge.sweeft.spacex.data.Position
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: SliderAdapter
    private var coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    private var slideStart = false
    private var speedForUi = 1
    private var speed: Long = 3200
    private val maxSpeed: Long = 3200
    private val shipViewModel: ShipViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        shipViewModel.response.observe(this, Observer {
            setAdapter(it as MutableList<ShipView>)
        })

        binding.viewPagerImageSlider.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (slideStart) {
                        coroutineScope.coroutineContext.cancelChildren()

                        coroutineScope.launch {
                            slide(speed)
                        }
                    }
                }
            }
        )

        binding.slideOnOff.setOnClickListener {
            slideStart = !slideStart
            if (slideStart) {
                binding.slideOnOff.text = getString(R.string.stop)
            } else {
                binding.slideOnOff.text = getString(R.string.start)
            }
            coroutineScope.coroutineContext.cancelChildren()
            if (slideStart) {
                coroutineScope.launch {
                    slide(speed)
                }
            }
        }
        binding.speedChange.setOnClickListener {
            if (speedForUi < 5) {
                speedForUi += 1
                speed /= 2
            } else {
                speedForUi = 1
                speed = maxSpeed
            }
            binding.speedChange.text = String.format("%dX", speedForUi)
            if (slideStart) {
                coroutineScope.launch {
                    slide(speed)
                }
            }
        }
    }

    private fun init() {
        shipViewModel.ships.value = ShipView(
            0,
            true,
            null,
            0,
            null,
            "",
            "",
            0,
            listOf(),
            0,
            Position(0.0, 0.0),
            listOf(),
            "",
            null,
            "",
            "",
            0,
            "",
            null,
            "",
            0,
            0,
            0
        )
    }

    private suspend fun slide(speed: Long) {
        withContext(Dispatchers.Main) {
            delay(speed)
            binding.viewPagerImageSlider.currentItem =
                binding.viewPagerImageSlider.currentItem + 1
        }
    }

    private fun setAdapter(shipList: MutableList<ShipView>) {
        this.adapter = SliderAdapter(shipList, binding.viewPagerImageSlider)
    }

    override fun onPause() {
        super.onPause()
        coroutineScope.coroutineContext.cancelChildren()
    }

    override fun onResume() {
        super.onResume()
        if (slideStart)
            coroutineScope.launch {
                slide(speed)
            }
    }

}