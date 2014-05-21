/*
 * Copyright (C) 2013 Fairphone Project
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

package com.fairphone.updater;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

public class Version implements Comparable<Version> {

    private static final String TAG = Version.class.getSimpleName();

    private static final String DEPENDENCY_SEPARATOR = ",";

    public static final String FAIRPHONE_VERSION_NUMBER = "FairphoneUpdateVersionNumber";

    public static final String FAIRPHONE_VERSION_NAME = "FairphoneUpdateVersionName";

    public static final String FAIRPHONE_VERSION_BUILD_NUMBER = "FairphoneUpdateVersionBuildNumber";

    public static final String FAIRPHONE_ANDROID_VERSION = "FairphoneUpdateAndroidVersion";

    public static final String FAIRPHONE_VERSION_OTA_DOWNLOAD_LINK = "FairphoneUpdateVersionOTADownloadLink";

    public static final String FAIRPHONE_VERSION_THUMBNAIL_DOWNLOAD_LINK = "FairphoneUpdateVersionThumbnailDownloadLink";

    public static final String FAIRPHONE_VERSION_OTA_MD5 = "FairphoneUpdateVersionOTAMD5";

    public static final String IMAGE_TYPE_AOSP = "AOSP";

    public static final String IMAGE_TYPE_FAIRPHONE = "FAIRPHONE";

    private int mNumber;

    private String mName;

    private String mAndroidVersion;

    private String mOTADownloadLink;

    private String mOTAMd5Sum;

    private String mBuildNumber;

    private String mReleaseNotes;

    private String mReleaseDate;

    private String mThumbnailImageLink;

    private ArrayList<Integer> mDependencies;

    private String mImageType;

    public Version() {
        mDependencies = new ArrayList<Integer>();
    }

    public static Version getVersionFromSharedPreferences(Context context) {
        Version version = new Version();
        SharedPreferences sharedPrefs = context.getSharedPreferences(
                FairphoneUpdater.FAIRPHONE_UPDATER_PREFERENCES, Context.MODE_PRIVATE);
        Resources resources = context.getResources();

        int defaultVersionNumber = resources.getInteger(R.integer.defaultVersionNumber);
        version.setNumber(sharedPrefs.getInt(FAIRPHONE_VERSION_NUMBER, defaultVersionNumber));

        String defaultVersionName = resources.getString(R.string.defaultVersionName);
        version.setName(sharedPrefs.getString(FAIRPHONE_VERSION_NAME, defaultVersionName));

        String defaultVersionBuildNumber = resources.getString(R.string.defaultBuildNumber);
        version.setBuildNumber(sharedPrefs.getString(FAIRPHONE_VERSION_BUILD_NUMBER,
                defaultVersionBuildNumber));

        String defaultVersionAndroid = resources.getString(R.string.defaultAndroidVersionNumber);
        version.setAndroidVersion(sharedPrefs.getString(FAIRPHONE_ANDROID_VERSION,
                defaultVersionAndroid));

        version.setDownloadLink(sharedPrefs.getString(FAIRPHONE_VERSION_OTA_DOWNLOAD_LINK, ""));

        version.setThumbnailLink(sharedPrefs.getString(FAIRPHONE_VERSION_THUMBNAIL_DOWNLOAD_LINK,
                ""));

        version.setMd5Sum(sharedPrefs.getString(FAIRPHONE_VERSION_OTA_MD5, ""));

        if (TextUtils.isEmpty(version.getMd5Sum()) || TextUtils.isEmpty(version.getMd5Sum())) {
            return null;
        }
        return version;
    }

    public void saveToSharedPreferences(Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(
                FairphoneUpdater.FAIRPHONE_UPDATER_PREFERENCES, Context.MODE_PRIVATE);

        Editor editor = sharedPrefs.edit();
        editor.putInt(FAIRPHONE_VERSION_NUMBER, getNumber());
        editor.putString(FAIRPHONE_VERSION_NAME, getName());
        editor.putString(FAIRPHONE_ANDROID_VERSION, getAndroidVersion());
        editor.putString(FAIRPHONE_VERSION_BUILD_NUMBER, getBuildNumber());
        editor.putString(FAIRPHONE_VERSION_OTA_DOWNLOAD_LINK, getDownloadLink());
        editor.putString(FAIRPHONE_VERSION_THUMBNAIL_DOWNLOAD_LINK, getThumbnailLink());
        editor.putString(FAIRPHONE_VERSION_OTA_MD5, getMd5Sum());
        editor.commit();
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        try {
            this.mNumber = Integer.valueOf(number);
        } catch (NumberFormatException e) {
            this.mNumber = 0;
        }
    }

    public void setNumber(int number) {
        this.mNumber = number;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDownloadLink() {
        return mOTADownloadLink;
    }

    public void setDownloadLink(String mDownloadLink) {
        this.mOTADownloadLink = mDownloadLink;
    }

    public String getMd5Sum() {
        return mOTAMd5Sum;
    }

    public void setMd5Sum(String mMd5Sum) {
        this.mOTAMd5Sum = mMd5Sum;
    }

    public String getAndroidVersion() {
        return mAndroidVersion;
    }

    public void setAndroidVersion(String mAndroid) {
        this.mAndroidVersion = mAndroid;
    }

    public boolean isNewerVersionThan(Version version) {

        boolean result = false;

        try {
            result = this.getNumber() > version.getNumber();
        } catch (Throwable t) {
            Log.e(Version.class.getSimpleName(), "Invalid Number for Version", t);
        }

        return result;
    }

    public void deleteFromSharedPreferences(Context context) {
        setNumber(1);
        setName(null);
        setBuildNumber(null);
        setAndroidVersion(null);
        setReleaseNotes(null);
        setReleaseDate(null);
        setMd5Sum(null);
        setThumbnailLink(null);
        setDownloadLink(null);
        saveToSharedPreferences(context);
    }

    public void setBuildNumber(String buildNumber) {
        mBuildNumber = buildNumber;
    }

    public String getBuildNumber() {
        return mBuildNumber;
    }

    public void setReleaseNotes(String releaseNotes) {
        mReleaseNotes = releaseNotes;
    }

    public String getReleaseNotes() {
        return mReleaseNotes;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setThumbnailLink(String thumbnailImageLink) {
        mThumbnailImageLink = thumbnailImageLink;
    }

    public String getThumbnailLink() {
        return mThumbnailImageLink;
    }

    public void setVersionDependencies(String dependencyList) {
        if (TextUtils.isEmpty(dependencyList)) {
            mDependencies.clear();
        } else {
            String[] dependencies = dependencyList.split(DEPENDENCY_SEPARATOR);
            for (String dependency : dependencies) {
                try {
                    mDependencies.add(Integer.valueOf(dependency));
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Invalid dependency");
                }
            }
        }
    }

    public ArrayList<Integer> getVersionDependencies() {
        return mDependencies;
    }

    public void setImageType(String imageType) {
        mImageType = imageType;
    }

    public String getImageType() {
        return mImageType;
    }
    
    public String getImageTypeDescription(Resources resources) {
        String description = resources.getString(R.string.fairphone);
        if(mImageType.equalsIgnoreCase(IMAGE_TYPE_AOSP)){
            description = resources.getString(R.string.android);
        }
        if(mImageType.equalsIgnoreCase(IMAGE_TYPE_FAIRPHONE)){
            description = resources.getString(R.string.fairphone);
        }
        return description;
    }
    
    public static String getImageTypeDescription(String imageType, Resources resources) {
        String description = resources.getString(R.string.fairphone);
        if(imageType.equalsIgnoreCase(IMAGE_TYPE_AOSP)){
            description = resources.getString(R.string.android);
        }
        if(imageType.equalsIgnoreCase(IMAGE_TYPE_FAIRPHONE)){
            description = resources.getString(R.string.fairphone);
        }
        return description;
    }

    @Override
    public int compareTo(Version another) {
        int retVal;
        if (another != null) {
            if (this.getNumber() < another.getNumber()) {
                retVal = 1;
            } else if (this.getNumber() == another.getNumber()) {
                retVal = 0;
            } else {
                retVal = -1;
            }
        } else {
            retVal = 1;
        }
        return retVal;
    }
}
