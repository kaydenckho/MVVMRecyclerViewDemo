package com.example.myapplication.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.localDatabase.LocalDatabase
import com.example.myapplication.model.Data
import com.example.myapplication.network.ApiRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ViewModelMainActivity(application: Application) : AndroidViewModel(application) {

    val dataList = MutableLiveData<ArrayList<Data>>()

    val liveDataList: LiveData<ArrayList<Data>> = dataList

    val apiRepository: ApiRepository by lazy { ApiRepository() }

    val db by lazy { LocalDatabase(this.getApplication()) }

    val online = isOnline(application.applicationContext)

    suspend fun getImageAsync() {
        if (online) {
            apiRepository.getAllData().collect {
                db.DataDao().insertData(it)
                dataList.value = it as ArrayList<Data>
            }
        } else {
            dataList.value = db.DataDao().getData() as ArrayList<Data>
        }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }
}
