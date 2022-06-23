package mk.nasa.services

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import mk.nasa.R
import mk.nasa.api.NasaFetcher
import mk.nasa.framework.getDatePreference
import java.sql.Time
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

private const val JOB_ID = 1
private const val NUMBER_OF_DAYS = 7

class NasaService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        NasaFetcher(this).fetchApodData(
            LocalDate.now().minusDays(NUMBER_OF_DAYS.toLong()),
            LocalDate.now()
        )
        NasaFetcher(this).fetchNeoData(
            LocalDate.now(),
            LocalDate.now().plusDays(NUMBER_OF_DAYS.toLong())
        )
    }

    companion object {
        fun enqueue(context: Context, intent: Intent) {
            enqueueWork(context, NasaService::class.java, JOB_ID, intent)
        }
    }
}