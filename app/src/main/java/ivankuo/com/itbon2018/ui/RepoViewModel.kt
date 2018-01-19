package ivankuo.com.itbon2018.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel

import javax.inject.Inject

import ivankuo.com.itbon2018.data.RepoRepository
import ivankuo.com.itbon2018.data.model.Repo
import ivankuo.com.itbon2018.data.model.Resource
import ivankuo.com.itbon2018.util.AbsentLiveData

class RepoViewModel @Inject
constructor(private val repository: RepoRepository) : ViewModel() {

    private val query = MutableLiveData<String>()

    val repos: LiveData<Resource<List<Repo>>>

    init {
        repos = Transformations.switchMap(query) { userInput ->
            if (userInput == null || userInput.isEmpty()) {
                AbsentLiveData.create()
            } else {
                repository.search(userInput)
            }
        }
    }

    fun searchRepo(userInput: String) {
        query.value = userInput
    }
}