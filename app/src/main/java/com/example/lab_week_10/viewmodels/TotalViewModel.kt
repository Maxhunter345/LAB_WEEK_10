package com.example.lab_week_10.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class TotalViewModel : ViewModel() {
    private val _total = MutableLiveData<Int>()
    val total: LiveData<Int> = _total

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        _total.value = 0
    }

    fun incrementTotal() {
        _total.value = _total.value?.plus(1)
    }

    fun setTotal(newTotal: Int) {
        _total.value = newTotal
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}