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

package ivankuo.com.itbon2018.data.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index

import com.google.gson.annotations.SerializedName

/**
 * Using name/owner_login as primary key instead of id since name/owner_login is always available
 * vs id is not.
 */
@Entity(indices = [(Index("id")), (Index("owner_login"))],
        primaryKeys = ["name", "owner_login"])
class Repo(val id: Int,
           @field:SerializedName("name")
           val name: String = "",
           @field:SerializedName("full_name")
           val fullName: String? = "",
           @field:SerializedName("description")
           val description: String? = "",
           @field:SerializedName("stargazers_count")
           val stars: Int?,
           @field:SerializedName("owner")
           @field:Embedded(prefix = "owner_")
           val owner: Owner,
           var html_url: String? = "")