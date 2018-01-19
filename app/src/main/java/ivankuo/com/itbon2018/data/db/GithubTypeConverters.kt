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

package ivankuo.com.itbon2018.data.db

import android.arch.persistence.room.TypeConverter
import android.util.Log

import java.util.ArrayList
import java.util.StringTokenizer

class GithubTypeConverters {
    @TypeConverter
    fun stringToIntList(data: String?): List<Int>? {
        return if (data == null) {
            emptyList()
        } else splitToIntList(data)
    }

    @TypeConverter
    fun intListToString(ints: List<Int>?): String? {
        return joinIntoString(ints)
    }

    /**
     * Splits a comma separated list of integers to integer list.
     *
     *
     * If an input is malformed, it is omitted from the result.
     *
     * @param input Comma separated list of integers.
     * @return A List containing the integers or null if the input is null.
     */
    fun splitToIntList(input: String?): List<Int>? {
        if (input == null) {
            return null
        }
        val result = ArrayList<Int>()
        val tokenizer = StringTokenizer(input, ",")
        while (tokenizer.hasMoreElements()) {
            val item = tokenizer.nextToken()
            try {
                result.add(Integer.parseInt(item))
            } catch (ex: NumberFormatException) {
                Log.e("ROOM", "Malformed integer list", ex)
            }

        }
        return result
    }

    /**
     * Joins the given list of integers into a comma separated list.
     *
     * @param input The list of integers.
     * @return Comma separated string composed of integers in the list. If the list is null, return
     * value is null.
     */
    fun joinIntoString(input: List<Int>?): String? {
        if (input == null) {
            return null
        }

        val size = input.size
        if (size == 0) {
            return ""
        }
        val sb = StringBuilder()
        for (i in 0 until size) {
            sb.append(Integer.toString(input[i]))
            if (i < size - 1) {
                sb.append(",")
            }
        }
        return sb.toString()
    }
}