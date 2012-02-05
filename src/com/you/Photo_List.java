package com.you;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class Photo_List extends MapActivity {

	private MapView map;
	private MapController mapCon;

	private Button b1, b2;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_list);
		MyApplication.getInstance().addActivity(this);

		map = (MapView) findViewById(R.id.pl_map);
		b1 = (Button) findViewById(R.id.pl_b1);
		b2 = (Button) findViewById(R.id.pl_b2);

		// 设置地图模式
		map.setTraffic(true);
		map.setSatellite(false);
		map.setStreetView(true);
		// 设置地图支持缩放
		map.setBuiltInZoomControls(true);

		// 设置显示地点
		GeoPoint geoBj = new GeoPoint((int) (39.95 * 1000000),
				(int) (116.37 * 1000000));
		mapCon = map.getController();
		mapCon.setCenter(geoBj);

		// 设置倍数（1-21）
		mapCon.setZoom(12);
	}

	class Photo_Overlay extends Overlay {

		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when) {
			
			super.draw(canvas, mapView, shadow);
			Paint paint = new Paint();
			
			return true;

		}

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
