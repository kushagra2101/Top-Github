package com.kushagragoel.top_github.ui.repolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kushagragoel.top_github.network.TopGithubApi
import com.kushagragoel.top_github.network.model.TopGithubApiOutputBean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoListViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<TopGithubApiOutputBean?>()

    // The external immutable LiveData for the response String
    val response: LiveData<TopGithubApiOutputBean?>
        get() = _response

    private val _is_api_in_progress = MutableLiveData<Boolean>()
    val isApiInProgress: LiveData<Boolean>
        get() = _is_api_in_progress


    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun onLangButtonClick(enteredLang: String?) {
        if (!enteredLang.isNullOrEmpty()) {
            callGetTopGithubProjectsAPI(enteredLang)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun callGetTopGithubProjectsAPI(langString: String) {
        _is_api_in_progress.value = true
        TopGithubApi.retrofitService.getRepo(langString)?.enqueue(object :
            Callback<TopGithubApiOutputBean> {
            override fun onResponse(
                call: Call<TopGithubApiOutputBean>,
                response: Response<TopGithubApiOutputBean>
            ) {
                _response.value = response.body()
                _is_api_in_progress.value = false
            }

            override fun onFailure(call: Call<TopGithubApiOutputBean>, t: Throwable) {
                _response.value = null
                _is_api_in_progress.value = false
                print(t)
            }
        })
    }
}