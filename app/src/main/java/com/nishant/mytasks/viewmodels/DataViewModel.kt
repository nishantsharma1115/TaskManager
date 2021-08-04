package com.nishant.mytasks.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nishant.mytasks.model.Task
import com.nishant.mytasks.repositories.DataRepository
import com.nishant.mytasks.util.Resource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DataViewModel
@ViewModelInject
constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _insertTaskStatus: MutableLiveData<Resource<Long>> = MutableLiveData()
    val insertTaskStatus: LiveData<Resource<Long>> get() = _insertTaskStatus
    fun insertIntoDb(task: Task) = viewModelScope.launch {
        _insertTaskStatus.postValue(Resource.Loading())
        dataRepository.insertTask(task, success = {
            _insertTaskStatus.postValue(Resource.Success(it))
        }, failure = {
            _insertTaskStatus.postValue(Resource.Error("Oops....", it))
        })
    }

    val categoryListWithCount = dataRepository.getAllCategoriesWithCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val todayNotes = dataRepository.getTodayTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val archieveTasks = dataRepository.getArchieveTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}