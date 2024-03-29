package com.example.to_do_list.pagingSource

interface Paginator<Key,Item> {
    suspend fun loadNextItems()
    fun reset()
}