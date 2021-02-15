package com.kushagragoel.top_github.ui.repolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ReportListViewModelFactory() : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RepoListViewModel()
        /*(
            unsplashRepository
        )*/ as T
    }
}