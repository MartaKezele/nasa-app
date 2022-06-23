package mk.nasa.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import mk.nasa.models.Apod
import mk.nasa.models.Neo

private const val DB_NAME = "nasa.db"
private const val DB_VERSION = 1
private const val APOD = "apod"
private const val NEO = "neo"

private const val DROP_APOD = "drop table if exists $APOD"
private const val DROP_NEO = "drop table if exists $NEO"
private val CREATE_TABLE_APOD = "create table if not exists $APOD(" +
        "${Apod::_id.name} integer primary key autoincrement, " +
        "${Apod::date.name} text not null, " +
        "${Apod::explanation.name} text not null, " +
        "${Apod::title.name} text not null, " +
        "${Apod::picturePath.name} text not null, " +
        "${Apod::read.name} integer not null" +
        ")"
private val CREATE_TABLE_NEO = "create table if not exists $NEO(" +
        "${Neo::_id.name} integer primary key autoincrement, " +
        "${Neo::neoReferenceId.name} text not null, " +
        "${Neo::name.name} text not null, " +
        "${Neo::absoluteMagnitudeH.name} real not null, " +
        "${Neo::minEstimatedDiameterInKm.name} real not null, " +
        "${Neo::maxEstimatedDiameterInKm.name} real not null, " +
        "${Neo::isPotentiallyHazardousAsteroid.name} integer not null, " +
        "${Neo::epochDateCloseApproach.name} integer not null, " +
        "${Neo::relativeVelocityKmph.name} real not null, " +
        "${Neo::orbitingBody.name} text not null, " +
        "${Neo::isSentryObject.name} integer not null" +
        ")"

class NasaSqlHelper(
    context: Context?
) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION), NasaRepo {
    override fun onCreate(db: SQLiteDatabase) {
         db.execSQL(CREATE_TABLE_APOD)
        db.execSQL(CREATE_TABLE_NEO)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // temporary
        db.execSQL(DROP_APOD)
        db.execSQL(DROP_NEO)
        onCreate(db)
    }

    override fun delete(table: String, selection: String?, selectionArgs: Array<String>?) =
        writableDatabase.delete(table, selection, selectionArgs)


    override fun update(
        table: String,
        values: ContentValues?,
        selection: String?,

        selectionArgs: Array<String>?
    ) =
    writableDatabase.update(table, values, selection, selectionArgs)


    override fun insert(table: String, values: ContentValues?) =
        writableDatabase.insert(table, null, values)


    override fun query(
        table: String,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? =
        readableDatabase.query(
            table,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder)

}