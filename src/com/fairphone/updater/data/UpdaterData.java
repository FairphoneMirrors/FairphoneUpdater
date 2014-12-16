package com.fairphone.updater.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Build;

import com.fairphone.updater.R;
import com.fairphone.updater.gappsinstaller.GappsInstallerHelper;
import com.fairphone.updater.tools.Utils;

public class UpdaterData
{

    private static UpdaterData mInstance;

    private int mLatestAOSPVersionNumber;

    private int mLatestFairphoneVersionNumber;

    private Map<Integer, Version> mAOSPVersionMap;

    private Map<Integer, Version> mFairphoneVersionMap;
    
    private Map<Integer, Store> mAppStoresMap;

    public static UpdaterData getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new UpdaterData();
        }
        return mInstance;
    }

    private UpdaterData()
    {
        mLatestAOSPVersionNumber = 0;
        mLatestFairphoneVersionNumber = 0;
        mAOSPVersionMap = new HashMap<Integer, Version>();
        mFairphoneVersionMap = new HashMap<Integer, Version>();
        mAppStoresMap = new HashMap<Integer, Store>();
    }

    public void resetUpdaterData()
    {
        mLatestAOSPVersionNumber = 0;
        mLatestFairphoneVersionNumber = 0;
        mAOSPVersionMap.clear();
        mFairphoneVersionMap.clear();
        mAppStoresMap.clear();
    }

    public void setLatestAOSPVersionNumber(String latestVersion)
    {
        mLatestAOSPVersionNumber = getLatestVersionFromTag(latestVersion);
    }

    private int getLatestVersionFromTag(String latestVersion)
    {
        int latestVersionNumber;
        try
        {
            latestVersionNumber = Integer.valueOf(latestVersion).intValue();
        } catch (NumberFormatException e)
        {
            latestVersionNumber = 0;
        }
        return latestVersionNumber;
    }

    public void setLatestFairphoneVersionNumber(String latestVersion)
    {
        mLatestFairphoneVersionNumber = getLatestVersionFromTag(latestVersion);
    }

    public void addAOSPVersion(Version version)
    {
        mAOSPVersionMap.put(version.getNumber(), version);
    }

    public void addFairphoneVersion(Version version)
    {
        mFairphoneVersionMap.put(version.getNumber(), version);
    }
    
    public void addAppStore(Store store)
    {
        mAppStoresMap.put(store.getNumber(), store);
    }

    public Version getLatestVersion(String imageType)
    {
        Version version = null;
        if (Version.IMAGE_TYPE_AOSP.equalsIgnoreCase(imageType))
        {
            version = mAOSPVersionMap.get(mLatestAOSPVersionNumber);
        }
        else if (Version.IMAGE_TYPE_FAIRPHONE.equalsIgnoreCase(imageType))
        {
            version = mFairphoneVersionMap.get(mLatestFairphoneVersionNumber);
        }

        return version;
    }

    public ArrayList<Version> mapToOrderedVersionList(Collection<Version> a)
    {
        ArrayList<Version> retval = new ArrayList<Version>();
        for (Version version : a)
        {
            retval.add(version);
        }
        Collections.sort(retval);
        return retval;
    }
    
    public ArrayList<Store> mapToOrderedStoreList(Collection<Store> a)
    {
        ArrayList<Store> retval = new ArrayList<Store>();
        for (Store store : a)
        {
            retval.add(store);
        }
        Collections.sort(retval);
        return retval;
    }

    public List<Version> getAOSPVersionList()
    {
        ArrayList<Version> retval = mapToOrderedVersionList(mAOSPVersionMap.values());
        return retval;
    }

    public List<Version> getFairphoneVersionList()
    {
        ArrayList<Version> retval = mapToOrderedVersionList(mFairphoneVersionMap.values());
        return retval;
    }
    
    public List<Store> getAppStoreList()
    {
        ArrayList<Store> retval = mapToOrderedStoreList(mAppStoresMap.values());

        return retval;
    }

    public Version getVersion(String imageType, int versionNumber)
    {
        Version version = null;
        if (Version.IMAGE_TYPE_AOSP.equalsIgnoreCase(imageType))
        {
            version = mAOSPVersionMap.get(versionNumber);
        }
        else if (Version.IMAGE_TYPE_FAIRPHONE.equalsIgnoreCase(imageType))
        {
            version = mFairphoneVersionMap.get(versionNumber);
        }

        return version;
    }
    
    public Store getStore(int storeNumber)
    {
        return mAppStoresMap.get(storeNumber);
    }

    public boolean isAOSPVersionListEmpty()
    {
        return mAOSPVersionMap.isEmpty();
    }

    public boolean isFairphoneVersionListEmpty()
    {
        return mFairphoneVersionMap.isEmpty();
    }
    
    public boolean isAppStoreListEmpty()
    {
        return mAppStoresMap.isEmpty();
    }
}
