package ge.sweeft.spacex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import ge.narogava.test.data.ShipView
import ge.sweeft.spacex.data.RetrofitService
import ge.sweeft.spacex.data.ShipApi
import ge.sweeft.spacex.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: SliderAdapter
    private lateinit var viewPagerItems: MutableList<ShipView>
    private val sliderHandler = Handler()
    private var slideStart = false
    private var speedForUi = 1
    private var speed: Long = 3200


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                speed = 3200
            }
            binding.speedChange.text = String.format("%dX", speedForUi)
        }
    }


    private val slideRunnable = Runnable {
//        if (binding.viewPagerImageSlider.currentItem == viewPagerItems.size - 1) {
//            binding.viewPagerImageSlider.currentItem = 0
//        } else {
        binding.viewPagerImageSlider.currentItem =
            binding.viewPagerImageSlider.currentItem + 1
//        }
    }

    private fun setAdapter(body: MutableList<ShipView>) {
        this.adapter = SliderAdapter(body, binding.viewPagerImageSlider,)
    }

    private fun setViewPager() {
        val destinationService = RetrofitService.createService(ShipApi::class.java)
        val requestCall = destinationService.getAllShips()
        requestCall.enqueue(object : Callback<List<ShipView>> {
            override fun onResponse(
                call: Call<List<ShipView>>,
                response: Response<List<ShipView>>
            ) {
                Log.d("Response", "onResponse: ${response.body()}")

                if (response.isSuccessful) {
                    val ships = response.body()
                    if (ships != null) {
                        Log.d("Response", "countrylist size : ${ships.size}")
                    }
                    response.body()?.let {
                        viewPagerItems = it as MutableList<ShipView>
                        setAdapter(it)
                    }
                    binding.viewPagerImageSlider.adapter = adapter

                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Something went wrong ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<ShipView>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Something went wrong $t", Toast.LENGTH_SHORT)
                    .show()
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
        sliderHandler.postDelayed(slideRunnable, 1000)
    }

}