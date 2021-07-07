package ge.sweeft.spacex

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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: SliderAdapter
    private lateinit var viewPagerItems: MutableList<ShipView>
    private val sliderHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViewPager()

        binding.viewPagerImageSlider.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    sliderHandler.removeCallbacks(slideRunnable)
                    sliderHandler.postDelayed(slideRunnable, 3000)
                }
            }
        )
    }

    private val slideRunnable = Runnable {
        binding.viewPagerImageSlider.currentItem =
            binding.viewPagerImageSlider.currentItem + 1
    }

    private fun setAdapter(body: MutableList<ShipView>) {
        this.adapter = SliderAdapter(body, binding.viewPagerImageSlider)
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
}