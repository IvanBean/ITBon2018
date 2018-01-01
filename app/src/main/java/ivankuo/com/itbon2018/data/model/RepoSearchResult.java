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

package ivankuo.com.itbon2018.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import ivankuo.com.itbon2018.data.db.GithubTypeConverters;

@Entity
@TypeConverters(GithubTypeConverters.class)
public class RepoSearchResult {
    @NonNull
    @PrimaryKey
    public final String query;
    public final List<Integer> repoIds;
    public final int totalCount;

    public RepoSearchResult(@NonNull String query, List<Integer> repoIds, int totalCount) {
        this.query = query;
        this.repoIds = repoIds;
        this.totalCount = totalCount;
    }
}