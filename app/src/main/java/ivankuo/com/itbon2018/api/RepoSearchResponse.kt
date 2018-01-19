/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ivankuo.com.itbon2018.api

import com.google.gson.annotations.SerializedName

import java.util.ArrayList

import ivankuo.com.itbon2018.data.model.Repo

/**
 * POJO to hold repo search responses. This is different from the Entity in the database because
 * we are keeping a search result in 1 row and denormalizing list of results into a single column.
 */
class RepoSearchResponse {
    @SerializedName("total_count")
    var total: Int = 0
    @SerializedName("items")
    var items: List<Repo>? = null

    val repoIds: List<Int>
        get() {
            val repoIds = ArrayList<Int>()
            for (repo in items!!) {
                repoIds.add(repo.id)
            }
            return repoIds
        }
}