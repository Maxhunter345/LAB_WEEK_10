package com.example.lab_week_10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.ViewModelProvider
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDatabase
import com.example.lab_week_10.viewmodels.TotalViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var db: TotalDatabase
    private val viewModel by lazy {
        ViewModelProvider(this)[TotalViewModel::class.java]
    }

    companion object {
        const val DEFAULT_ID: Long = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize database
        db = TotalDatabase.getInstance(this)

        // Load data from database
        loadDataFromDatabase()

        // Setup ViewModel observers
        prepareViewModel()
    }

    private fun updateText(total: Int) {
        findViewById<TextView>(R.id.text_total).text =
            getString(R.string.text_total, total)
    }

    private fun prepareViewModel() {
        viewModel.total.observe(this) { total ->
            updateText(total ?: 0)
        }

        findViewById<Button>(R.id.button_increment).setOnClickListener {
            viewModel.incrementTotal()
            // Save immediately when button is clicked
            saveDataToDatabase()
        }
    }

    private fun loadDataFromDatabase() {
        lifecycleScope.launch {
            val totalEntity = db.totalDao().getTotal(DEFAULT_ID)
            totalEntity?.let {
                viewModel.setTotal(it.totalValue)
            } ?: run {
                // If no data exists, create initial data
                val initialTotal = Total(id = DEFAULT_ID, totalValue = 0)
                db.totalDao().insert(initialTotal)
                viewModel.setTotal(0)
            }
        }
    }

    private fun saveDataToDatabase() {
        lifecycleScope.launch {
            val currentTotal = viewModel.total.value ?: 0
            val totalEntity = Total(id = DEFAULT_ID, totalValue = currentTotal)
            db.totalDao().insert(totalEntity) // Using insert with REPLACE strategy
        }
    }

    override fun onPause() {
        super.onPause()
        // Save data when app goes to background
        saveDataToDatabase()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Save data when app is destroyed
        saveDataToDatabase()
    }
}