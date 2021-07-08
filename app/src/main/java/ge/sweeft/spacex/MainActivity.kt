package ge.sweeft.spacex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import ge.narogava.test.data.ShipView
import ge.sweeft.spacex.databinding.ActivityMainBinding
import ge.sweeft.spacex.viewmodel.ShipViewModel
import dagger.hilt.android.AndroidEntryPoint
import ge.narogava.test.data.Mission
import ge.narogava.test.data.Position

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: SliderAdapter

    private val sliderHandler = Handler()
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
//        setViewPager()
        shipViewModel.response.observe(this, Observer {
                setAdapter(it as MutableList<ShipView>)
        })

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

    private fun init() {
        shipViewModel.ships.value = ShipView(
            1249191,
            true,
            null,
            15252765,
            null,
            "Port of Los Angeles",
            "https://i.imgur.com/woCxpkj.jpg",
            7434016,
            listOf(
                Mission(
                    "COTS 1",
                    7
                ),
                Mission(
                    "COTS 2",
                    8
                )
            ),
            367020820,
            Position(
                30.52731,
                -88.10171
            ),
            listOf(
                "Support Ship",
                "Barge Tug"
            ),
            "AMERICANCHAMPION",
            null,
            "American Champion",
            "Tug",
            0,
            "Stopped",
            null,
            "https://www.marinetraffic.com/en/ais/details/ships/shipid:434663/vessel:AMERICAN%20CHAMPION",
            266712,
            588000,
            1976
        )
    }


    private val slideRunnable = Runnable {
        binding.viewPagerImageSlider.currentItem =
            binding.viewPagerImageSlider.currentItem + 1
    }

    private fun setAdapter(body: MutableList<ShipView>) {
        this.adapter = SliderAdapter(body, binding.viewPagerImageSlider)
    }

    private fun setViewPager() { }

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