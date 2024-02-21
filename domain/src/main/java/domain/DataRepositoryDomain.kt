package domain

import domain.model.LinkModelDomain
import kotlinx.coroutines.flow.StateFlow

interface DataRepositoryDomain {
    fun getDataVK(data: LinkModelDomain)
    fun getLinkVK(inpUrl: String): String

    val downloadProgress: StateFlow<Int>

}