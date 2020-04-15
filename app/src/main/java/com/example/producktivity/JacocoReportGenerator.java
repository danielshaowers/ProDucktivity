package com.example.producktivity;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

public class JacocoReportGenerator {
    static void generateCoverageReport() {
        String TAG = "jacoco";
        // use reflection to call emma dump coverage method, to avoid
        // always statically compiling against emma jar
        Log.d("StorageSt", Environment.getExternalStorageState());
        String coverageFilePath = Environment.getExternalStorageDirectory() + File.separator + "coverage.exec";
        File coverageFile = new File(coverageFilePath);
        try {
            coverageFile.createNewFile();
            Class<?> emmaRTClass = Class.forName("com.vladium.emma.rt.RT");
            Method dumpCoverageMethod = emmaRTClass.getMethod("dumpCoverageData",
                    coverageFile.getClass(), boolean.class, boolean.class);

            dumpCoverageMethod.invoke(null, coverageFile, false, false);
            Log.e(TAG, "generateCoverageReport: ok");
        } catch (Exception  e) {
            throw new RuntimeException("Is emma jar on classpath?", e);
        }
    }
}