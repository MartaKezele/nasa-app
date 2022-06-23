package mk.nasa.contentproviders

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import mk.nasa.dao.NasaRepo
import mk.nasa.dao.getNasaRepo
import mk.nasa.models.Apod
import java.lang.IllegalArgumentException

private const val AUTHORITY = "mk.nasa.contentproviders.apodprovider"
private const val PATH = "apod"
private const val APOD = "apod"
val APOD_PROVIDER_URI = Uri.parse("content://$AUTHORITY/$PATH")
private const val APODS = 10
private const val APOD_ID = 20
private val URI_MATCHER = with(UriMatcher(UriMatcher.NO_MATCH)) {
    addURI(AUTHORITY, PATH, APODS)
    addURI(AUTHORITY, "$PATH/#", APOD_ID)
    this
}

class ApodProvider : ContentProvider() {

    private lateinit var nasaRepository: NasaRepo

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        when (URI_MATCHER.match(uri)) {
            APODS -> return nasaRepository.delete(APOD, selection, selectionArgs)
            APOD_ID -> {
                // eg. /5 -> id
                uri.lastPathSegment?.let {
                    return nasaRepository.delete(APOD, "${Apod::_id.name}=?", arrayOf(it))
                }

            }
        }
        throw IllegalArgumentException("Wrong uri.")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? =
        ContentUris.withAppendedId(APOD_PROVIDER_URI, nasaRepository.insert(APOD, values))

    override fun onCreate(): Boolean {
        nasaRepository = getNasaRepo(context)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? =
        nasaRepository.query(APOD, projection, selection, selectionArgs, sortOrder)

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        when (URI_MATCHER.match(uri)) {
            APODS -> return nasaRepository.update(APOD, values, selection, selectionArgs)
            APOD_ID -> {
                // eg. /5 -> id
                uri.lastPathSegment?.let {
                    return nasaRepository.update(APOD, values, "${Apod::_id.name}=?", arrayOf(it))
                }

            }
        }
        throw IllegalArgumentException("Wrong uri.")
    }
}