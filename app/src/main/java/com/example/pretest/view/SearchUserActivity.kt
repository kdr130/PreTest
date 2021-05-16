package com.example.pretest.view

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pretest.databinding.ActivitySearchUserBinding
import com.example.pretest.utils.KeyboardUtil
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchUserBinding
    private val viewModel : SearchUserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.model = viewModel
        binding.lifecycleOwner = this

        initSearch()
        initRecyclerView()
        initObserver()
    }

    private fun initSearch() {
        binding.search.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                viewModel.search(binding.search.text.toString().trim())
                KeyboardUtil.hideSoftKeyBoard(this@SearchUserActivity, binding.search)
                true
            } else {
                false
            }
        }

        // todo: remove it
        binding.search.setText("test")
        viewModel.search(binding.search.text.toString().trim())
    }

    private fun initRecyclerView() {
        val dividerItemDecoration = DividerItemDecoration(
            this, (binding.list.layoutManager as? LinearLayoutManager)?.orientation ?: LinearLayoutManager.VERTICAL
        )
        binding.list.addItemDecoration(dividerItemDecoration)
    }

    private fun initObserver() {
        viewModel.userList.observe(this, {
            Log.d(TAG, "get list: ${it}")
            binding.list.adapter = SearchUserAdapter(it)
        })
    }

    companion object {
        private const val TAG = "SearchUserActivity"
    }
}