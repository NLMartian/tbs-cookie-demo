package me.ele.x5;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

  public static final String URL = "https://ele.me/";
  public static final String COOKIE = "abc=123";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button btnSetCookie = (Button) findViewById(R.id.btn_set_cookie);
    Button btnReadCookie = (Button) findViewById(R.id.btn_read_cookie);
    Button btnExportFile = (Button) findViewById(R.id.btn_export_file);
    final TextView txtCookie = (TextView) findViewById(R.id.cookie);

    final CookieManager tbsCookieManager = CookieManager.getInstance();
    final CookieSyncManager tbsCookieSyncManager = CookieSyncManager.createInstance(this);

    final android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
    final android.webkit.CookieSyncManager cookieSyncManager =
        android.webkit.CookieSyncManager.createInstance(this);

    btnSetCookie.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        tbsCookieManager.setCookie(URL, COOKIE);
        tbsCookieSyncManager.sync();

        cookieManager.setCookie(URL, COOKIE);
        cookieSyncManager.sync();
      }
    });

    btnReadCookie.setOnClickListener(new View.OnClickListener() {

      @Override public void onClick(View v) {
        String tbsCookie = tbsCookieManager.getCookie(URL);
        String systemCookie = cookieManager.getCookie(URL);

        StringBuilder content = new StringBuilder();
        content.append("tbs cookie: ");
        content.append(tbsCookie);
        content.append("\n");

        content.append("system cookie: ");
        content.append(systemCookie);

        txtCookie.setText(content.toString());
      }
    });

    btnExportFile.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        File database = new File("/data/data/me.ele.x5/app_webview/Cookies");
        File copy =
            new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Cookies");
        try {
          copyFileUsingStream(database, copy);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }

  private static void copyFileUsingStream(File source, File dest) throws IOException {
    InputStream is = null;
    OutputStream os = null;
    try {
      is = new FileInputStream(source);
      os = new FileOutputStream(dest);
      byte[] buffer = new byte[1024];
      int length;
      while ((length = is.read(buffer)) > 0) {
        os.write(buffer, 0, length);
      }
    } finally {
      is.close();
      os.close();
    }
  }

}
