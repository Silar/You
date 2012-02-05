package com.you;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class YouTu extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.youtu);
		MyApplication.getInstance().addActivity(this);

		MakeDirs("YouTu");

		// 跳转至主页面
		final Intent visit_intent1 = new Intent();
		visit_intent1.setClass(YouTu.this, Main.class);

		// 跳转至第一次使用游图页面
		final Intent visit_intent2 = new Intent();
		visit_intent2.setClass(YouTu.this, You_First.class);

		Timer timer = new Timer();
		TimerTask tast = new TimerTask() {
			@Override
			public void run() {

				File fol = new File("sdcard/YouTu");
				File[] fil = fol.listFiles();

				if (fil.length > 0) {
					startActivity(visit_intent1);
				} else
					startActivity(visit_intent2);
				finish();
			}
		};
		timer.schedule(tast, 1000 * 2); //两秒后跳转

	}

	/**
	 * 判断SD卡是否存在
	 * 
	 * @return
	 */
	public static boolean IsSdCard() {
		boolean nIsSaveSdCard = false;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
			nIsSaveSdCard = true;
		else
			nIsSaveSdCard = false;
		return nIsSaveSdCard;
	}

	public static boolean MakeDirs(String aPath) {
		boolean nIsExist = false;
		if (IsSdCard()) {
			File sdCardDir = Environment.getExternalStorageDirectory();
			String p = sdCardDir.getAbsolutePath() + "/" + aPath;
			Log.i("New_Visit.MakeDirs", p);
			File nPath = new File(p);
			nIsExist = nPath.exists();
			if (!nIsExist)
				nIsExist = nPath.mkdirs();
		} else {
			File nPath = new File("./" + aPath);
			nIsExist = nPath.exists();
			if (!nIsExist)
				nIsExist = nPath.mkdirs();
		}
		return nIsExist;
	}

	public static FileOutputStream GetFileOutStream(String aFileName,
			boolean aIsApp) {
		try {
			if (IsSdCard()) {
				File sdCardDir = Environment.getExternalStorageDirectory();
				File saveFile = new File(sdCardDir, aFileName);
				FileOutputStream outStream = new FileOutputStream(saveFile,
						aIsApp);
				return outStream;
			} else {
				File saveFile = new File("./", aFileName);
				FileOutputStream outStream = new FileOutputStream(saveFile,
						aIsApp);
				return outStream;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
