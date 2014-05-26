package com.blackmoon.tutopic.app;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import org.apache.commons.io.FileUtils;

import android.app.Application;
import android.os.Environment;
import android.os.Handler;

import com.kankan.logging.Logger;

public class CrashHandler implements UncaughtExceptionHandler {
    private static final Logger LOG = Logger.getLogger(CrashHandler.class);

    private final Application mApplication;
    private Handler mUIHandler;
    private Thread mUiThread;

    public CrashHandler(Application app) {
        mApplication = app;
        mUIHandler = new Handler();
        mUiThread = Thread.currentThread();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable e) {
        LOG.error(e);
        Throwable cause = e.getCause();
        while (cause != null) {
            LOG.error(cause);
            cause = cause.getCause();
        }

        writeCrashInfoToFile(e);

        if (Thread.currentThread() != mUiThread) {
            mUIHandler.post(new Runnable() {

                @Override
                public void run() {
                    mApplication.onTerminate();
                }
            });
        } else {
            mApplication.onTerminate();
        }
    }

    private void writeCrashInfoToFile(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        Throwable cause = t.getCause();
        while (cause != null) {
            cause.printStackTrace(pw);
            cause = cause.getCause();
        }
        String crashInfo = sw.toString();
        pw.close();

        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File file = mApplication.getApplicationContext().getExternalCacheDir();
                if (file != null) {
                    file = new File(file, "crash");
                    file.mkdirs();
                    FileUtils.writeStringToFile(file, crashInfo, "utf-8");
                }
            }
        } catch (IOException e) {
            LOG.warn(e);
        }
    }
}
