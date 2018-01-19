package ivankuo.com.itbon2018.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations

import javax.inject.Inject
import javax.inject.Singleton

import ivankuo.com.itbon2018.api.ApiResponse
import ivankuo.com.itbon2018.api.GithubService
import ivankuo.com.itbon2018.api.RepoSearchResponse
import ivankuo.com.itbon2018.data.db.RepoDao
import ivankuo.com.itbon2018.data.model.Repo
import ivankuo.com.itbon2018.data.model.RepoSearchResult
import ivankuo.com.itbon2018.data.model.Resource
import ivankuo.com.itbon2018.util.AbsentLiveData

@Singleton
class RepoRepository @Inject
constructor(private val repoDao: RepoDao, private val githubService: GithubService) {

    fun search(query: String): LiveData<Resource<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>, RepoSearchResponse>() {
            override fun loadFromDb(): LiveData<List<Repo>> {
                return Transformations.switchMap(repoDao.search(query)) { searchData ->
                    if (searchData == null) {
                        AbsentLiveData.create()
                    } else {
                        repoDao.loadOrdered(searchData.repoIds!!)
                    }
                }
            }

            override fun shouldFetch(data: List<Repo>?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<RepoSearchResponse>> {
                return githubService.searchRepos(query)
            }

            override fun saveCallResult(item: RepoSearchResponse) {
                val repoIds = item.repoIds
                val repoSearchResult = RepoSearchResult(query, repoIds, item.total)

                repoDao.insertRepos(item.items)
                repoDao.insert(repoSearchResult)
            }
        }.asLiveData()
    }
}