package in.eweblabs.careeradvance.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import in.eweblabs.careeradvance.Network.RetrofitInstance;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.StaticData.StaticConstant;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class DownloadService extends IntentService {

    public DownloadService() {
        super("Download Service");
    }

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private int totalFileSize;
    private String mFileName ;

    @Override
    protected void onHandleIntent(Intent intentReceived) {

        if (intentReceived != null) {
            mFileName = intentReceived.getStringExtra(StaticConstant.USER_RESUME_TEXT);

            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setType("*/*");
            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), mFileName);

            if (mFileName.endsWith(".rtf")) {
                intent.setDataAndType(Uri.fromFile(outputFile),"application/rtf");
            } else if (mFileName.endsWith(".docx")) {
                intent.setDataAndType(Uri.fromFile(outputFile),"application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            } else if (mFileName.endsWith(".doc")) {
                intent.setDataAndType(Uri.fromFile(outputFile),"application/msword");
            } else if (mFileName.endsWith(".pdf")) {
                intent.setDataAndType(Uri.fromFile(outputFile),"application/pdf");
            }
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);


            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(android.R.drawable.stat_sys_download)
                    .setContentTitle(mFileName)
                    .setContentIntent(pIntent)
                    .setProgress(0, 0, true)
                    .setContentText(getString(R.string.downloading_file))
                    .setAutoCancel(true);
            notificationManager.notify(0, notificationBuilder.build());

            initDownload(intentReceived.getStringExtra(StaticConstant.USER_RESUME_PATH));
        }

    }

    private void initDownload(String resumeUrl) {
        Call<ResponseBody> request = RetrofitInstance.getInstance().downloadResume(resumeUrl);
        try {
            downloadFile(request.execute().body());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void downloadFile(ResponseBody body) throws IOException {

        int count;
        byte data[] = new byte[1024 * 4];
        long fileSize = body.contentLength();
        InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), mFileName);
        OutputStream output = new FileOutputStream(outputFile);
        long total = 0;
        long startTime = System.currentTimeMillis();
        int timeCount = 1;
        while ((count = bis.read(data)) != -1) {

            total += count;
            totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
            double current = Math.round(total / (Math.pow(1024, 2)));

           // int progress = (int) ((total * 100) / fileSize);

          //  long currentTime = System.currentTimeMillis() - startTime;

            Download download = new Download();
            download.setTotalFileSize(totalFileSize);

            /*if (currentTime > 500 * timeCount) {

                download.setCurrentFileSize((int) current);
                download.setProgress(progress);
                //sendNotification(download);
                notificationManager.notify(1234, notificationBuilder.build());
                timeCount++;
            }*/

            output.write(data, 0, count);
        }
        onDownloadComplete();
        output.flush();
        output.close();
        bis.close();

    }

    private void sendNotification(Download download) {
     //   Logger.v("downloading",""+download.getCurrentFileSize() + "/" + totalFileSize + " MB");
       // sendIntent(download);
      //  notificationBuilder.setProgress(100, download.getProgress(), false);

      //  notificationBuilder.setContentText("Downloading file " + download.getCurrentFileSize() + "/" + totalFileSize + " MB");
        notificationManager.notify(1234, notificationBuilder.build());
    }

    private void sendIntent(Download download) {

       /* Intent intent = new Intent(BaseActivityScreen.MESSAGE_PROGRESS);
        intent.putExtra("download", download);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);*/
    }

    private void onDownloadComplete() {

        Download download = new Download();
        download.setProgress(100);
        //sendIntent(download);
        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText(getString(R.string.download_complete));
        notificationBuilder.setSmallIcon(android.R.drawable.stat_sys_download_done);
        notificationManager.notify(0, notificationBuilder.build());

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }

}
