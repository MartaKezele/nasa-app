package mk.nasa.api

import android.content.ContentValues
import android.content.Context
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mk.nasa.R
import mk.nasa.contentproviders.APOD_PROVIDER_URI
import mk.nasa.contentproviders.NEO_PROVIDER_URI
import mk.nasa.framework.getBooleanPreference
import mk.nasa.framework.sendBroadcast
import mk.nasa.framework.setBooleanPreference
import mk.nasa.framework.setDatePreference
import mk.nasa.handlers.downloadAndStorePicture
import mk.nasa.models.Apod
import mk.nasa.models.Neo
import mk.nasa.models.NeoBase
import mk.nasa.nasamodels.NasaApod
import mk.nasa.nasamodels.NasaNeo
import mk.nasa.nasamodels.NasaNeoBase
import mk.nasa.receivers.NasaReceiver
import okhttp3.internal.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


const val API_KEY = "api_key"
const val DEMO_KEY = "DEMO_KEY"
const val START_DATE = "start_date"
const val END_DATE = "end_date"
const val DATE_FORMAT = "yyyy-MM-dd"

class NasaFetcher(private val context: Context) {

    private var nasaApi: NasaApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(NASA_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        nasaApi = retrofit.create(NasaApi::class.java)
    }

    fun fetchApodData(startDate: LocalDate, endDate: LocalDate) {
        val filterMap = Util.immutableMap(
            mapOf(
                API_KEY to DEMO_KEY,
                START_DATE to startDate.toString(),
                END_DATE to endDate.toString()
            )
        )

        val request = nasaApi.fetchApodData(filterMap)
        request.enqueue(object : Callback<List<NasaApod>> {
            override fun onResponse(
                call: Call<List<NasaApod>>, response: Response<List<NasaApod>>
            ) {
                response.body()?.let {
                    populateApod(it)
                }
            }

            override fun onFailure(call: Call<List<NasaApod>>, t: Throwable) {
                Log.d(javaClass.name, t.message, t)
            }

        })
    }

    fun fetchNeoData(startDate: LocalDate, endDate: LocalDate) {
        val filterMap = Util.immutableMap(
            mapOf(
                API_KEY to DEMO_KEY,
                START_DATE to startDate.toString(),
                END_DATE to endDate.toString()
            )
        )

        val request = nasaApi.fetchNeoData(filterMap)
        request.enqueue(object : Callback<NasaNeoBase> {
            override fun onResponse(call: Call<NasaNeoBase>, response: Response<NasaNeoBase>) {
                response.body()?.let {
                    populateNeo(it)
                }
            }

            override fun onFailure(call: Call<NasaNeoBase>, t: Throwable) {
                Log.d(javaClass.name, t.message, t)
            }

        })
    }

    private fun populateApod(nasaApodList: List<NasaApod>) {
        GlobalScope.launch {
            nasaApodList.forEach {
                val picturePath = downloadAndStorePicture(
                    context,
                    it.url,
                    it.title.replace(" ", "_")
                )
                val values = ContentValues().apply {
                    put(Apod::date.name, it.date)
                    put(Apod::explanation.name, it.explanation)
                    put(Apod::title.name, it.title)
                    put(Apod::picturePath.name, picturePath)
                    put(Apod::read.name, false)
                }
                context.contentResolver.insert(APOD_PROVIDER_URI, values)
            }
            context.setBooleanPreference(
                context.getString(R.string.apod_data_imported),
                true
            )
            updatePreferenceAndSendBroadcast(
                context.getBooleanPreference(context.getString(R.string.apod_data_imported)),
                context.getBooleanPreference(context.getString(R.string.neo_data_imported))
            )
        }
    }

    private fun populateNeo(nasaNeoBase: NasaNeoBase) {
        nasaNeoBase.nasaNeoMap.forEach {
            it.value.forEach {
                val values = ContentValues().apply {
                    put(
                        Neo::neoReferenceId.name,
                        it.neoReferenceId
                    )
                    put(
                        Neo::name.name,
                        it.name
                    )
                    put(
                        Neo::absoluteMagnitudeH.name,
                        it.absoluteMagnitudeH
                    )
                    put(
                        Neo::minEstimatedDiameterInKm.name,
                        it.estimatedDiameter.kilometers.estimatedDiameterMin
                    )
                    put(
                        Neo::maxEstimatedDiameterInKm.name,
                        it.estimatedDiameter.kilometers.estimatedDiameterMax
                    )
                    put(
                        Neo::isPotentiallyHazardousAsteroid.name,
                        it.isPotentiallyHazardousAsteroid
                    )
                    put(
                        Neo::epochDateCloseApproach.name,
                        it.closeApproachData[0].epochDateCloseApproach.toInt()
                    )
                    put(
                        Neo::relativeVelocityKmph.name,
                        it.closeApproachData[0].relativeVelocity.kilometersPerHour.toDouble()
                    )
                    put(
                        Neo::orbitingBody.name,
                        it.closeApproachData[0].orbitingBody.value
                    )
                    put(
                        Neo::isSentryObject.name,
                        it.isSentryObject
                    )
                }
                context.contentResolver.insert(NEO_PROVIDER_URI, values)
            }
        }

        context.setBooleanPreference(
            context.getString(R.string.neo_data_imported),
            true
        )
        updatePreferenceAndSendBroadcast(
            context.getBooleanPreference(context.getString(R.string.apod_data_imported)),
            context.getBooleanPreference(context.getString(R.string.neo_data_imported))
        )
    }

    private fun updatePreferenceAndSendBroadcast(
        apodDataImported: Boolean,
        neoDataImported: Boolean
    ) {
        if (apodDataImported && neoDataImported) {
            context.setDatePreference(
                context.getString(R.string.last_data_import_date),
                LocalDate.now()
            )
            context.sendBroadcast<NasaReceiver>()
        }
    }
}