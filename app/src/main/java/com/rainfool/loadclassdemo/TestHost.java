package com.rainfool.loadclassdemo;

import android.util.Log;
import com.rainfool.loadclass.LoadClassHost;
import com.rainfool.loadclass.Test0;
import com.rainfool.loadclass.TestRoot;

/**
 * @author krystian
 */
@LoadClassHost
public class TestHost {
    static TestRoot testRoot = new TestRoot();

    public static long loadRoot() {
        long start = System.currentTimeMillis();
        testRoot.loadTest();
        long end = System.currentTimeMillis();
        return end - start;
    }
}
