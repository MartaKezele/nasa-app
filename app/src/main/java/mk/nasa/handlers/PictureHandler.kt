package mk.nasa.handlers

import android.content.Context
import android.util.Log
import mk.nasa.factory.createGetHttpUrlConnection
import java.io.File
import java.lang.Exception
import java.net.HttpURLConnection
import java.nio.file.Files
import java.nio.file.Paths

fun downloadAndStorePicture(context: Context, url: String, fileName: String): String? {
    val extension = url.substring(url.lastIndexOf("."))
    val file: File = createFile(context, fileName, extension)
    try {
        val connection: HttpURLConnection = createGetHttpUrlConnection(url)
        Files.copy(connection.inputStream, Paths.get(file.toURI()))
        return file.absolutePath
    } catch (e: Exception) {
        Log.e("DOWNLOAD_IMAGE", e.message, e)
    }
    return null
}

fun createFile(context: Context, fileName: String, extension: String): File {
    val dir = context.applicationContext.getExternalFilesDir(null)
    val file = File(dir, File.separator + fileName + extension)
    if (file.exists()) {
        file.delete()
    }
    return file
}