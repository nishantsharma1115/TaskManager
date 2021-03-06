package com.nishant.mytasks.ui

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

//    private val _getAllCategoriesStatus = MutableLiveData<Resource<List<TaskCount>>>()
//    val getAllCategoriesStatus: LiveData<Resource<List<TaskCount>>> = _getAllCategoriesStatus
//    fun getAllCategoriesWithCount() = viewModelScope.launch {
//        _getAllCategoriesStatus.postValue(Resource.Loading())
//        dataRepository.getAllCategoriesWithCount({ categoryList ->
//            _getAllCategoriesStatus.postValue(Resource.Success(categoryList))
//        }, { error ->
//            _getAllCategoriesStatus.postValue(Resource.Error(error))
//        })
//    }

    val categoryListWithCount = dataRepository.getAllCategoriesWithCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}