/**
 *
 */
package in.eweblabs.careeradvance.loader;

import java.io.File;

import android.content.Context;
import android.os.Environment;

/**
 * @author ppphuc
 *
 */
public class FileCache {

	private File cacheDir;

    public FileCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), "LazyList");
        }
        else {
            cacheDir = context.getCacheDir();
        }
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }

    public File getFile(String url) {
        String fileName = String.valueOf(url.hashCode());
        File file = new File(cacheDir, fileName);
        return file;
    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null) {
            return;
        }
        for (File f:files) {
            f.delete();
        }
    }

}
