/**
 *
 */
package in.eweblabs.careeradvance.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.ImageView;

import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.UI.CircleImageView;

public class ImageLoader {

	MemoryCache memCache = new MemoryCache();
    FileCache fileCache;
    private Map<CircleImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<CircleImageView, String>());
    ExecutorService executorService;

    public ImageLoader (Context context) {
        fileCache = new FileCache(context);
        executorService = Executors.newFixedThreadPool(5);
    }

   public int stub_id = R.drawable.ic_face_white_48dp;

    public void DisplayImage(String url, CircleImageView imageView) {
        imageViews.put(imageView, url);
        Bitmap bitmap = memCache.get(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
        else {
            queuePhoto(url, imageView);
            imageView.setImageResource(stub_id);
        }
    }

    private void queuePhoto(String url, ImageView imageView) {
        PhoToLoad p = new PhoToLoad(url, imageView);
        executorService.submit(new PhotoLoader(p));
    }

    private class PhoToLoad {
        public String url;
        public ImageView imageView;

        public PhoToLoad(String u, ImageView i) {
            url = u;
            imageView = i;
        }
    }

    class PhotoLoader implements Runnable {
        PhoToLoad phoToLoad;
        PhotoLoader(PhoToLoad phoToLoad) {
            this.phoToLoad = phoToLoad;
        }

        @Override
        public void run() {
            if (imageViewReused(phoToLoad)) {
                return;
            }
            Bitmap bitmap = getBitmap(phoToLoad.url);
            memCache.put(phoToLoad.url, bitmap);
            if (imageViewReused(phoToLoad)) {
                return;
            }
            BitmapDisplayer bitmapDisplayer = new BitmapDisplayer(bitmap, phoToLoad);
            Activity activity = (Activity)phoToLoad.imageView.getContext();
            activity.runOnUiThread(bitmapDisplayer);
        }
    }

    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        PhoToLoad phoToLoad;

        public BitmapDisplayer(Bitmap bitmap, PhoToLoad phoToLoad) {
            this.bitmap = bitmap;
            this.phoToLoad = phoToLoad;
        }

        public void run() {
            if (imageViewReused(phoToLoad)) {
                return;
            }
            if (bitmap != null) {
                phoToLoad.imageView.setImageBitmap(bitmap);
            }
            else {
               //phoToLoad.imageView.setImageResource(stub_id);
            }
        }
    }

    boolean imageViewReused(PhoToLoad phoToLoad) {
        String tag = imageViews.get(phoToLoad.imageView);
        if (tag == null && !tag.equals(phoToLoad.url)) {
            return true;
        }
        return false;
    }

    private Bitmap getBitmap(String url) {
        File file = fileCache.getFile(url);
        Bitmap b = decodeFile(file);
        if (b != null) {
            return b;
        }
        Bitmap bitmap = null;
        try {
            URL imageURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)imageURL.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setInstanceFollowRedirects(true);
            InputStream inputStream = connection.getInputStream();
            OutputStream outputStream = new FileOutputStream(file);
            Utils.copyStream(inputStream, outputStream);
            outputStream.close();
            bitmap = decodeFile(file);
            return bitmap;
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    private Bitmap decodeFile(File file) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(new FileInputStream(file), null, options);
            final int REQUIRE_SIZE = 1024*4;
            int widthTemp = options.outWidth, heightTemp = options.outHeight;
            int scale = 1;
            while (true) {
                if (widthTemp/2 < REQUIRE_SIZE || heightTemp/2 < REQUIRE_SIZE) {
                    break;
                }
                widthTemp  /= 2;
                heightTemp /= 2;
                scale      *= 2;
            }
            BitmapFactory.Options options1 = new BitmapFactory.Options();
            options1.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(file), null, options1);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


}
