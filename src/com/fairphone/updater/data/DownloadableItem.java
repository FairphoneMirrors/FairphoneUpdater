/*
 * Copyright (C) 2014 Fairphone Project
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

package com.fairphone.updater.data;

import java.util.ArrayList;

import android.text.TextUtils;
import android.util.Log;

public abstract class DownloadableItem
{
    private static final String TAG = Version.class.getSimpleName();

    public static final String DEFAULT_NOTES_LANG = "en";

    private static final String DEPENDENCY_SEPARATOR = ",";

    protected int mNumber;

    protected String mName;

    protected String mOTADownloadLink;

    protected String mOTAMd5Sum;

    protected String mBuildNumber;

    protected String mReleaseDate;

    protected String mThumbnailImageLink;

    protected ArrayList<Integer> mDependencies;

    public DownloadableItem()
    {
        setNumber(0);
        setName("");
        setDownloadLink("");
        setMd5Sum("");
        setBuildNumber("");
        setReleaseDate("");
        setThumbnailLink("");
    }

    public int getNumber()
    {
        return mNumber;
    }

    public void setNumber(String number)
    {
        try
        {
            this.mNumber = Integer.valueOf(number);
        } catch (NumberFormatException e)
        {
            this.mNumber = 0;
        }
    }

    public void setNumber(int number)
    {
        this.mNumber = number;
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String mName)
    {
        this.mName = mName;
    }

    public String getDownloadLink()
    {
        return mOTADownloadLink;
    }

    public void setDownloadLink(String mDownloadLink)
    {
        this.mOTADownloadLink = mDownloadLink;
    }

    public String getMd5Sum()
    {
        return mOTAMd5Sum;
    }

    public void setMd5Sum(String mMd5Sum)
    {
        this.mOTAMd5Sum = mMd5Sum;
    }

    public boolean isNewerVersionThan(DownloadableItem item)
    {

        boolean result = false;

        if (item != null)
        {
            result = this.getNumber() > item.getNumber();
        }
        else
        {
            Log.e(TAG, "Invalid Number for Version");
        }

        return result;
    }

    public void setBuildNumber(String buildNumber)
    {
        mBuildNumber = buildNumber;
    }

    public String getBuildNumber()
    {
        return mBuildNumber;
    }

    public void setReleaseDate(String releaseDate)
    {
        mReleaseDate = releaseDate;
    }

    public String getReleaseDate()
    {
        return mReleaseDate;
    }

    public void setThumbnailLink(String thumbnailImageLink)
    {
        mThumbnailImageLink = thumbnailImageLink;
    }

    public String getThumbnailLink()
    {
        return mThumbnailImageLink;
    }

    public void setVersionDependencies(String dependencyList)
    {
        if (TextUtils.isEmpty(dependencyList))
        {
            mDependencies.clear();
        }
        else
        {
            String[] dependencies = dependencyList.split(DEPENDENCY_SEPARATOR);
            for (String dependency : dependencies)
            {
                try
                {
                    mDependencies.add(Integer.valueOf(dependency));
                } catch (NumberFormatException e)
                {
                    Log.e(TAG, "Invalid dependency");
                }
            }
        }
    }

    public ArrayList<Integer> getVersionDependencies()
    {
        return mDependencies;
    }
}
