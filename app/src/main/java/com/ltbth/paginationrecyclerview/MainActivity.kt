package com.ltbth.paginationrecyclerview

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
//    private lateinit var progressBar: ProgressBar
    private var isLoading = false
    private var isLastPage = false
    private var currentPage = 1
    private var totalPage = 5
    private var users = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        progressBar = findViewById(R.id.progress_bar)
        recyclerView = findViewById(R.id.rcv_user)

        users.addAll(getList())
        userAdapter = UserAdapter(users)
        if (currentPage < totalPage) {
            userAdapter.addLoading()
        } else {
            isLastPage = true
        }
        val linearLayoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView.apply {
            adapter = userAdapter
            layoutManager = linearLayoutManager
        }

//        recyclerView.addItemDecoration(
//            DividerItemDecoration(
//                this@MainActivity,
//                DividerItemDecoration.VERTICAL
//            )
//        )

        recyclerView.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItem() {
//                progressBar.visibility = ProgressBar.VISIBLE
                isLoading = true
                currentPage += 1
                loadNextPage()
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

        })
    }

    private fun loadNextPage() {
        val handler = Handler()
        handler.postDelayed({
            val moreUsers: List<User> = getList()
            userAdapter.removeLoading()
            users.addAll(moreUsers)
            userAdapter.notifyDataSetChanged()

//            progressBar.visibility = ProgressBar.GONE
            isLoading = false
            if (currentPage < totalPage) {
                userAdapter.addLoading()
            } else { // currentPage == totalPage
                isLastPage = true
            }
        }, 2000)
    }

    private fun getList(): List<User> {
        Toast.makeText(this@MainActivity, "Load page $currentPage/$totalPage"
            , Toast.LENGTH_LONG)
            .show()
        val users = arrayListOf<User>()
        for (i in 1..10) {
            users.add(User(R.drawable.me, "Username ${Random.nextInt(1000,9999)}"))
        }
        return users
    }
}