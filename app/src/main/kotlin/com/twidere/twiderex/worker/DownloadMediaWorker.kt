/*
 *  Twidere X
 *
 *  Copyright (C) 2020-2021 Tlaster <tlaster@outlook.com>
 * 
 *  This file is part of Twidere X.
 * 
 *  Twidere X is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  Twidere X is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with Twidere X. If not, see <http://www.gnu.org/licenses/>.
 */
package com.twidere.twiderex.worker

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.twidere.twiderex.extensions.toWorkResult
import com.twidere.twiderex.jobs.common.DownloadMediaJob
import com.twidere.twiderex.model.MicroBlogKey
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DownloadMediaWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val contentResolver: ContentResolver,
    private val downloadMediaJob: DownloadMediaJob,
) : CoroutineWorker(context, workerParams) {

    companion object {
        fun create(
            accountKey: MicroBlogKey,
            source: String,
            target: Uri,
        ) = OneTimeWorkRequestBuilder<DownloadMediaWorker>()
            .setInputData(
                Data.Builder()
                    .putString("accountKey", accountKey.toString())
                    .putString("source", source)
                    .putString("target", target.toString())
                    .build()
            )
            .build()
    }

    override suspend fun doWork(): Result {
        val target = inputData.getString("target") ?: return Result.failure()
        val source = inputData.getString("source") ?: return Result.failure()
        val accountKey = inputData.getString("accountKey")?.let {
            MicroBlogKey.valueOf(it)
        } ?: return Result.failure()
        return downloadMediaJob.execute(
            target = target,
            source = source,
            accountKey = accountKey
        ) {
            contentResolver.openOutputStream(Uri.parse(it))
        }.toWorkResult()
    }
}
