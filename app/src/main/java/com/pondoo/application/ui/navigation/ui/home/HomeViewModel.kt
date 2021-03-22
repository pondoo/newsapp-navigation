package com.pondoo.application.ui.navigation.ui.home

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pondoo.application.data.MainRepository
import com.pondoo.application.model.Article
import com.pondoo.application.model.ResultFlow
import com.pondoo.application.utils.Config
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class HomeViewModel @ViewModelInject constructor(val mainRepository: MainRepository): ViewModel() {


    var articleList= MutableLiveData<List<Article>>()

    fun apiCall(ct:Context,category:String){
        try {
            var language = Locale.getDefault().language //Getting device Language
            val prefences = ct.getSharedPreferences("Utils", Context.MODE_PRIVATE)
            var country= prefences.getString("country","us")
            viewModelScope.launch {
                mainRepository.fetchNews(country!!,category, Config.api).collect {
                    if(it.status.equals(ResultFlow.Status.SUCCESS)){
                        articleList.postValue(it.data!!.articles)
                    }
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

}