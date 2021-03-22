package com.pondoo.application.data.remote

import com.pondoo.application.model.ResultFlow
import com.pondoo.application.utils.ErrorUtils
import com.pondoo.application.model.Error
import com.pondoo.application.model.News
import com.pondoo.application.network.ApiInterface
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val retrofit: Retrofit) {

    suspend fun fethNews(ct:String,category: String,apiKey: String):ResultFlow<News>{
        val newsService=retrofit.create(ApiInterface::class.java)
        return getResponse(request = { newsService.getNews(ct,category,apiKey)},
            defaultErrorMessage = "Error fetching News")
    }


    private suspend fun  <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String): ResultFlow<T> {
        return try {
            println("I'm working in thread ${Thread.currentThread().name}")
            val result = request.invoke()
            if (result.isSuccessful) {
                return ResultFlow.success(result.body())
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                ResultFlow.error(errorResponse?.status_message ?: defaultErrorMessage, errorResponse)
            }

        } catch (e: Throwable) {
            return ResultFlow.error("Unknown Error", null)
        }
    }
}