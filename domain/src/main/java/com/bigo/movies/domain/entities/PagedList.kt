package com.bigo.movies.domain.entities

class PagedList<T>(
    val items: List<T>,
    val page: Int,
    val pages: Long,
    val perPage: Int,
    val total: Long
) {
    fun isFirstPage() = page == 1

    fun nextPage() = page + 1

    fun hasNext() = page < pages

    class Builder<T> {
        private var items: List<T> = listOf()
        private var page = 0
        private var pages = 0L
        private var perPage = 0
        private var total = 0L

        fun items(items: List<T>) = apply {
            this.items = items
        }

        fun page(page: Int) = apply {
            this.page = page
        }

        fun pages(pages: Long) = apply {
            this.pages = pages
        }

        fun perPage(perPage: Int) = apply {
            this.perPage = perPage
        }

        fun total(total: Long) = apply {
            this.total = total
        }

        fun build() =
            PagedList(items, page, pages, perPage, total)
    }
}