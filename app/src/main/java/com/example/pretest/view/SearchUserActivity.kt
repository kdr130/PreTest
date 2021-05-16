package com.example.pretest.view

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pretest.databinding.ActivitySearchUserBinding
import com.example.pretest.utils.KeyboardUtil
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchUserBinding
    private val viewModel: SearchUserViewModel by viewModel()
    private val adapter: SearchUserAdapter = SearchUserAdapter()
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.model = viewModel
        binding.lifecycleOwner = this

        initSearch()
        initRecyclerView()
        restoreState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, binding.search.text.trim().toString())
    }


    private fun initSearch() {
        binding.search.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                search()
                KeyboardUtil.hideSoftKeyBoard(this@SearchUserActivity, binding.search)
                true
            } else {
                false
            }
        }
    }

    private fun initRecyclerView() {
        val dividerItemDecoration = DividerItemDecoration(
            this,
            (binding.list.layoutManager as? LinearLayoutManager)?.orientation
                ?: LinearLayoutManager.VERTICAL
        )
        binding.list.addItemDecoration(dividerItemDecoration)

        binding.list.adapter = adapter
        adapter.addLoadStateListener {
            viewModel.setLoadState(it, adapter.itemCount)
        }
    }

    private fun search() {
        val query = binding.search.text.toString().trim()
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.search(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun restoreState(instanceState: Bundle?) {
        val lastQuery = instanceState?.getString(LAST_SEARCH_QUERY) ?: ""
        binding.search.setText(lastQuery)

        if (lastQuery.isNotEmpty()) {
            search()
        }
    }

    companion object {
        private const val TAG = "SearchUserActivity"
        private const val LAST_SEARCH_QUERY = "LAST_SEARCH_QUERY"
    }
}