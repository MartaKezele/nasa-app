package mk.nasa.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import mk.nasa.R
import mk.nasa.databinding.ActivitySplashBinding
import mk.nasa.framework.*
import mk.nasa.services.NasaService
import java.time.LocalDate

private const val DELAY_SHORT = 3000L
private const val DELAY_LONG = 5000L

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startAnimations()
        redirect()
        println("create")
    }

    private fun startAnimations() {
        binding.ivLogo.startAnimation(R.anim.slide_in_from_left)
    }

    private fun redirect() {
        val lastDataImportDate = getDatePreference(getString(R.string.last_data_import_date))
        if (lastDataImportDate != null && lastDataImportDate == LocalDate.now()) {
            startAppWithDelay(this, DELAY_SHORT)
        } else {
            setBooleanPreference(getString(R.string.apod_data_imported), false)
            setBooleanPreference(getString(R.string.neo_data_imported), false)
            if (isOnline()) {
                Intent(this, NasaService::class.java).apply {
                    NasaService.enqueue(
                        this@SplashActivity,
                        this
                    )
                }
            } else {
                Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT)
                    .show()
                Toast.makeText(this, getString(R.string.app_will_close_soon), Toast.LENGTH_SHORT)
                    .show()
                callDelayed(DELAY_LONG) { finish() }
            }
        }
    }
}