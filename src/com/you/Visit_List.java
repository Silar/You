package com.you;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class gets all visits and lists them with an image, its name and the
 * datetime.
 * 
 * @author Simple_liu
 * 
 */

public class Visit_List extends Activity {

	private Button vl_b1, vl_b2;
	GridView grid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.visit_list);
		MyApplication.getInstance().addActivity(this);

		vl_b1 = (Button) findViewById(R.id.vl_b1);
		vl_b2 = (Button) findViewById(R.id.vl_b2);
		grid = (GridView) findViewById(R.id.vl_gridview);

		// 主页面返回选择
		vl_b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent vl_intent1 = new Intent();
				vl_intent1.setClass(Visit_List.this, Main.class);

				Intent vl_intent11 = new Intent();
				vl_intent11.setClass(Visit_List.this, You_First.class);

				File fol = new File("sdcard/YouTu");
				File[] fil = fol.listFiles();

				if (fil.length > 0) {
					startActivity(vl_intent1);
				} else
					startActivity(vl_intent11);
				finish();
			}
		});

		// 新建游记页面选择
		vl_b2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent vl_intent2 = new Intent();
				vl_intent2.setClass(Visit_List.this, New_Visit.class);

				Intent vl_intent22 = new Intent();
				vl_intent22.setClass(Visit_List.this, New_Visit_First.class);

				File folder = new File("sdcard/YouTu");
				File[] fo = folder.listFiles();

				if (fo.length == 0) {
					startActivity(vl_intent22);
				} else {
					startActivity(vl_intent2);
				}

			}
		});

		// 获取所有游记信息

		// 获取游记名字
		File file = new File("sdcard/YouTu");

		// 文件夹按日期排序（最新的排在上面）

		File[] files = file.listFiles();
		if (files.length > 0) {

			Arrays.sort(files, new Visit_List.CompratorByLastModified());

			// 获取游记创建时间

			// 自定义文件创建时间显示ʾ
			SimpleDateFormat Nformat = new SimpleDateFormat("yyyy/MM/dd");

			// 生成动态数组，加入数据.
			final ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

			for (File mCurrentFile : files) {
				// 判断获得的内容是文件夹还是文件，如果是文件夹则列出来.
				if (mCurrentFile.isDirectory()) {
					// 获取游记创建时间
					long time = mCurrentFile.lastModified();
					Date currentTime = new Date(time);
					String ing = Nformat.format(currentTime);

					// 计算每个游记里有多少张相片
					int Pcount = 0;
					File d = new File("sdcard/YouTu/"
							+ mCurrentFile.getName().toString());
					File flist[] = d.listFiles();
					for (int x = 0; x < flist.length; x++) {
						Pcount++;
					}

					// 获取相片文件夹的最新拍摄的一张照片
					String visit = "Album";
					SharedPreferences sp = getSharedPreferences(visit, 0);
					String vn = sp.getString(mCurrentFile.getName(), "");

					// 将文件地址直接转码成bitmap.
					Bitmap bt = BitmapFactory.decodeFile("sdcard/YouTu/"
							+ mCurrentFile.getName().toString() + "/" + vn
							+ ".JPEG");
					Log.d("Visit_List", vn);

					HashMap<String, Object> map = new HashMap<String, Object>();
					if (bt != null) {
						map.put("ItemImage", bt);
					} else {
						map.put("ItemImage", R.drawable.icon);
					}
					map.put("ItemTitle", mCurrentFile.getName() + "(" + Pcount
							+ ")");
					map.put("ItemText", ing);
					listItem.add(map);
				}
			}
			;

			// 生成适配器的Item和动态数组对应的元素
			final SimpleAdapter listItemAdapter = new SimpleAdapter(this,
					listItem,// 数据源
					R.layout.visit_item, // ListItem的XML实现
					// 动态数组与ImageItem对应的子项
					new String[] { "ItemImage", "ItemTitle", "ItemText" },
					// ImageItem的XML文件里面的一个ImageView,两个TextView ID
					new int[] { R.id.ItemImage, R.id.ItemTitle, R.id.ItemText });

			// 重新定义Adapter,可以显示图片.
			listItemAdapter.setViewBinder(new ViewBinder() {

				@Override
				public boolean setViewValue(View view, Object data,
						String textRepresentation) {
					// TODO Auto-generated method stub
					if (view instanceof ImageView && data instanceof Bitmap) {
						ImageView iv = (ImageView) view;
						iv.setImageBitmap((Bitmap) data);
						return true;
					} else
						return false;
				}

			});

			// 添加并且显示
			grid.setAdapter(listItemAdapter);

			// 添加点击，进入对应相册进行图片浏览.
			grid.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub

					// 获取进入相册的名称并且将名称传给Photo_List.
					RelativeLayout rl = (RelativeLayout) view;
					TextView tv = (TextView) rl.getChildAt(1);
					String ff = (String) tv.getText();
					String fn = ff.substring(0, ff.lastIndexOf("(")).trim();
					Log.d("游记", fn);
					Log.d("游记", ff);

					Intent vl_intent3 = new Intent();
					vl_intent3.setClass(Visit_List.this, Photo_List.class);

					// 将点击的Item相应的游记名字暂时保存，用来显示这个游记里面的照片.
					String dataN = "Fname";
					SharedPreferences set = getSharedPreferences(dataN,
							MODE_PRIVATE);
					SharedPreferences.Editor editor = set.edit();
					editor.putString("Fn", fn);
					editor.commit();

					startActivity(vl_intent3);

				}

			});

			// 长按显示是否删除游记
			grid.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

				@Override
				public void onCreateContextMenu(ContextMenu menu, View v,
						ContextMenuInfo menuInfo) {
					// TODO Auto-generated method stub

					// AdapterView.AdapterContextMenuInfo来获取单元的信息。
					final AdapterView.AdapterContextMenuInfo Linfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
					new AlertDialog.Builder(Visit_List.this)

							.setTitle("删除游记")

							.setIcon(R.drawable.icon)

							.setMessage("确定删除该游记？")
							.setPositiveButton("是",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub

											int pos = Linfo.position;
											// 获取对应HaspMap的数据内容
											HashMap<String, Object> ma = listItem
													.get(pos);
											// 删除对应Item
											listItem.remove(pos);
											// 获取对应相册名字
											String ss = ma.get("ItemTitle")
													.toString();
											String str = ss.substring(0,
													ss.lastIndexOf("(")).trim();
											Log.d("游记显示", str);
											Log.d("游记显示", ss);
											String st = "sdcard/YouTu/" + str;
											// 删除文件夹
											delFolder(st);

											// 更新Item显示
											listItemAdapter
													.notifyDataSetChanged();

											// 对比Main主页面继续本次旅行后对应的游记名字，如果删除的相册名字和它一样，则也把继续本次旅行后对应的名字删掉
											// 删除之后，将排在第一行的游记名字保存到“VisitName”中.
											SharedPreferences sh = getSharedPreferences(
													"VisitName", MODE_PRIVATE);
											String con = sh.getString(
													"Vcontent", "");

											Log.d("Hello", con);
											if (con.equals(str)) {
												sh.edit().clear().commit();

												// 获取排在第一行游记的名字,并保存到“VisitName”中.
												File fo = new File(
														"sdcard/YouTu");
												File[] fi = fo.listFiles();
												if (fi.length > 0) {
													File fil = fi[0];
													String filen = fil
															.getName()
															.toString();
													SharedPreferences.Editor edi = sh
															.edit();
													edi.putString("Vcontent",
															filen);
													edi.commit();
												}

											}

											// 删除游记相关描述信息
											SharedPreferences sha = getSharedPreferences(
													"Visit_Message", 0);
											sha.edit().remove(str).commit();

											// 删除SharedPreferences(Album)中的相关信息
											SharedPreferences shar = getSharedPreferences(
													"Album", 0);
											shar.edit().remove(str).commit();
										}
									})
							.setNegativeButton("否",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub

										}
									}).show();
				}
			});

		} else {
			Toast toast = Toast.makeText(getApplicationContext(),
					"暂时还没有游记，赶快去创建一个吧！", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

	/**
	 * 对比文件创建时间
	 * 
	 * @author Simple_liu
	 * 
	 */
	static class CompratorByLastModified implements Comparator<File> {
		public int compare(File f1, File f2) {
			long diff = f1.lastModified() - f2.lastModified();
			if (diff > 0)
				return -1;
			else if (diff == 0)
				return 0;
			else
				return 1;
		}

		public boolean equals(Object obj) {
			return true;
		}
	}

	/**
	 * transform bytes[] to bitmap object.
	 * 
	 * @param bytes
	 * @param opts
	 * @return
	 */
	public static Bitmap getPicFromBytes(byte[] bytes,
			BitmapFactory.Options opts) {
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param filePathAndName
	 *            String 文件夹路径及名称 如c:/fqf
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			System.out.println("删除文件夹操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径 如 c:/fqf
	 */
	public void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}

}
