package mk.nasa.contentproviders

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import mk.nasa.dao.NasaRepo
import mk.nasa.dao.getNasaRepo
import mk.nasa.models.Neo
import java.lang.IllegalArgumentException

private const val AUTHORITY = "mk.nasa.contentproviders.neoprovider"
private const val PATH = "neo"
private const val NEO = "neo"
val NEO_PROVIDER_URI = Uri.parse("content://$AUTHORITY/$PATH")
private const val NEOS = 10
private const val NEO_ID = 20
private val URI_MATCHER = with(UriMatcher(UriMatcher.NO_MATCH)) {
    addURI(AUTHORITY, PATH, NEOS)
    addURI(AUTHORITY, "$PATH/#", NEO_ID)
    this
}

class NeoProvider : ContentProvider() {

    private lateinit var nasaRepository: NasaRepo

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        when (URI_MATCHER.match(uri)) {
            NEOS -> return nasaRepository.delete(NEO, selection, selectionArgs)
            NEO_ID -> {
                // eg. /5 -> id
                uri.lastPathSegment?.let {
                    return nasaRepository.delete(NEO, "${Neo::_id.name}=?", arrayOf(it))
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
        ContentUris.withAppendedId(NEO_PROVIDER_URI, nasaRepository.insert(NEO, values))

    override fun onCreate(): Boolean {
        nasaRepository = getNasaRepo(context)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? =
        nasaRepository.query(NEO, projection, selection, selectionArgs, sortOrder)

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        when (URI_MATCHER.match(uri)) {
            NEOS -> return nasaRepository.update(NEO, values, selection, selectionArgs)
            NEO_ID -> {
                // eg. /5 -> id
                uri.lastPathSegment?.let {
                    return nasaRepository.update(NEO, values, "${Neo::_id.name}=?", arrayOf(it))
                }

            }
        }
        throw IllegalArgumentException("Wrong uri.")
    }
}