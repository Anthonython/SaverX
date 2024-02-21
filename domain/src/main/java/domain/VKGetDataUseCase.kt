package domain

import domain.model.LinkModelDomain

class VKGetDataUseCase(private val repository: DataRepositoryDomain) {

    interface Callback {
        fun onProgressUpdate(progress: Int)
        fun onSuccess(result: Boolean)
        fun onError(error: Exception?)
    }

    fun execute(data: LinkModelDomain, callback: Callback) {
        try {
            val url = repository.getLinkVK(data.link)
            if (url != "") {
                repository.getDataVK(LinkModelDomain(url, data.fileName))
                callback.onSuccess(true)
            } else {
                callback.onSuccess(false)
            }
        } catch (e: Exception) {
            callback.onError(e)
        }
    }
}