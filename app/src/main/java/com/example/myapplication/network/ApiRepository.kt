package com.example.myapplication.network

import com.example.myapplication.ui.general.model.Data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiRepository {

    var client: Webservice = Webservice.webservice
    suspend fun getAllData() : Flow<List<Data>> = flow { emit(client.getData()) }
}