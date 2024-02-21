package com.example.data.data

import android.os.Environment
import android.util.Log
import domain.DataRepositoryDomain
import domain.model.LinkModelDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class DataRepository : DataRepositoryDomain {
    private val _downloadProgress = MutableStateFlow(0)
    override val downloadProgress: StateFlow<Int> = _downloadProgress

    override fun getDataVK(data: LinkModelDomain) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val connection = URL(data.link).openConnection()
                connection.connect()

                val fileSize: Float = connection.contentLength.toFloat()
                var progress = 0f
                var progressP: Float

                connection.getInputStream().use { inputStream ->
                    val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    val outputFile = File(storageDir, data.fileName)

                    if (!outputFile.exists()) outputFile.createNewFile()

                    FileOutputStream(outputFile).use { outputStream ->
                        val buffer = ByteArray(4096)
                        var read: Int

                        while (inputStream.read(buffer).also { read = it } != -1) {
                            progress += 4096f
                            outputStream.write(buffer, 0, read)
                            progressP = ((progress / fileSize) * 100f)
                            if (progressP.toInt() % 2 == 0) {
                                _downloadProgress.value = progressP.toInt()
                                Log.e("TAG", "РЕЗ: ${progressP.toInt()}")
                            }
                        }
                    }
                }


            } catch (e: Exception) {
                Log.e("TAG", "РЕЗ: DATAREPOS $e")
            }
        }
    }

    override fun getLinkVK(inpUrl: String): String {
        var value = ""
        try {
            val document = Jsoup.connect(inpUrl).get()
            val regex = """("url480":"[^"]+")""".toRegex()
            val matches = regex.findAll(document.toString())

            for (match in matches) {
                value = match.groupValues[0]
            }
            value = value.substring(10)
            value = value.substring(0, value.length - 1)

        } catch (e: Exception) {
            Log.e("TAG", "РЕЗ ошибка ${e.message}")
        }
        return value
    }
}
