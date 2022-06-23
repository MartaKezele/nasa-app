package mk.nasa.api

import mk.nasa.nasamodels.NasaApod
import mk.nasa.nasamodels.NasaNeoBase
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

const val NASA_API_URL = "https://api.nasa.gov/"
interface NasaApi {
    @GET("planetary/apod")
    fun fetchApodData(@QueryMap filters : Map<String, String>) : Call<List<NasaApod>>

    @GET("neo/rest/v1/feed")
    fun fetchNeoData(@QueryMap filters : Map<String, String>) : Call<NasaNeoBase>


}