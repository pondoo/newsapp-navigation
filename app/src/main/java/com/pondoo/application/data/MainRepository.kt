package com.pondoo.application.data

import com.pondoo.application.data.remote.RemoteDataSource
import com.pondoo.application.model.News
import com.pondoo.application.model.ResultFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class MainRepository  @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {


    suspend fun fetchNews(country:String,category: String,apiKey: String): Flow<ResultFlow<News>> {
        return flow {
            try {
                emit(ResultFlow.loading())
                emit(remoteDataSource.fethNews(country,category,apiKey))
            }catch (e: Exception){
                e.printStackTrace()
            }
            //Cache to database if response is successful
        }.flowOn(Dispatchers.IO)
    }
}