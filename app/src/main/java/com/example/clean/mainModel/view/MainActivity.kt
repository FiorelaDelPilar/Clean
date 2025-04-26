package com.example.clean.mainModel.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clean.common.SportEvent
import com.example.clean.databinding.ActivityMainBinding
import com.example.clean.mainModel.presenter.MainPresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), OnClickListener, MainView {
    private lateinit var binding: ActivityMainBinding
    private val adapter: ResultAdapter by inject { parametersOf(this) }
    private val presenter: MainPresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        presenter.onCreate()
        setupRecyclerView()
        setupSwipeRefresh()
        setupClicks()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupSwipeRefresh() {
        binding.srlResults.setOnRefreshListener {
            lifecycleScope.launch { presenter.refresh() }
        }
    }

    private fun setupClicks() {
        binding.btnAd.run {
            setOnClickListener {
                lifecycleScope.launch {
                    lifecycleScope.launch { presenter.registerAd() }
                }
            }
            setOnLongClickListener {
                lifecycleScope.launch {
                    lifecycleScope.launch { presenter.closeAd() }
                }
                true
            }

        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch { presenter.getEvents() }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun onClick(result: SportEvent.ResultSuccess) {
        lifecycleScope.launch {
            presenter.saveResult(result)
        }
    }

    //View layer
    override fun add(event: SportEvent.ResultSuccess) {
        adapter.add(event)
    }

    override fun clearAdapter() {
        adapter.clear()
    }

    override suspend fun showAdUI(isVisible: Boolean) = withContext(Dispatchers.Main) {
        binding.btnAd.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun showProgress(isVisible: Boolean) {
        binding.srlResults.isRefreshing = isVisible
    }

    override suspend fun showToast(msg: String) = withContext(Dispatchers.Main) {
        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showSnackbar(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }
}