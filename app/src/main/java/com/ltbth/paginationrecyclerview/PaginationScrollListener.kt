package com.ltbth.paginationrecyclerview

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private val linearLayoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount: Int = linearLayoutManager.childCount
        val totalItemCount: Int = linearLayoutManager.itemCount
        val firstVisibleItemPosition: Int = linearLayoutManager.findFirstVisibleItemPosition()

        if (isLoading() || isLastPage()) {
            return
        } else if (firstVisibleItemPosition >= 0 && visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
            loadMoreItem()
        }
    }

    abstract fun loadMoreItem()
    abstract fun isLoading(): Boolean
    abstract fun isLastPage(): Boolean
}