package me.ele.x5;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

/**
 * Created by jgzhu on 3/22/17.
 */

public class MyApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();

    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
      @Override public void onViewInitFinished(boolean arg0) {
        Log.e("testx5", " onViewInitFinished is " + arg0);
      }

      @Override public void onCoreInitFinished() {
        Toast.makeText(getApplicationContext(), "Tbs init finished", Toast.LENGTH_SHORT).show();
        Log.e("app", " onCoreInitFinished");
      }
    };
    QbSdk.setTbsListener(new TbsListener() {
      @Override public void onDownloadFinish(int i) {
        Log.d("testx5","onDownloadFinish is " + i);
      }

      @Override public void onInstallFinish(int i) {
        Log.d("testx5","onInstallFinish is " + i);
      }

      @Override public void onDownloadProgress(int i) {
        Log.d("testx5","onDownloadProgress:"+i);
      }
    });

    QbSdk.initX5Environment(getApplicationContext(),  cb);
    //QbSdk.forceSysWebView();
  }
}
