package test.app.downloadimage;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoadImage extends AppCompatActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String imageUrl="https://api.learn2crack.com/android/images/donut.png";
        img= (ImageView) findViewById(R.id.img);
        new DownloadImage().execute(imageUrl);
    }

    class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        private URL imageUrl;
        private HttpURLConnection httpUrlConnection;
        private InputStream inStream;
        private Bitmap bitmap;
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                imageUrl = new URL(params[0]);
                httpUrlConnection = (HttpURLConnection) imageUrl.openConnection();
                httpUrlConnection.setDoInput(true);
                httpUrlConnection.connect();
                inStream = httpUrlConnection.getInputStream();
                BitmapFactory.Options bitmapOption = new BitmapFactory.Options();
                bitmapOption.inPreferredConfig = Bitmap.Config.ARGB_8888;
                bitmap = BitmapFactory.decodeStream(inStream, null, bitmapOption);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            File filePath = Environment.getExternalStorageDirectory();
            File dir = new File(filePath.getAbsolutePath() + "/wallpaper/");
            dir.mkdirs();
            String fileName = "wallpaper";
            File file = new File(dir, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
                fos.flush();
                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                img.setImageBitmap(bitmap);
                pDialog.dismiss();
            }

        }
    }
}
