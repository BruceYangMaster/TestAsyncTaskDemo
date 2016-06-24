package com.deepblue.testasynctaskdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 练习asynctask的demo---下载图片
 */
public class MainActivity extends AppCompatActivity {

    private ImageView mImg;
    private String mUrl = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mImg = (ImageView) findViewById(R.id.img);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        /**
         * 执行异步任务从传入的url得到一个bitmap，然后设置给imageview
         */
        new MyAsyncTask().execute(mUrl);
    }

    class MyAsyncTask extends AsyncTask<String, Integer, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];//得到url
            Bitmap bitmap = null;
            InputStream is = null;
            /**
             * 模拟了一个进度
             */
            for (int i = 0; i < 100; i++) {
                publishProgress(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                //打开连接
                URLConnection urlConnection = new URL(url).openConnection();
                urlConnection.connect();
                is = urlConnection.getInputStream();
                //通过得到的输入流得到bitmap
                bitmap = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mImg.setImageBitmap(bitmap);
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            int value = values[0];
            mProgressBar.setProgress(value);
            if (value == 99) {
                mProgressBar.setVisibility(View.GONE);
            }
        }
    }
}
