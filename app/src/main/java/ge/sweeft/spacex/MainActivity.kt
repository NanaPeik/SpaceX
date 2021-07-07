package ge.sweeft.spacex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import ge.narogava.test.data.ShipView
import ge.sweeft.spacex.databinding.ActivityMainBinding
import ge.sweeft.spacex.viewmodel.ShipViewModel
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: SliderAdapter

    //    private lateinit var viewPagerItems: MutableList<ShipView>
    private val sliderHandler = Handler()
    private var slideStart = false
    private var speedForUi = 1
    private var speed: Long = 3200
    private val maxSpeed: Long = 3200
    private lateinit var shipViewModel: ShipViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shipViewModel = ViewModelProvider(this).get(ShipViewModel::class.java)
        setViewPager()

        binding.viewPagerImageSlider.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (slideStart) {
                        sliderHandler.removeCallbacks(slideRunnable)
                        sliderHandler.postDelayed(slideRunnable, speed)
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
            sliderHandler.removeCallbacks(slideRunnable)
            if (slideStart) {

                sliderHandler.postDelayed(slideRunnable, speed)
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
        }
    }


    private val slideRunnable = Runnable {
        binding.viewPagerImageSlider.currentItem =
            binding.viewPagerImageSlider.currentItem + 1
    }

    private fun setAdapter(body: MutableList<ShipView>) {
        this.adapter = SliderAdapter(body, binding.viewPagerImageSlider)
    }

    private fun setViewPager() {

        shipViewModel.response.observe(this, Observer {
            if (it != null) {
                setAdapter(it as MutableList<ShipView>)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(slideRunnable)
    }

    override fun onResume() {
        super.onResume()
        if (slideStart)
            sliderHandler.postDelayed(slideRunnable, maxSpeed)
    }

}