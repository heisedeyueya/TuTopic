package com.kankan.tutopic.cache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

import com.kankan.logging.Logger;
import com.kankan.tutopic.exception.InsufficientStorageException;

/**
 * A simple subclass of {@link ImageResizer} that fetches and resizes images fetched from a URL.
 */
public class ImageFetcher extends ImageResizer {
    private static final Logger LOG = Logger.getLogger(ImageFetcher.class);

    private static final int HTTP_CACHE_SIZE = 10 * 1024 * 1024; // 10MB

    public static final String HTTP_CACHE_DIR = "http";

    private int mMaxNumOfPixels = 240 * 320;

    /**
     * Initialize providing a target image width and height for the processing images.
     * 
     * @param context
     * @param imageWidth
     * @param imageHeight
     */
    private ImageFetcher(Context context, int imageWidth, int imageHeight) {
        super(context, imageWidth, imageHeight);
    }

    /**
     * Initialize providing a single target image size (used for both width and height);
     * 
     * @param context
     * @param imageSize
     */
    private ImageFetcher(Context context, int imageSize) {
        super(context, imageSize);
    }

    /**
     * The main process method, which will be called by the ImageWorker in the AsyncTask background thread.
     * 
     * @param data
     *            The data to load the bitmap, in this case, a regular http URL
     * @return The downloaded and resized bitmap
     * @throws InsufficientStorageException
     * @throws IOException
     */
    private Bitmap processBitmap(String data) {
        LOG.debug("process bitmap {}.", data);

        // Download a bitmap, write it to a file
        File f = null;
        try {
            f = downloadBitmap(mContext, data);
        } catch (IOException e) {
            LOG.warn(e);
        } catch (InsufficientStorageException e) {
            LOG.warn(e);
        }

        if (f != null) {
            // Return a sampled down version
            return decodeSampledBitmapFromFile(f.toString(), mImageWidth, mImageHeight, mMaxNumOfPixels);
        }

        return null;
    }

    @Override
    protected Bitmap processBitmap(Object data) {
        return processBitmap(String.valueOf(data));
    }

    /**
     * Download a bitmap from a URL, write it to a disk and return the File pointer. This implementation uses a simple
     * disk cache.
     * 
     * @param context
     *            The context to use
     * @param urlString
     *            The URL to fetch
     * @return A File pointing to the fetched bitmap
     * @throws InsufficientStorageException
     * @throws IOException
     */
    public static File downloadBitmap(Context context, String urlString) throws IOException,
            InsufficientStorageException {
        File cacheDir = DiskLruCache.getDiskCacheDir(context, HTTP_CACHE_DIR);

        DiskLruCache cache = DiskLruCache.openCacheSafe(context, cacheDir, HTTP_CACHE_SIZE);
        if (cache == null) {
            cacheDir = DiskLruCache.getInternalDiskCacheDir(context, HTTP_CACHE_DIR);
            cache = DiskLruCache.openCache(context, cacheDir, HTTP_CACHE_SIZE);
        }

        final File cacheFile = new File(cache.createFilePath(urlString));

        if (cache.containsKey(urlString)) {
            LOG.debug("{} found in http cache.", urlString);
            return cacheFile;
        }

        LOG.debug("download {}.", urlString);

        CacheUtils.disableConnectionReuseIfNecessary();
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            final InputStream in =
                    new BufferedInputStream(urlConnection.getInputStream(), CacheUtils.IO_BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(cacheFile), CacheUtils.IO_BUFFER_SIZE);

            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }

            return cacheFile;

        } catch (IOException e) {
            LOG.warn("download failed. err={}", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOG.warn(e);
                }
            }
        }

        return null;
    }

    public static ImageFetcher getInstance(Activity activity) {
        final DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        // final int longest = height > width ? height : width;
        ImageFetcher instance = new ImageFetcher(activity.getApplicationContext(), width, height);

        return instance;
    }

    public static ImageFetcher getInstance(Context ctx, int width, int height) {
        ImageFetcher instance = new ImageFetcher(ctx, width, height);

        return instance;
    }

    public void setMaxNumOfPixels(int maxNumOfPixels) {
        mMaxNumOfPixels = maxNumOfPixels;
    }
}
