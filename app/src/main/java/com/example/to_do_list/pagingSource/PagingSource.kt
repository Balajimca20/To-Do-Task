package com.example.to_do_list.pagingSource

class PagingSource<Key,Item>(
    private val initialKey:Key,
    private inline val onLoadUpdated:(Boolean)->Unit,
    private inline val onRequest:suspend (nextKey:Key) -> Result<Item>,
    private inline val getNextKey:suspend (Item) -> Key,
    private inline val onError:suspend (Throwable?) -> Unit,
    private inline val onSuccess:suspend (items:Item,newKey:Key) -> Unit
):Paginator<Key,Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false
    override suspend fun loadNextItems() {
        if(isMakingRequest){
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currentKey)
        isMakingRequest = false

        val items = result.getOrElse {
            onError(it)
            onLoadUpdated(false)
            return
        }
        currentKey = getNextKey(items)
        onSuccess(items,currentKey)
        onLoadUpdated(false)
    }

    override fun reset() {
        currentKey = initialKey
    }
}